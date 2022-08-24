package com.jazzhipster.mycompose.presentation

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jazzhipster.mycompose.R
import com.jazzhipster.mycompose.navigation.PlayActions
import com.jazzhipster.mycompose.presentation.common.AppBar
import com.jazzhipster.mycompose.presentation.tabs.CourseTabs
import com.jazzhipster.mycompose.remote.model.ArticleModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfilePage(actions: PlayActions, viewModel: HomeViewModel) {
    BackHandler {
        viewModel.onPositionChanged(CourseTabs.HOME_PAGE)
    }
    Scaffold(topBar = {
        AppBar(title = "Profile", showBack = true, backClick = {
            viewModel.onPositionChanged(CourseTabs.HOME_PAGE)
        })
    }) {
        Column(
            modifier = Modifier
                .background(Color.Yellow)
                .fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            UserInfoFields(actions.enterArticle)
        }
    }
}

@Composable
fun UserInfoFields(
    enterArticle: (ArticleModel) -> Unit
) {
    Text(
        text = "John liao",
        modifier = Modifier.height(32.dp),
        style = MaterialTheme.typography.h5
    )
    Text(
        text = "mobile number",
        modifier = Modifier
            .padding(bottom = 20.dp)
            .height(24.dp),
        style = MaterialTheme.typography.body1
    )

}

