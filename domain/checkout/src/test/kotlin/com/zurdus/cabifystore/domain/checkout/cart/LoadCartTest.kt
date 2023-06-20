package com.zurdus.cabifystore.domain.checkout.cart

import com.zurdus.cabifystore.data.cart.api.CartSource
import com.zurdus.cabifystore.data.discount.api.DiscountRepository
import com.zurdus.cabifystore.data.discount.api.model.BulkPriceDiscount
import com.zurdus.cabifystore.data.discount.api.model.TwoForOneDiscount
import com.zurdus.cabifystore.model.Product
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.math.BigDecimal

internal class LoadCartTest {
    data class ExpectedCartInfo(
        val cartItemCount: Int,
        val productCount: Int,
        val total: BigDecimal,
    )

    private val prodA = Product("A", "Product A", BigDecimal(5), "")
    private val prodB = Product("B", "Product B", BigDecimal(20), "")
    private val prodC = Product("C", "Product C", BigDecimal(7.50), "")

    private val mockCartSource = mockk<CartSource>(relaxed = true) {
        every { getProducts() } answers {
            flow {
                emit(listOf(prodA, prodB, prodC))
                emit(listOf(prodA, prodB, prodA))
                emit(listOf(prodB, prodB, prodB, prodA, prodB))
                emit(listOf(prodA, prodB, prodA, prodA, prodC, prodB, prodB))
            }
        }
    }

    private val mockDiscountRepository = mockk<DiscountRepository> {
        coEvery { getDiscounts() } returns setOf(
            TwoForOneDiscount(
                applicableProductCodes = setOf("A")
            ),
            BulkPriceDiscount(
                applicableProductCodes = setOf("B"),
                itemCountThreshold = 3,
                bulkPrice = BigDecimal(19)
            )
        )
    }

    private val loadCart = LoadCart(mockCartSource, mockDiscountRepository)

    @Test
    fun whenProductsAreGiven_thenCartStructureIsCreatedCorrectly() = runTest {
        val expectedResults = listOf(
            ExpectedCartInfo(cartItemCount = 3, productCount = 3, total = BigDecimal("32.5")),
            ExpectedCartInfo(cartItemCount = 2, productCount = 3, total = BigDecimal("25")),
            ExpectedCartInfo(cartItemCount = 2, productCount = 5, total = BigDecimal("81")),
            ExpectedCartInfo(cartItemCount = 3, productCount = 7, total = BigDecimal("74.5")),
        )

        loadCart().toList().forEachIndexed { index, cart ->
            val expectedResult = expectedResults[index]

            assertThat(cart.items.size, `is`(expectedResult.cartItemCount))
            assertThat(cart.productCount, `is`(expectedResult.productCount))
            assertThat(cart.total, `is`(expectedResult.total))
        }
    }
}
