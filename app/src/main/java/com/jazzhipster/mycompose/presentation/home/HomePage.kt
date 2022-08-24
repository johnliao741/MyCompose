package com.jazzhipster.mycompose.presentation.home

import android.annotation.SuppressLint
import android.net.Network
import android.text.TextUtils
import android.util.Log
import android.widget.ProgressBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.jazzhipster.mycompose.R
import com.jazzhipster.mycompose.navigation.PlayActions
import com.jazzhipster.mycompose.presentation.common.AppBar
import com.jazzhipster.mycompose.presentation.common.ShowMessageDialog
import com.jazzhipster.mycompose.remote.model.ArticleModel
import com.jazzhipster.mycompose.remote.model.BannerBean
import com.jazzhipster.mycompose.remote.vo.Result
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalPagerApi
@Composable
fun HomePage(
    actions: PlayActions,
    modifier: Modifier,
    viewModel: HomePageViewModel
) {
    val listState = rememberLazyListState()
    val bannerData by viewModel.getBannerLiveData.observeAsState()
    val lazyPagingItems = viewModel.getArticleResult.collectAsLazyPagingItems()
    Log.e("bannerData", lazyPagingItems.toString())
    LaunchedEffect(true) {
        viewModel.getBanner()
    }

    viewModel.searchArticle()
    Column(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        val coroutineScope = rememberCoroutineScope()
        LcePage(result = bannerData, onErrorClick = {
            viewModel.getBanner()
            viewModel.searchArticle()
        }) {
            val banners = (bannerData as Result.Success<List<BannerBean>>).data
            val pagerState = rememberPagerState()
            AppBar(title = stringResource(id = R.string.home_page))
            HorizontalPager(
                count = banners.size,
                state = pagerState,
            ) { index ->
                AsyncImage(
                    model = banners[index].imagePath,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(7 / 3f)
                        .clickable {

                        }, contentDescription = null
                )
            }

            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
            )

            ArticleListPaging(
                modifier = Modifier,
                listState = listState,
                lazyPagingItems = lazyPagingItems,
                enterArticle = actions.enterArticle,

            ){articleModel ->
                viewModel.delete(articleModel)
                lazyPagingItems.itemSnapshotList.items.minus(articleModel)
            }


            var job: Job? = null
            LaunchedEffect(pagerState) {
                snapshotFlow { pagerState.currentPage }.collect { page ->
                    job?.cancel()
                    job = coroutineScope.launch {
                        delay(2000)
                        var next = pagerState.currentPage + 1
                        if (next >= pagerState.pageCount)
                            next = 0
                        pagerState.animateScrollToPage(next, 0f)
                    }
                    job?.start()

                }
            }
        }

    }
}

@Composable
fun <T> LcePage(
    result: Result<T>?,
    onErrorClick: () -> Unit,
    content: @Composable () -> Unit
) {
    when (result) {
        Result.Loading -> {
            LoadingContent()
        }
        is Result.Success -> {
            content()
        }
        is Result.Error -> {
            ShowMessageDialog(
                title = "錯誤",
                message = result.e.message ?: "未知錯誤",
                dismissDialog = { onErrorClick() }) {
                onErrorClick()
            }
        }
    }
}

@Composable
fun ArticleListPaging(
    modifier: Modifier,
    listState: LazyListState,
    lazyPagingItems: LazyPagingItems<ArticleModel>,
    enterArticle: (ArticleModel) -> Unit,
    deleteItem:(ArticleModel)->Unit
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(lazyPagingItems) { article ->
            ArticleListItem(article = article!!, onClick = { urlArgs ->
                //enterArticle(urlArgs)
                deleteItem(article)
            })
        }
        lazyPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {

                }
                loadState.append is LoadState.Loading -> {
                    item { LoadingContent() }
                }
                loadState.refresh is LoadState.Error -> {

                }
                loadState.append is LoadState.Error -> {

                }
            }

        }
    }
}

@Composable
fun ArticleListItem(
    article: ArticleModel,
    onClick: (ArticleModel) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    elevation: Dp = 1.dp,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1
) {
    Surface(
        elevation = elevation,
        shape = shape,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick(article)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (article.envelopePic.isNotBlank()) {
                AsyncImage(
                    model = article.envelopePic,
                    modifier = Modifier
                        .width(100.dp)
                        .aspectRatio(1f), contentDescription = null
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    modifier = Modifier
                        .height(96.dp)
                        .width(91.5.dp)
                )
            }
            Column {
                Text(text = article.title)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = article.superChapterName)
                    Text(if (TextUtils.isEmpty(article.author)) article.shareUser else article.author)
                }
            }
        }
    }
}

@Composable
fun LoadingContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(
            {
                ProgressBar(it)
            },
            modifier = Modifier

        ) {

        }

    }
}