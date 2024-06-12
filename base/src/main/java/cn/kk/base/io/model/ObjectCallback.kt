package cn.kk.customview.io.model

interface ObjectCallback<T> {

    fun onResult(result: T)

    fun onFailure(code: Int, ex: Exception?)
}