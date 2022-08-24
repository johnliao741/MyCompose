package com.jazzhipster.mycompose.presentation.tabs

import com.jazzhipster.mycompose.R

enum class CourseTabs(
    val title:String,
    val icon:Int
) {
    HOME_PAGE("Home", R.drawable.ic_launcher_foreground),
    PROJECT("Project",R.mipmap.icon_tab_bar_home_normal),
    OFFICIAL_ACCOUNT("Official",R.mipmap.icon_tab_bar_history_normal),
    MINE("Mine",R.mipmap.icon_tab_bar_device_normal)
}