package com.maruchin.feature.recipes.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.maruchin.core.ui.theme.GymsterTheme
import com.maruchin.data.diet.sampleDiets
import com.maruchin.gymster.feature.recipes.R

@Composable
internal fun RecipeItem(
    name: String,
    macro: String,
    ingredients: List<String>,
    isPlanned: Boolean,
    index: Int = 0,
    onShowMore: (() -> Unit)? = null,
    onAddToPlanned: () -> Unit,
    onRemoveFromPlanned: () -> Unit
) {
    val isEven = index % 2 == 0
    Column {
        Row {
            if (isEven) {
                RecipeData(
                    name = name,
                    macro = macro,
                    ingredients = ingredients,
                )
                RecipeImage(image = painterResource(R.drawable.recipe_cover))
            } else {
                RecipeImage(image = painterResource(R.drawable.recipe_cover))
                RecipeData(
                    name = name,
                    macro = macro,
                    ingredients = ingredients,
                )
            }
        }
        Row {
            if (isPlanned) {
                FilterChip(
                    selected = true,
                    onClick = onRemoveFromPlanned,
                    label = {
                        Text(text = "Nie planuj")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.EventBusy, contentDescription = null)
                    },
                    modifier = Modifier.padding(start = 12.dp),
                )
            } else {
                FilterChip(
                    selected = false,
                    onClick = onAddToPlanned,
                    label = {
                        Text(text = "Zaplanuj")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.EventAvailable, contentDescription = null)
                    },
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
            if (onShowMore != null) {
                AssistChip(
                    onClick = onShowMore,
                    label = {
                        Text(text = "Szczegóły")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            modifier = Modifier.size(AssistChipDefaults.IconSize)
                        )
                    },
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
        }
    }
}

@Composable
private fun RowScope.RecipeData(
    name: String,
    macro: String,
    ingredients: List<String>,
) {
    Column(modifier = Modifier.weight(1f)) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp)
        )
        Text(
            text = macro,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        Text(
            text = stringResource(R.string.ingredients).uppercase(),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp)
        )
        ingredients.forEach { ingredient ->
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                Text(text = "- ", style = MaterialTheme.typography.bodySmall)
                Text(text = ingredient, style = MaterialTheme.typography.bodySmall)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun RowScope.RecipeImage(image: Painter) {
    Image(
        painter = image,
        contentDescription = null,
        modifier = Modifier.weight(1f)
    )
}

@Preview(showBackground = true)
@Composable
private fun RecipeItemPreview(@PreviewParameter(IndexProvider::class) index: Int) {
    val recipe = sampleDiets[0].groups[0].recipes[0]
    GymsterTheme {
        RecipeItem(
            name = recipe.name,
            macro = recipe.macro.format(),
            ingredients = recipe.ingredients.map { it.format() },
            index = index,
            isPlanned = true,
            onAddToPlanned = {},
            onRemoveFromPlanned = {},
            onShowMore = {},
        )
    }
}

private class IndexProvider : PreviewParameterProvider<Int> {
    override val values = sequenceOf(0, 1)
}
