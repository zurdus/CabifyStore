package com.zurdus.cabifystore.feature.checkout.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.zurdus.base.ui.theme.CabifyTheme
import com.zurdus.cabifystore.feature.cart.R
import com.zurdus.cabifystore.model.Cart
import com.zurdus.cabifystore.model.CartItem
import com.zurdus.cabifystore.model.Product
import com.zurdus.cabifystore.ui.composable.AnimatedCurrency
import com.zurdus.cabifystore.ui.composable.Preview
import com.zurdus.cabifystore.ui.composable.Stepper
import com.zurdus.cabifystore.util.formatToEuros
import org.koin.androidx.compose.getViewModel
import java.math.BigDecimal
import com.zurdus.base.ui.R as BaseR

@Composable
fun CheckoutScreen(
    navController: NavController,
) {
    val viewModel: CheckoutViewModel = getViewModel()

    val cart by viewModel.cart.collectAsStateWithLifecycle()
    val productToDelete by viewModel.productToDelete.collectAsStateWithLifecycle()

    CheckoutScreen(
        cart = cart,
        productToDelete = productToDelete,
        onBack = navController::navigateUp,
        onProductCountChange = viewModel::onProductCountChange,
        onProductDeleteConfirm = viewModel::onProductDeleteConfirm,
        onProductDeleteCancel = viewModel::onProductDeleteCancel,
        onPayButtonClick = {} // TODO put something here
    )
}

@Composable
private fun CheckoutScreen(
    cart: Cart,
    productToDelete: Product?,
    onBack: () -> Unit,
    onProductCountChange: (Product, Int) -> Unit,
    onProductDeleteConfirm: (Product) -> Unit,
    onProductDeleteCancel: () -> Unit,
    onPayButtonClick: () -> Unit,
) {
    Scaffold(
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
                backgroundColor = CabifyTheme.color.neutral.background,
                contentColor = CabifyTheme.color.neutral.content,
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    ) { contentPaddings ->
        when {
            cart.items.isEmpty() -> {
                EmptyScreen(
                    paddings = contentPaddings,
                    onButtonClick = onBack,
                )
            }

            else -> {
                CartContent(
                    cart = cart,
                    paddings = contentPaddings,
                    onProductCountChange = onProductCountChange,
                    onPayButtonClick = onPayButtonClick,
                )
            }
        }

        DeleteProductConfirmDialog(
            productToDelete = productToDelete,
            onConfirmClick = onProductDeleteConfirm,
            onDismiss = onProductDeleteCancel,
        )
    }
}

@Composable
private fun CartContent(
    cart: Cart,
    paddings: PaddingValues,
    onProductCountChange: (Product, Int) -> Unit,
    onPayButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddings),
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(paddings)
                .fillMaxWidth()
                .weight(1f),
        ) {
            item {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(R.string.checkout_title_your_shopping_cart),
                    style = CabifyTheme.typography.h1,
                )
            }

            items(cart.items.toList()) { item ->
                CartItem(item, onProductCountChange)
                Divider()
            }
        }

        CartFooter(cart, onPayButtonClick)
    }
}

@Composable
private fun CartItem(
    cartItem: CartItem,
    onProductCountChange: (Product, Int) -> Unit,
) {
    val visual = cartItem.getVisual()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(visual.imageUrl)
                .decoderFactory(SvgDecoder.Factory())
                .placeholder(BaseR.drawable.placeholder)
                .crossfade(true)
                .build(),
            modifier = Modifier.width(40.dp),
            contentDescription = null,
        )

        Spacer(Modifier.width(12.dp))

        Column {
            Text(
                text = visual.name,
                style = CabifyTheme.typography.subtitle1
            )

            Spacer(Modifier.height(8.dp))

            Stepper(
                value = visual.itemCount,
                onValueChange = { newValue ->
                    onProductCountChange(cartItem.product, newValue)
                }
            )
        }

        PriceColumn(
            modifier = Modifier.weight(1f),
            visual = visual,
        )
    }
}

@Composable
private fun PriceColumn(
    visual: CartItemVisual,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
    ) {
        AnimatedVisibility(visual.discount != BigDecimal.ZERO) {
            Box(
                modifier = Modifier.width(IntrinsicSize.Min),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .alpha(0.5f),
                    text = visual.subtotal.formatToEuros(),
                    style = CabifyTheme.typography.subtitle2,
                    textAlign = TextAlign.End
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .alpha(0.5f)
                        .background(CabifyTheme.color.neutral.content)
                )
            }
        }

        AnimatedCurrency(targetState = visual.total) { total ->
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = total.formatToEuros(),
                style = CabifyTheme.typography.subtitle1,
                color = CabifyTheme.color.main.content,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
private fun CartFooter(
    cart: Cart,
    onPayButtonClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        elevation = 12.dp,
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 24.dp,
                vertical = 16.dp
            ),
        ) {
            SummaryRow(
                tag = stringResource(R.string.checkout_tag_subtotal),
                value = cart.subtotal,
            )

            SummaryRow(
                tag = stringResource(R.string.checkout_tag_discount),
                value = cart.totalDiscount,
            )

            SummaryRow(
                tag = stringResource(R.string.checkout_tag_total),
                value = cart.total,
                emphasis = true,
            )

            Spacer(Modifier.height(24.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = CabifyTheme.shape.large,
                onClick = onPayButtonClick,
            ) {
                Text(
                    text = stringResource(R.string.button_text_pay_order),
                    style = CabifyTheme.typography.subtitle1
                )
            }
        }
    }
}

@Composable
private fun SummaryRow(
    tag: String,
    value: BigDecimal,
    emphasis: Boolean = false
) {
    val style = if (emphasis) {
        CabifyTheme.typography.h3
    } else {
        CabifyTheme.typography.subtitle1
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = tag,
            style = style,
        )

        AnimatedCurrency(targetState = value) {
            Text(
                modifier = Modifier.fillMaxWidth(fraction = 0.5f),
                text = value.formatToEuros(),
                style = style,
                color = CabifyTheme.color.main.content,
                textAlign = TextAlign.End,
            )
        }
    }
}

@Composable
private fun EmptyScreen(
    paddings: PaddingValues,
    onButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddings)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.checkout_title_no_items),
            style = CabifyTheme.typography.h6
        )

        Text(
            text = stringResource(R.string.checkout_subtitle_add_products),
            style = CabifyTheme.typography.h6,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = CabifyTheme.shape.large,
            onClick = onButtonClick,
        ) {
            Text(
                text = stringResource(R.string.button_text_explore_catalog),
                style = CabifyTheme.typography.subtitle1
            )
        }
    }
}

@Composable
private fun DeleteProductConfirmDialog(
    productToDelete: Product?,
    onConfirmClick: (Product) -> Unit,
    onDismiss: () -> Unit,
) {
    if (productToDelete == null) return

    AlertDialog(
        modifier = Modifier.padding(16.dp),
        onDismissRequest = onDismiss,
        text = {
            Text(
                text = stringResource(R.string.dialog_delete_text),
                style = CabifyTheme.typography.subtitle1,
                color = CabifyTheme.color.neutral.content,
            )
        },
        confirmButton = {
            Button(
                shape = CabifyTheme.shape.large,
                onClick = { onConfirmClick(productToDelete) },
            ) {
                Text(
                    text = stringResource(R.string.dialog_button_delete),
                    style = CabifyTheme.typography.subtitle1,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.dialog_button_cancel),
                    style = CabifyTheme.typography.subtitle1,
                    color = CabifyTheme.color.main.content
                )
            }
        }
    )
}

@Preview
@Composable
private fun CartItemPreview(
    @PreviewParameter(DiscountParamProvider::class) discount: BigDecimal,
) {
    Preview {
//        CartItem(
//            cartItem = CartItem(
//                product = Product(
//                    code = "test",
//                    name = "Test item",
//                    price = BigDecimal.TEN,
//                    imageUrl = ""
//                ),
//                count = 2,
//                discount = discount,
//            )
//        )
    }
}

private class DiscountParamProvider : PreviewParameterProvider<BigDecimal> {
    override val values: Sequence<BigDecimal> = sequenceOf(
        BigDecimal.ZERO,
        BigDecimal(5),
    )
}
