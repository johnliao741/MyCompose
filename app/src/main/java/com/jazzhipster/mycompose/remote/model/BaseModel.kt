package com.jazzhipster.mycompose.remote.model

import com.google.gson.annotations.SerializedName

class BaseModel<T>(
    @SerializedName("errorCode")
    val status:Int,
    @SerializedName("errorMsg")
    val errorMsg:String,
    val data:T
) {
}