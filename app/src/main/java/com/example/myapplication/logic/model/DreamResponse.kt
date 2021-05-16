package com.example.myapplication.logic.model

data class DreamResponse (
    val reason:String,
    var result:List<Dream>,
    var error_code:Int
)

data class Dream(
    val id:String,
    val title:String,
    var des:String,
    val list:List<String>
)
