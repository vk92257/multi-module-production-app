package com.bjs.pdanative.presentation.components.header

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bjs.pdanative.R

@Composable
@Preview(showBackground = true)
fun TitleHeader(
    title: String = "",
    textStyle: TextStyle = MaterialTheme.typography.h6,
    trailerIcon: Painter = painterResource(id = R.drawable.ic_close),
    headIcon: Painter = painterResource(id = R.drawable.ic_arrow_back),
    isShowHeadIcon: Boolean = true,
    isShowTrailerIcon: Boolean = true,
    onHeadIconClick: () -> Unit = {},
    onTrailerIconClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.blackSecondary)
            )
            .size(60.dp)
    ) {

        Text(
            text = title.uppercase(),
            modifier = Modifier
                .align(
                    alignment = Alignment.Center,
                )
                .padding(start = 60.dp, end = 60.dp),
            style = textStyle,
            color = colorResource(id = R.color.white),
            maxLines = 1,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
        if (isShowHeadIcon)
            Icon(
                painter = headIcon,
                contentDescription = "painter",
                tint = colorResource(id = R.color.white),
                modifier = Modifier
                    .align(
                        alignment = Alignment.CenterStart
                    )
                    .clickable {
                        onHeadIconClick()
                    }
                    .size(60.dp)
                    .padding(15.dp)
            )

        if (isShowTrailerIcon)
            Icon(
                painter = trailerIcon,
                contentDescription = "painter",
                tint = colorResource(id = R.color.white),
                modifier = Modifier
                    .align(
                        alignment = Alignment.CenterEnd
                    )
                    .clickable {
                        onTrailerIconClick()
                    }
                    .size(60.dp)
                    .padding(15.dp)
            )
    }
}