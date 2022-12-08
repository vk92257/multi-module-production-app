package com.core_ui.components.dropDowns

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.core_ui.R

/**
 * @Author: Vivek
 * @Date: 29/03/22
 */

@Composable
fun CustomDropDown(
    modifier: Modifier = Modifier,
    suggestions: List<String> = emptyList(),
    onItemClick: (String) -> Unit = {},
    placeHolder: String = suggestions[0],
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText: String by remember { mutableStateOf(placeHolder.ifEmpty { suggestions[0] }) }
    var textileSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp //it requires androidx.compose.material:material-icons-extended
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.Gray,
                ),
                shape = RoundedCornerShape(7.dp)
            )

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned {
                    textileSize = it.size.toSize()

                }
                .clickable { expanded = !expanded }
        ) {
            Text(
                text = selectedText,
                modifier =
                Modifier.padding(20.dp).align(
                    alignment = Alignment.CenterStart
                ),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Normal,
                color = colorResource(id = R.color.textSecondary),
            )

            Icon(icon, "contentDescription",
                Modifier
                    .clickable { expanded = !expanded }
                    .padding(20.dp)
                    .align(alignment = Alignment.CenterEnd) ,
            tint = colorResource(id = R.color.textSecondary),
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textileSize.width.toDp() })
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        selectedText = label
                        expanded = false
                        onItemClick(label)
                    }) {
                    Text(
                        text = label,
                    )
                }
            }
        }

    }
}


@Preview
@Composable
fun DropDownPreview(
) {
    CustomDropDown(
        modifier = Modifier.padding(20.dp).background(
            color = Color.White

                ).padding(
            10.dp
                ),
        suggestions = listOf("Apple", "Ball", "3", "4", "5", "6", "7", "8", "9", "10"),
        onItemClick = {},
        placeHolder = "Select",
    )

}