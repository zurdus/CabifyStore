package com.zurdus.cabifystore.data.product.datasource

/**
 * This could be remote or whatever origin of the data we want.
 */
internal class ProductImageDataSource {
    companion object {
        private const val CODE_VOUCHER = "VOUCHER"
        private const val CODE_SHIRT = "TSHIRT"
        private const val CODE_MUG = "MUG"

        private const val URL_VOUCHER = "https://svgur.com/i/u8V.svg"
        private const val URL_SHIRT = "https://svgur.com/i/uAu.svg"
        private const val URL_MUG = "https://svgur.com/i/uAT.svg"

        val imageBank = mapOf(
            CODE_VOUCHER to URL_VOUCHER,
            CODE_SHIRT to URL_SHIRT,
            CODE_MUG to URL_MUG,
        )
    }

    fun getProductImageUrl(productCode: String): String = imageBank[productCode] ?: ""
}
