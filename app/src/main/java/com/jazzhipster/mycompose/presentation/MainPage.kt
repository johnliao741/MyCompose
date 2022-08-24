package com.jazzhipster.mycompose.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.ExperimentalPagingApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jazzhipster.mycompose.navigation.PlayActions
import com.jazzhipster.mycompose.presentation.home.HomePage
import com.jazzhipster.mycompose.presentation.home.HomePageViewModel
import com.jazzhipster.mycompose.presentation.project.ProjectPage
import com.jazzhipster.mycompose.presentation.project.ProjectViewModel
import com.jazzhipster.mycompose.presentation.tabs.CourseTabs


@ExperimentalPagerApi
@ExperimentalPagingApi
@Composable
fun MainPage(actions: PlayActions,viewModel: HomeViewModel = viewModel() ) {
    val position by viewModel.position.observeAsState()
    val tabs = CourseTabs.values()
    Scaffold(
        modifier=Modifier.statusBarsPadding(),
        backgroundColor = MaterialTheme.colors.primary,
        bottomBar = {
            BottomNavigation() {
                tabs.forEach { tab ->
                    BottomNavigationItem(
                        icon = {
                            Icon(painterResource(id = tab.icon), contentDescription = null)
                        },
                        label = { Text(text = tab.title.toUpperCase()) },
                        selected = tab == position, onClick = {
                            viewModel.onPositionChanged(tab)
                        })
                }
            }
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        Crossfade(targetState = position) { screen ->
            when(screen){
                CourseTabs.HOME_PAGE ->{
                    val viewModel = hiltViewModel<HomePageViewModel>()
                    HomePage(actions,modifier, viewModel)
                }
                CourseTabs.PROJECT->{
                    val viewModel = hiltViewModel<ProjectViewModel>()
                    ProjectPage(actions,modifier, viewModel)
                }
                CourseTabs.MINE->{
                    ProfilePage(actions = actions,viewModel)
                }
            }
        }
    }
}