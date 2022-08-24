package com.jazzhipster.mycompose.remote.page_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jazzhipster.mycompose.remote.model.ArticleModel
import com.jazzhipster.mycompose.remote.service.HomePageService

class HomePagingSource(val homePageService: HomePageService): PagingSource<Int, ArticleModel>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleModel>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleModel> {
        return try {
            val page = params.key ?: 1
            val apiResponse = homePageService.getArticle(page)
            val articleList = apiResponse.data.datas
            val prevKey = if(page>1) page -1 else null
            val nextKey = if(articleList.isNotEmpty()) page +1 else null
            LoadResult.Page(articleList,prevKey,nextKey)
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }

}