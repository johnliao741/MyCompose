package com.jazzhipster.mycompose.presentation.article

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.webkit.WebView
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.jazzhipster.mycompose.presentation.common.AppBar
import com.jazzhipster.mycompose.remote.model.ArticleModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ArticlePage(
    article: ArticleModel?,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var webview: WebView? = null
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            AppBar(title = article?.title ?: "文章詳情",
                showBack = true,
                backClick = {
                    if (webview?.canGoBack() == true) {
                        webview?.goBack()
                    } else {
                        onBack()
                    }
                },
                showRight = true,
                rightImg = Icons.Filled.Share,
                rightClick = {
                    sharePost(article?.title,article?.link,context)
                }
            )
        },
        content = {
            AndroidView(factory = {
                WebView(context).apply {
                    webview = this
                    loadUrl(article?.link ?: "")
                }
            })
        }
    )
}
fun sharePost(title:String?,post:String?,context: Context){
    if(title == null || post == null)
        return
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TITLE,title)
        putExtra(Intent.EXTRA_TEXT,post)
    }
    context.startActivity(intent)
}