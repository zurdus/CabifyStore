package com.zurdus.cabifystore.data.discount.impl

import com.zurdus.cabifystore.data.discount.api.model.BulkPriceDiscount
import com.zurdus.cabifystore.data.discount.api.model.TwoForOneDiscount
import com.zurdus.cabifystore.model.Discount
import java.math.BigDecimal

/**
 * This should come from remote config or something like that.
 */
internal class MemoryDiscountDataSource {
    fun getDiscounts(): Set<Discount> = setOf(
        TwoForOneDiscount(applicableProductCodes = setOf("VOUCHER")),
        BulkPriceDiscount(
            applicableProductCodes = setOf("TSHIRT"),
            itemCountThreshold = 3,
            bulkPrice = BigDecimal(19)
        )
    )
}
