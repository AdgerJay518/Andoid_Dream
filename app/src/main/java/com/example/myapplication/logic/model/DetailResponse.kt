package com.example.myapplication.logic.model

data class DetailResponse (
    val reason:String,
    val result:Detail,
    var error_code:Int
    ){
    data class Detail(
        val id:String,
        val title:String,
        var des:String,
        val list:List<String>
    )
}
