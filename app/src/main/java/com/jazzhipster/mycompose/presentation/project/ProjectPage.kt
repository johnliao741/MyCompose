package com.jazzhipster.mycompose.presentation.project

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.jazzhipster.mycompose.navigation.PlayActions
import com.jazzhipster.mycompose.presentation.home.ArticleListItem
import com.jazzhipster.mycompose.presentation.home.LcePage
import com.jazzhipster.mycompose.presentation.home.LoadingContent
import com.jazzhipster.mycompose.remote.model.ArticleModel
import com.jazzhipster.mycompose.remote.model.ProjectTreeModel
import com.jazzhipster.mycompose.remote.vo.Result


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProjectPage(
    enterArticle: PlayActions,
    modifier: Modifier,
    viewModel: ProjectViewModel
) {
    val lazyPagingItems = viewModel.projectsLiveData.collectAsLazyPagingItems()
    val tree by viewModel.treeProjectLiveData.observeAsState()
    val position by viewModel._position.collectAsState(initial = 0)
    LaunchedEffect(true) {
        viewModel.getData()
    }
    Scaffold {
        Column(
            modifier = Modifier.background(
                color = MaterialTheme.colors.primary
            )
        ) {
            LcePage(result = tree, onErrorClick = {
                viewModel.getData()
            }) {
                val data = (tree as Result.Success).data.data
                ArticleTabRow(position = position, data = data) { index, id, isFirst ->
                    if(isFirst){
                        if(position == 0)
                            viewModel.onPositionChanged(index)
                    }else{
                        viewModel.onPositionChanged(index)
                    }
                }
                ArticleListPaging(modifier, rememberLazyListState(), lazyPagingItems, enterArticle.enterArticle)
            }
        }
    }


}

@Composable
fun ArticleTabRow(
    position: Int?,
    data: List<ProjectTreeModel>,
    onTabClick: (Int, Int, Boolean) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = position ?: 0,
        modifier = Modifier.wrapContentWidth(),
        edgePadding = 3.dp
    ) {
        data.forEachIndexed { index, projectTreeModel ->
            Tab(
                text = { Text(text = projectTreeModel.name) },
                selected = position == index,
                onClick = {
                    onTabClick(index, projectTreeModel.id, false)
                }
            )

        }
        onTabClick(0, data[position ?: 0].id, true)
    }
}

@Composable
fun ArticleListPaging(
    modifier: Modifier,
    listState: LazyListState,
    lazyPagingItems: LazyPagingItems<ArticleModel>,
    enterArticle: (ArticleModel) -> Unit
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(lazyPagingItems) { article ->
            ArticleListItem(article = article!!, onClick = { urlArgs ->
                enterArticle(urlArgs)
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