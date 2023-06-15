package com.zurdus.cabifystore.feature.catalog.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.zurdus.base.ui.theme.CabifyTheme
import com.zurdus.cabifystore.base.response.ResponseError
import com.zurdus.cabifystore.data.product.model.Product
import com.zurdus.cabifystore.feature.catalog.R
import org.koin.androidx.compose.getViewModel
import java.math.BigDecimal

@Composable
fun CatalogScreen() {
    val viewModel = getViewModel<CatalogViewModel>()

    val products by viewModel.products.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()
    val refreshing by viewModel.refreshing.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()

    CatalogScreen(
        products = products,
        loading = loading,
        refreshing = refreshing,
        error = error,
        onRefresh = viewModel::onCatalogRefresh,
        onProductClick = viewModel::onProductItemClick
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CatalogScreen(
    products: List<Product>,
    loading: Boolean,
    refreshing: Boolean,
    error: ResponseError?,
    onRefresh: () -> Unit,
    onProductClick: (Product) -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = onRefresh,
    )

    Scaffold(
        modifier = Modifier.pullRefresh(pullRefreshState),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.cabify_store),
                        style = CabifyTheme.typography.h4
                    )
                },
                backgroundColor = CabifyTheme.color.neutral.background,
                contentColor = CabifyTheme.color.neutral.content,
                elevation = 0.dp
            )
        }
    ) { contentPaddings ->
        when {
            loading -> LoadingScreen()
            error != null -> {
                RefreshableContent(
                    pullRefreshState = pullRefreshState,
                    refreshing = refreshing
                ) {
                    ErrorScreen()
                }
            }

            else -> {
                RefreshableContent(
                    pullRefreshState = pullRefreshState,
                    refreshing = refreshing
                ) {
                    ProductCatalog(contentPaddings, products, onProductClick)
                }
            }
        }
    }
}

@Composable
private fun ProductCatalog(
    contentPaddings: PaddingValues = PaddingValues(0.dp),
    products: List<Product>,
    onProductClick: (Product) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPaddings),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item(
            span = { GridItemSpan(2) },
        ) {
            Text(
                text = stringResource(R.string.catalog_title_made_for_you),
                style = CabifyTheme.typography.h1,
            )

            Spacer(Modifier.height(48.dp))
        }

        itemsIndexed(products) { index, product ->
            ProductItem(
                index = index,
                product = product,
                onClick = onProductClick
            )
        }
    }
}

@Composable
private fun ProductItem(
    index: Int,
    product: Product,
    onClick: (Product) -> Unit,
) {
    val visual = product.getCatalogItemVisual(index = index)
    val colorSystem = visual.colorSystem

    val interactionSource = remember { MutableInteractionSource() }

    Surface(
        modifier = Modifier
            .clip(CabifyTheme.shape.medium)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(),
                onClick = { onClick(product) },
            ),
        color = colorSystem.background,
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(visual.imageUrl)
                    .decoderFactory(SvgDecoder.Factory())
                    .crossfade(true)
                    .build(),
                colorFilter = ColorFilter.tint(colorSystem.accent),
                modifier = Modifier
                    .height(96.dp)
                    .fillMaxWidth(),
                contentDescription = null,
            )

            Spacer(Modifier.height(16.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = visual.name,
                style = CabifyTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
                color = colorSystem.content,
            )

            Spacer(Modifier.height(8.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = visual.price,
                style = CabifyTheme.typography.h2,
                textAlign = TextAlign.Center,
                color = colorSystem.content,
            )
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = CabifyTheme.color.main.accent,
        )
    }
}

@Composable
private fun ErrorScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("There was an error", style = MaterialTheme.typography.body1)

        Text("Please try again")
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RefreshableContent(
    pullRefreshState: PullRefreshState,
    refreshing: Boolean,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.TopCenter,
    ) {
        content()

        PullRefreshIndicator(refreshing, pullRefreshState)
    }
}

@Preview
@Composable
fun CatalogItemPreview() {
    ProductItem(
        index = 1,
        product = Product(
            code = "test",
            imageUrl = "https://i.imgur.com/2cYRww5.png",
            name = "Test item",
            price = BigDecimal("100"),
        ),
        onClick = {},
    )
}
