package com.h.alamassi.library.model

class Category(var name: String, var image: String?) {
    var id: Int? = null

    companion object {
        const val TABLE_NAME = "category"
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_IMAGE = "image"

        const val TABLE_CREATE =
            "CREATE TABLE $TABLE_NAME($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_NAME TEXT NOT NULL, $COL_IMAGE TEXT)"
    }
}