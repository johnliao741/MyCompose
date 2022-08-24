package com.jazzhipster.mycompose.remote.model


import com.google.gson.annotations.SerializedName

data class ArticleListModel(
    @SerializedName("curPage")
    val curPage: Int,
    @SerializedName("datas")
    val datas: List<ArticleModel>,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("over")
    val over: Boolean,
    @SerializedName("pageCount")
    val pageCount: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("total")
    val total: Int
) {

}