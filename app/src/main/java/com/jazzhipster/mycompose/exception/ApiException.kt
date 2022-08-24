package com.jazzhipster.mycompose.exception

class ApiException(val msg:String):Exception(msg) {
    override fun toString(): String {
        return "ApiException(msg='$msg')"
    }
}