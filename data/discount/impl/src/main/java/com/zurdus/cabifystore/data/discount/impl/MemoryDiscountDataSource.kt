package com.zurdus.cabifystore.data.discount.impl

import com.zurdus.cabifystore.model.Discount

/**
 * This should probably come from remote config or something like that.
 */
internal class MemoryDiscountDataSource {
    fun getDiscounts(): List<Discount> = emptyList()
}
