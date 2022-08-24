package com.jazzhipster.mycompose.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.ExperimentalPagingApi
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.gson.Gson
import com.jazzhipster.mycompose.remote.model.ArticleModel
import com.jazzhipster.mycompose.presentation.HomeViewModel
import com.jazzhipster.mycompose.presentation.MainPage
import com.jazzhipster.mycompose.presentation.article.ArticlePage
import com.jazzhipster.mycompose.presentation.login.LoginPage
import java.net.URLEncoder

object Destinations {
    const val LOGIN_PAGE_ROUTE = "login_page_route"
    const val HOME_PAGE_ROUTE = "home_page_route"
    const val ARTICLE_ROUTE = "article_route"
    const val ARTICLE_ROUTE_URL = "article_route_url"
}

@ExperimentalAnimatedInsets
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalPagingApi
@Composable
fun NavGraph(
    startDestination: String = Destinations.LOGIN_PAGE_ROUTE
) {
    val navController = rememberNavController()
    val actions = remember(navController) { PlayActions(navController) }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destinations.LOGIN_PAGE_ROUTE){
            LoginPage(actions = actions)
        }
        composable(Destinations.HOME_PAGE_ROUTE) {
            val viewModel = hiltViewModel<HomeViewModel>()
            MainPage(actions = actions, viewModel = viewModel)
        }
        composable(
            "${Destinations.ARTICLE_ROUTE}/{${Destinations.ARTICLE_ROUTE_URL}}",
            arguments = listOf(navArgument(Destinations.ARTICLE_ROUTE_URL) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val parcelable = arguments.getString(Destinations.ARTICLE_ROUTE_URL)
            val fromJson = Gson().fromJson(parcelable, ArticleModel::class.java)
            ArticlePage(article = fromJson, onBack = actions.upPress)
        }
    }
}

@ExperimentalAnimatedInsets
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalPagingApi
@Preview(showBackground = true)
@Composable
fun PreviewNavGraph(
) {
    NavGraph()
}

class PlayActions(navigation: NavHostController) {
    val enterHome:()->Unit = {
        navigation.navigate("${Destinations.HOME_PAGE_ROUTE}"){
            popUpTo(Destinations.LOGIN_PAGE_ROUTE){inclusive = true}
        }
    }
    val enterArticle: (ArticleModel) -> Unit = { article ->
        val gson = Gson().toJson(article).trim()
        val result = URLEncoder.encode(gson, "utf-8")
        navigation.navigate("${Destinations.ARTICLE_ROUTE}/$result")
    }
    val upPress: () -> Unit = {
        navigation.navigateUp()
    }
}