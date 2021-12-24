package com.h.alamassi.library.model

data class Book(
    var name: String,
    var author: String,
    var year: String,
    var categoryId: Long,
    var description: String,
    var language: String,
    var numOfPages: String,
    var numOfCopies: String,
    var shelf: String,
    var image: String?
) {


    var id: Long? = null

    companion object {
        const val TABLE_NAME = "book"
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_AUTHOR = "author"
        const val COL_YEAR = "year"
        const val COL_CATEGORY_ID = "category_id"
        const val COL_DESCRIPTION = "description"
        const val COL_LANGUAGE = "language"
        const val COL_NUMBER_OF_PAGES = "number_of_pages"
        const val COL_IMAGE = "image"
        const val COL_NUMBER_OF_COPIES = "number_of_copies"
        const val COL_SHELF = "shelf"
        const val TABLE_CREATE =
            "CREATE TABLE $TABLE_NAME($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_NAME TEXT NOT NULL, $COL_AUTHOR TEXT NOT NULL, $COL_YEAR INTEGER NOT NULL, " +
                    "$COL_CATEGORY_ID INTEGER NOT NULL, $COL_DESCRIPTION TEXT ,$COL_LANGUAGE TEXT NOT NULL, $COL_IMAGE TEXT, " +
                    "$COL_NUMBER_OF_PAGES INTEGER NOT NULL, $COL_NUMBER_OF_COPIES INTEGER NOT NULL,$COL_SHELF TEXT NOT NULL, " +
                    "FOREIGN KEY ($COL_CATEGORY_ID) REFERENCES ${Category.TABLE_NAME} (${Category.COL_ID}) )"

    }
}