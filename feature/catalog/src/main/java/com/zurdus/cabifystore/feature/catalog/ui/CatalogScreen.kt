package com.zurdus.cabifystore.feature.catalog.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.zurdus.base.ui.theme.CabifyTheme
import com.zurdus.cabifystore.common.response.ResponseError
import com.zurdus.cabifystore.feature.catalog.R
import com.zurdus.cabifystore.feature.catalog.navigation.navigateToDetail
import com.zurdus.cabifystore.model.Product
import com.zurdus.cabifystore.navigation.navigateToCart
import com.zurdus.cabifystore.ui.composable.Preview
import org.koin.androidx.compose.getViewModel
import java.math.BigDecimal
import com.zurdus.base.ui.R as BaseR

@Composable
fun CatalogScreen(
    navController: NavController,
) {
    val viewModel = getViewModel<CatalogViewModel>()

    val products by viewModel.products.collectAsStateWithLifecycle()
    val cartItemCount by viewModel.cartCount.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()
    val refreshing by viewModel.refreshing.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()

    CatalogScreen(
        products = products,
        cartItemCount = cartItemCount,
        loading = loading,
        refreshing = refreshing,
        error = error,
        onRefresh = viewModel::onCatalogRefresh,
        onProductClick = { index, product ->
            navController.navigateToDetail(product, index) }
    ) {
        navController.navigateToCart()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CatalogScreen(
    products: List<Product>,
    cartItemCount: Int,
    loading: Boolean,
    refreshing: Boolean,
    error: ResponseError?,
    onRefresh: () -> Unit,
    onProductClick: (Int, Product) -> Unit,
    onCartButtonClick: () -> Unit,
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(BaseR.string.cabify_store),
                            style = CabifyTheme.typography.h4
                        )
                    }
                },
                actions = {
                    Row {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingCart,
                            tint = CabifyTheme.color.neutral.content,
                            contentDescription = null,
                        )

                        Box(
                            modifier = Modifier
                                .clickable(
                                    onClick = onCartButtonClick,
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(),
                                )
                                .background(
                                    CabifyTheme.color.mainInverse.background,
                                    CabifyTheme.shape.large
                                )
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                                .defaultMinSize(16.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = cartItemCount.toString(),
                                style = CabifyTheme.typography.caption,
                                color = CabifyTheme.color.mainInverse.content,
                            )
                        }
                    }
                },
                backgroundColor = CabifyTheme.color.neutral.background,
                contentColor = CabifyTheme.color.neutral.content,
                elevation = 0.dp,
                navigationIcon = {}
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

            products.isEmpty() -> {
                RefreshableContent(
                    pullRefreshState = pullRefreshState,
                    refreshing = refreshing
                ) {
                    EmptyScreen()
                }
            }

            else -> {
                RefreshableContent(
                    pullRefreshState = pullRefreshState,
                    refreshing = refreshing
                ) {
                    ProductCatalog(
                        contentPaddings = contentPaddings,
                        products = products,
                        onProductClick = onProductClick,
                        onCartButtonClick = onCartButtonClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductCatalog(
    contentPaddings: PaddingValues = PaddingValues(0.dp),
    products: List<Product>,
    onProductClick: (Int, Product) -> Unit,
    onCartButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(contentPaddings),
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
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
                CatalogItem(
                    index = index,
                    product = product,
                    onClick = onProductClick
                )
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 12.dp,
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                shape = CabifyTheme.shape.large,
                onClick = onCartButtonClick,
            ) {
                Text(
                    text = stringResource(R.string.button_continue_to_checkout),
                    style = CabifyTheme.typography.subtitle1,
                )
            }
        }
    }
}

@Composable
private fun CatalogItem(
    index: Int,
    product: Product,
    onClick: (Int, Product) -> Unit,
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
                onClick = { onClick(index, product) },
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
                    .placeholder(BaseR.drawable.placeholder)
                    .crossfade(true)
                    .build(),
                colorFilter = ColorFilter.tint(colorSystem.accent),
                modifier = Modifier
                    .height(96.dp)
                    .fillMaxWidth(),
                contentDescription = null,
            )

            Spacer(Modifier.height(16.dp))

            CompositionLocalProvider(LocalContentColor provides colorSystem.content) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = visual.name,
                    style = CabifyTheme.typography.subtitle1,
                    textAlign = TextAlign.Center,
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = visual.price,
                    style = CabifyTheme.typography.h2,
                    textAlign = TextAlign.Center,
                )
            }
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
        Text(
            text = stringResource(R.string.error_title_uh_oh),
            style = CabifyTheme.typography.h6
        )

        Text(
            text = stringResource(R.string.error_text_something_went_wrong),
            style = CabifyTheme.typography.body1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun EmptyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.empty_catalog_title_uh_oh),
            style = CabifyTheme.typography.h6
        )

        Text(
            text = stringResource(R.string.empty_catalog_text_ran_out_of_goodies),
            style = CabifyTheme.typography.body1,
            textAlign = TextAlign.Center
        )
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
private fun CatalogScreenPreview() {
    Preview {
        CatalogScreen(
            products = listOf(
                Product(
                    code = "",
                    name = "Cabify socks",
                    price = BigDecimal("7"),
                    imageUrl = ""
                ),
                Product(
                    code = "",
                    name = "Cabify crown",
                    price = BigDecimal("150"),
                    imageUrl = ""
                ),
                Product(
                    code = "",
                    name = "Cabify hat",
                    price = BigDecimal("15"),
                    imageUrl = ""
                ),
            ),
            cartItemCount = 3,
            loading = false,
            refreshing = false,
            error = null,
            onRefresh = {},
            onProductClick = { _, _ ->},
            onCartButtonClick = {},
        )
    }
}
