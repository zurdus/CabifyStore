package com.zurdus.cabifystore.data.discount.impl.model

import com.zurdus.cabifystore.data.discount.api.model.BulkPriceDiscount
import com.zurdus.cabifystore.model.CartItem
import com.zurdus.cabifystore.model.Product
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.math.BigDecimal


internal class BulkPriceDiscountTest {
    private val productTestCode = "testCode"
    private val productWrongCode = "wrongCode"
    private val regularPrice = BigDecimal(10)
    private val discountedPrice = BigDecimal(5)

    private val cartItemWithWrongCode = CartItem(
        product = Product(
            code = productWrongCode,
            name = "Wrong product",
            price = regularPrice,
            imageUrl = ""
        ),
        count = 5,
        discount = BigDecimal.ZERO,
    )

    private val cartItemWithWrongThreshold = CartItem(
        product = Product(
            code = productTestCode,
            name = "Right product",
            price = regularPrice,
            imageUrl = ""
        ),
        count = 2,
        discount = BigDecimal.ZERO,
    )

    private val validCartItem = CartItem(
        product = Product(
            code = productTestCode,
            name = "Right product",
            price = regularPrice,
            imageUrl = ""
        ),
        count = 5,
        discount = BigDecimal.ZERO,
    )

    private val discount = BulkPriceDiscount(
        applicableProductCodes = setOf(productTestCode),
        itemCountThreshold = 3,
        bulkPrice = discountedPrice,
    )

    @Test
    fun whenItemCodeNotApplicable_thenDiscountIsNotApplied() {
        val result = discount.evaluate(cartItemWithWrongCode)

        assertThat(result.discount, `is`(BigDecimal.ZERO))
    }

    @Test
    fun whenThresholdIsNotMet_thenDiscountIsNotApplied() {
        val result = discount.evaluate(cartItemWithWrongThreshold)

        assertThat(result.discount, `is`(BigDecimal.ZERO))
    }

    @Test
    fun whenConditionsAreMet_thenDiscountIsApplied() {
        val expectedDiscount = discountedPrice.times(BigDecimal(5))
        val result = discount.evaluate(validCartItem)

        assertThat(result.discount, equalTo(expectedDiscount))
    }
}
