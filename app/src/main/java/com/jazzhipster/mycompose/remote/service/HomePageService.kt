package com.jazzhipster.mycompose.remote.service

import com.jazzhipster.mycompose.remote.model.*
import retrofit2.http.*

//https://www.wanandroid.com/
interface HomePageService {
    //Login
    @POST("login")
    suspend fun login(@Body request: LoginRequest):BaseModel<Unit>
    //Home
    @GET("banner/json")
    suspend fun getBanner():BaseModel<List<BannerBean>>

    @GET("article/list/{a}/json")
    suspend fun getArticle(@Path("a") a:Int):BaseModel<ArticleListModel>

    //Project
    @GET("project/tree/json")
    suspend fun getProjectTree():BaseModel<List<ProjectTreeModel>>

    @GET("project/list/{page}/json")
    suspend fun getProject(
        @Path("page") page:Int,
        @Query("cid") cid:Int
    ):BaseModel<List<ArticleModel>>
}