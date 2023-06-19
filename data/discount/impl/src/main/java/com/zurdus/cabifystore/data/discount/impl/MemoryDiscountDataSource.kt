package com.zurdus.cabifystore.data.discount.impl

import com.zurdus.cabifystore.data.discount.impl.model.BulkPriceDiscount
import com.zurdus.cabifystore.data.discount.impl.model.TwoForOneDiscount
import com.zurdus.cabifystore.model.Discount
import java.math.BigDecimal

/**
 * This should come from remote config or something like that.
 */
internal class MemoryDiscountDataSource {
    fun getDiscounts(): Set<Discount> = setOf(
        TwoForOneDiscount(applicableProductCodes = setOf("VOUCHER")),
        BulkPriceDiscount(applicableProductCodes = setOf("TSHIRT"), bulkPrice = BigDecimal(19))
    )
}
