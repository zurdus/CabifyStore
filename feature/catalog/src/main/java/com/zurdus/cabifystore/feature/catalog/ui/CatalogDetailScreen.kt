package com.zurdus.cabifystore.feature.catalog.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.zurdus.base.ui.theme.CabifyTheme
import com.zurdus.cabifystore.model.Product
import com.zurdus.cabifystore.ui.composable.Preview
import org.koin.androidx.compose.getViewModel
import java.math.BigDecimal
import com.zurdus.base.ui.R as BaseR

@Composable
fun CatalogDetailScreen(
    product: Product,
    index: Int,
    navController: NavController,
) {
    val viewModel: CatalogDetailViewModel = getViewModel()

    CatalogDetailScreen(
        product = product,
        index = index,
        onButtonClick = { addedProduct ->
            viewModel.onAddProductClick(addedProduct)
            navController.navigateUp()
        }
    )
}

@Composable
private fun CatalogDetailScreen(
    product: Product,
    index: Int,
    onButtonClick: (Product) -> Unit,
) {
    val visual = product.getCatalogItemVisual(index)
    val colorSystem = visual.colorSystem

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CabifyTheme.shape.medium),
        color = colorSystem.background,
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            ProductRow(visual)

            Spacer(Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = CabifyTheme.shape.large,
                onClick = { onButtonClick(product) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorSystem.accent,
                    contentColor = colorSystem.background,
                )
            ) {
                Text(
                    text = "Add to cart",
                    style = CabifyTheme.typography.subtitle1
                )
            }
        }
    }
}

@Composable
private fun ProductRow(visual: CatalogItemVisual) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier
                .height(96.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(visual.imageUrl)
                .decoderFactory(SvgDecoder.Factory())
                .placeholder(BaseR.drawable.placeholder)
                .crossfade(true)
                .build(),
            colorFilter = ColorFilter.tint(visual.colorSystem.accent),
            contentDescription = null,
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CompositionLocalProvider(
                LocalContentColor provides visual.colorSystem.content
            ) {
                Text(
                    text = visual.name,
                    style = CabifyTheme.typography.subtitle1,
                )

                Text(
                    text = visual.price,
                    style = CabifyTheme.typography.h2,
                )
            }
        }
    }
}

@Preview
@Composable
fun CatalogDetailScreenPreview() {
    Preview {
        CatalogDetailScreen(
            product = Product(
                code = "test",
                name = "Test product",
                price = BigDecimal.TEN,
                imageUrl = ""
            ),
            index = 1,
            onButtonClick = {},
        )
    }
}
