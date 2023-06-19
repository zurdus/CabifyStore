package com.zurdus.cabifystore.data.discount.api

import com.zurdus.cabifystore.model.Discount

interface DiscountRepository {
    suspend fun getDiscounts(): Set<Discount>
}
