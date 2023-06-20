package com.zurdus.data.product.impl

import com.zurdus.cabifystore.common.response.Response
import com.zurdus.cabifystore.common.response.ResponseError
import com.zurdus.cabifystore.model.Product
import com.zurdus.data.product.api.datasource.LocalProductDataSource
import com.zurdus.data.product.impl.datasource.RemoteProductDataSource
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.math.BigDecimal

internal class DefaultRepositoryTest {
    private val mockRemoteSource = mockk<RemoteProductDataSource>(relaxed = true)
    private val mockLocalSource = mockk<LocalProductDataSource>(relaxed = true)

    private val repository = DefaultProductRepository(mockRemoteSource, mockLocalSource)

    private val productList = listOf(
        Product("A", "Product A", BigDecimal(10), ""),
        Product("B", "Product B", BigDecimal(10), ""),
        Product("C", "Product C", BigDecimal(10), ""),
        Product("D", "Product D", BigDecimal(10), ""),
    )

    @Test
    fun whenRemoteDataLoaded_thenIsReturnedCorrectly() = runTest {
        coEvery { mockRemoteSource.getProducts() } returns Response.Success(productList)

        val response = repository.getProducts()

        assertThat(response, `is`(Response.Success(productList)))
    }

    @Test
    fun whenRemoteDataLoaded_thenIsSavedLocally() = runTest {
        coEvery { mockRemoteSource.getProducts() } returns Response.Success(productList)

        val localProducts = slot<List<Product>>()
        coEvery { mockLocalSource.saveProducts(capture(localProducts)) } returns Unit
        coEvery { mockLocalSource.getProducts() } answers {
            if (localProducts.isCaptured) {
                Response.Success(localProducts.captured)
            } else {
                Response.Failure(ResponseError.Unknown)
            }
        }

        repository.getProducts()

        assertThat(mockLocalSource.getProducts(), `is`(Response.Success(productList)))
    }

    @Test
    fun whenRemoteDataErrorAndLocalDataExists_thenLocalDataReturned() = runTest {
        coEvery { mockRemoteSource.getProducts() } returns Response.Failure(ResponseError.Network)
        coEvery { mockLocalSource.getProducts() } returns Response.Success(productList)

        val response = repository.getProducts()

        assertThat(response, `is`(Response.Success(productList)))
    }

    @Test
    fun whenRemoteDataErrorAndLocalDataDoesntExist_thenErrorReturned() = runTest {
        coEvery { mockRemoteSource.getProducts() } returns Response.Failure(ResponseError.Network)
        coEvery { mockLocalSource.getProducts() } returns Response.Failure(ResponseError.Unknown)

        val response = repository.getProducts()

        assertThat(response, `is`(Response.Failure(ResponseError.Unknown)))
    }
}
