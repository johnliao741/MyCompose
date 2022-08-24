package com.jazzhipster.mycompose.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.jazzhipster.mycompose.exception.ApiException
import com.jazzhipster.mycompose.remote.model.BannerBean
import com.jazzhipster.mycompose.remote.model.BaseModel
import com.jazzhipster.mycompose.remote.model.LoginRequest
import com.jazzhipster.mycompose.remote.model.ProjectTreeModel
import com.jazzhipster.mycompose.remote.page_source.HomePagingSource
import com.jazzhipster.mycompose.remote.page_source.ProjectPagingSource
import com.jazzhipster.mycompose.remote.service.HomePageService
import javax.inject.Inject

class HomeArticleRepository @Inject constructor(
    val homePageService: HomePageService
) {
    suspend fun login(request: LoginRequest):BaseModel<Unit>{
        val response = homePageService.login(request)
        if(response.status!=0){
            throw ApiException(response.errorMsg)
        }
        return response
    }

    fun getPagingData(page:Int) = Pager(
        PagingConfig(
            pageSize = 6,
            enablePlaceholders = false
        )
    ){
        HomePagingSource(homePageService)
    }.flow

    suspend fun getBanner():BaseModel<List<BannerBean>>{
        return homePageService.getBanner()
    }
    //Course
    suspend fun getProjectTree():BaseModel<List<ProjectTreeModel>>{
        return homePageService.getProjectTree()
    }
    fun getProjectPagingData(cid:Int) = Pager(
        PagingConfig(
            pageSize = 6,
            enablePlaceholders = false
        )
    ){
        ProjectPagingSource(homePageService).apply {
            this.cid = cid
        }
    }.flow

}