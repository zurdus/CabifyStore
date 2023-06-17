package com.zurdus.cabifystore.data.discount.impl

import com.zurdus.cabifystore.data.discount.api.DiscountRepository
import com.zurdus.cabifystore.model.Discount

internal class DefaultDiscountRepository(
    private val discountDataSource: MemoryDiscountDataSource,
) : DiscountRepository {
    override suspend fun getDiscounts(): List<Discount> = discountDataSource.getDiscounts()
}
