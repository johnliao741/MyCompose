package com.jazzhipster.mycompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.paging.ExperimentalPagingApi
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jazzhipster.mycompose.navigation.NavGraph
import com.jazzhipster.mycompose.navigation.PreviewNavGraph
import com.jazzhipster.mycompose.ui.theme.MyComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimatedInsets
@ExperimentalComposeUiApi
@ExperimentalFoundationApi

@ExperimentalPagerApi
@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window,false)
        setContent {
            val keyboardController = LocalSoftwareKeyboardController.current
            MyComposeTheme {
                // A surface container using the 'background' color from the theme
                ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .imePadding()
                            .pointerInput(Unit) {
                                detectTapGestures {
                                    keyboardController?.hide()
                                }
                            },
                        color = MaterialTheme.colors.background

                    ) {
                        NavGraph()


                    }
                }


            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!",
        Modifier.fillMaxSize(),
        textAlign = TextAlign.Center

        )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyComposeTheme {
        Greeting("Android")
    }
}