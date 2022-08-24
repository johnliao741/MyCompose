package com.jazzhipster.mycompose.presentation.common

import android.text.Layout
import androidx.compose.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun AppBar(
    title: String,
    showBack: Boolean = false,
    backClick: () -> Unit = {},
    showRight: Boolean = false,
    rightImg: ImageVector = Icons.Rounded.MoreVert,
    rightClick: () -> Unit = {}

) {
    Column(
        modifier = Modifier.background(
            color = MaterialTheme.colors.primary
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(43.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (showBack) {
                IconButton(
                    modifier = Modifier.wrapContentWidth(
                        Alignment.Start
                    ),
                    onClick = backClick
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "back",
                        tint = Color.White

                    )
                }
            }else{
                IconButton(onClick = {}, enabled = false) {

                }
            }
            Text(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (showRight) {
                IconButton(
                    modifier = Modifier.wrapContentWidth(Alignment.End),
                    onClick = rightClick
                ) {
                    Icon(
                        imageVector = rightImg,
                        contentDescription = "more",
                        tint = Color.White
                    )

                }
            }else{
                IconButton(onClick = {}, enabled = false) {

                }
            }
        }
    }


}