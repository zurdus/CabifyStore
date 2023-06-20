package com.zurdus.cabifystore.data.discount.impl.model

import com.zurdus.cabifystore.data.discount.api.model.TwoForOneDiscount
import com.zurdus.cabifystore.model.CartItem
import com.zurdus.cabifystore.model.Product
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.math.BigDecimal

internal class TwoForOneDiscountTest {
    private val productTestCode = "testCode"
    private val productWrongCode = "wrongCode"
    private val itemPrice = BigDecimal(10)

    private val cartItemWithWrongCode = CartItem(
        product = Product(
            code = productWrongCode,
            name = "Wrong product",
            price = itemPrice,
            imageUrl = ""
        ),
        count = 2,
        discount = BigDecimal.ZERO,
    )

    private val cartItemWithWrongItemCount = CartItem(
        product = Product(
            code = productTestCode,
            name = "Right product",
            price = itemPrice,
            imageUrl = ""
        ),
        count = 1,
        discount = BigDecimal.ZERO,
    )

    private val discount = TwoForOneDiscount(
        applicableProductCodes = setOf(productTestCode),
    )

    @Test
    fun whenItemCodeNotApplicable_thenDiscountIsNotApplied() {
        val result = discount.evaluate(cartItemWithWrongCode)

        assertThat(result.discount, CoreMatchers.`is`(BigDecimal.ZERO))
    }

    @Test
    fun whenItemCountNotMet_thenDiscountIsNotApplied() {
        val result = discount.evaluate(cartItemWithWrongItemCount)

        assertThat(result.discount, CoreMatchers.`is`(BigDecimal.ZERO))
    }

    @Test
    fun whenConditionsAreMet_thenDiscountIsApplied() {
        data class DiscountResult(val itemCount: Int, val discount: BigDecimal)

        val expectedDiscounts = listOf(
            DiscountResult(2, itemPrice),
            DiscountResult(3, itemPrice),
            DiscountResult(4, itemPrice * BigDecimal(2)),
        )

        expectedDiscounts.forEach { discountResult ->
            val cartItem = CartItem(
                product = Product(
                    code = productTestCode,
                    name = "Right product",
                    price = itemPrice,
                    imageUrl = ""
                ),
                count = discountResult.itemCount,
                discount = BigDecimal.ZERO,
            )

            val result = discount.evaluate(cartItem)

            assertThat(result.discount, CoreMatchers.equalTo(discountResult.discount))
        }
    }
}
