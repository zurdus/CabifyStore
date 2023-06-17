package com.zurdus.cabifystore.domain.checkout.discount

import com.zurdus.cabifystore.data.discount.api.DiscountRepository
import com.zurdus.cabifystore.model.Discount
import com.zurdus.cabifystore.util.Dispatchers
import kotlinx.coroutines.withContext

class LoadDiscounts(
    private val discountRepository: DiscountRepository,
    private val dispatchers: Dispatchers,
) {
    suspend operator fun invoke(): List<Discount> = withContext(dispatchers.default) {
        discountRepository.getDiscounts()
    }
}
