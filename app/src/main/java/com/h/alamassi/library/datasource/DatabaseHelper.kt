package com.h.alamassi.library.datasource

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.h.alamassi.library.model.Book
import com.h.alamassi.library.model.Category
import com.h.alamassi.library.model.User

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private lateinit var db: SQLiteDatabase

    override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {
        sqLiteDatabase?.execSQL(Book.TABLE_CREATE)
        sqLiteDatabase?.execSQL(Category.TABLE_CREATE)
        sqLiteDatabase?.execSQL(User.TABLE_CREATE)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, p1: Int, p2: Int) {
        sqLiteDatabase?.execSQL("DROP TABLE IF EXISTS ${Category.TABLE_NAME}")
        sqLiteDatabase?.execSQL("DROP TABLE IF EXISTS ${Book.TABLE_NAME}")
        sqLiteDatabase?.execSQL("DROP TABLE IF EXISTS ${User.TABLE_NAME}")


        sqLiteDatabase?.execSQL(User.TABLE_CREATE)
        sqLiteDatabase?.execSQL(Book.TABLE_CREATE)
        sqLiteDatabase?.execSQL(Category.TABLE_CREATE)
    }

    fun createUser(user: User): Long {
        val contentValues = ContentValues()
        contentValues.put(User.COL_ID, user.id)
        contentValues.put(User.COL_NAME, user.name)
        contentValues.put(User.COL_PASSWORD, user.password)
        contentValues.put(User.COL_IMAGE, user.image ?: "")
        val result = writableDatabase.insert(User.TABLE_NAME, null, contentValues)
        return result
    }

    fun updateUser(user: User): Boolean {
        val contentValues = ContentValues()
        contentValues.put(User.COL_NAME, user.name)
        contentValues.put(User.COL_PASSWORD, user.password)
        contentValues.put(User.COL_IMAGE, user.image ?: "")
        val result = writableDatabase.update(
            User.TABLE_NAME,
            null,
            "${User.COL_ID} =?",
            arrayOf(user.id.toString())
        )
        return result != -1
    }

    fun deleteUser(userId: Int): Boolean {
        val result = writableDatabase.delete(
            User.TABLE_NAME,
            "${User.COL_ID} =?",
            arrayOf(userId.toString())
        )
        return result != -1
    }

    @SuppressLint("Recycle")
    fun getUser(userId: Long): User? {
        val query = readableDatabase.rawQuery(
            "SELECT * FROM ${User.TABLE_NAME} WHERE ${User.COL_ID} =? LIMIT 1",
            arrayOf(userId.toString())
        )
        val idIndex = query.getColumnIndex(User.COL_ID)
        val nameIndex = query.getColumnIndex(User.COL_NAME)
        val passwordIndex = query.getColumnIndex(User.COL_PASSWORD)
        val imageIndex = query.getColumnIndex(User.COL_IMAGE)

        query.moveToFirst()
        var user: User? = null

        while (!query.isAfterLast) {
            val id = query.getInt(idIndex)
            val name = query.getString(nameIndex)
            val password = query.getString(passwordIndex)
            val image = query.getString(imageIndex)
            user = User(name, password, image)
            user.id = id
            query.moveToNext()
        }
        return user
    }

    @SuppressLint("Recycle")
    fun getAllUsers(): MutableList<User> {
        val query = readableDatabase.rawQuery(
            "SELECT * FROM ${User.TABLE_NAME}", null
        )
        val idIndex = query.getColumnIndex(User.COL_ID)
        val nameIndex = query.getColumnIndex(User.COL_NAME)
        val passwordIndex = query.getColumnIndex(User.COL_PASSWORD)
        val imageIndex = query.getColumnIndex(User.COL_IMAGE)

        query.moveToFirst()
        val users: MutableList<User> = mutableListOf()

        while (!query.isAfterLast) {
            val id = query.getInt(idIndex)
            val name = query.getString(nameIndex)
            val password = query.getString(passwordIndex)
            val image = query.getString(imageIndex)

            val user = User(name, password, image)
            user.id = id
            users.add(user)
            query.moveToNext()
        }
        return users
    }

    @SuppressLint("Recycle")
    fun authUser(nameValue: String, passwordValue: String): User? {
        val query = readableDatabase.rawQuery(
            "SELECT * FROM ${User.TABLE_NAME} WHERE ${User.COL_NAME} =? AND ${User.COL_PASSWORD}=? LIMIT 1",
            arrayOf(nameValue, passwordValue)
        )
        val idIndex = query.getColumnIndex(User.COL_ID)
        val nameIndex = query.getColumnIndex(User.COL_NAME)
        val passwordIndex = query.getColumnIndex(User.COL_PASSWORD)
        val imageIndex = query.getColumnIndex(User.COL_IMAGE)

        query.moveToFirst()
        var user: User? = null

        while (!query.isAfterLast) {
            val id = query.getInt(idIndex)
            val name = query.getString(nameIndex)
            val password = query.getString(passwordIndex)
            val image = query.getString(imageIndex)
            user = User(name, password, image)
            user.id = id
            query.moveToNext()
        }
        return user
    }


    fun insertBook(book: Book): Long {
        val contentValues = ContentValues()
        contentValues.put(Book.COL_ID, book.id)
        contentValues.put(Book.COL_NAME, book.name)
        contentValues.put(Book.COL_AUTHOR, book.author)
        contentValues.put(Book.COL_CATEGORY_ID, book.categoryId)
        contentValues.put(Book.COL_DESCRIPTION, book.description)
        contentValues.put(Book.COL_LANGUAGE, book.language)
        contentValues.put(Book.COL_NUMBER_OF_COPIES, book.numOfCopies)
        contentValues.put(Book.COL_NUMBER_OF_PAGES, book.numOfPages)
        contentValues.put(Book.COL_SHELF, book.shelf)
        contentValues.put(Book.COL_YEAR, book.year)
        contentValues.put(Book.COL_IMAGE, book.image ?: "")
        return writableDatabase.insert(Book.TABLE_NAME, null, contentValues)
    }

    fun updateBook(book: Book): Boolean {
        val contentValues = ContentValues()
        contentValues.put(Book.COL_ID, book.id)
        contentValues.put(Book.COL_NAME, book.name)
        contentValues.put(Book.COL_AUTHOR, book.author)
        contentValues.put(Book.COL_CATEGORY_ID, book.categoryId)
        contentValues.put(Book.COL_DESCRIPTION, book.description)
        contentValues.put(Book.COL_LANGUAGE, book.language)
        contentValues.put(Book.COL_NUMBER_OF_COPIES, book.numOfCopies)
        contentValues.put(Book.COL_NUMBER_OF_PAGES, book.numOfPages)
        contentValues.put(Book.COL_SHELF, book.shelf)
        contentValues.put(Book.COL_YEAR, book.year)
        contentValues.put(Book.COL_IMAGE, book.image ?: "")
        val result = writableDatabase.update(
            Book.TABLE_NAME,
            null,
            "${Book.COL_ID} =?",
            arrayOf(book.id.toString())
        )
        return result != -1
    }

    fun deleteBook(bookId: Long): Boolean {
        val result = writableDatabase.delete(
            Book.TABLE_NAME,
            "${Book.COL_ID} =?",
            arrayOf(bookId.toString())
        )
        return result != -1
    }


    @SuppressLint("Recycle")
    fun getCategoryBooks(categoryId: Long): ArrayList<Book> {
        val query = readableDatabase.rawQuery(
            "SELECT * FROM ${Book.TABLE_NAME} WHERE ${Book.COL_CATEGORY_ID} = ?",
            arrayOf(categoryId.toString())
        )
        val idIndex = query.getColumnIndex(Book.COL_ID)
        val nameIndex = query.getColumnIndex(Book.COL_NAME)
        val authorIndex = query.getColumnIndex(Book.COL_AUTHOR)
        val categoryIdIndex = query.getColumnIndex(Book.COL_CATEGORY_ID)
        val descriptionIndex = query.getColumnIndex(Book.COL_DESCRIPTION)
        val yearIndex = query.getColumnIndex(Book.COL_YEAR)
        val shelfIndex = query.getColumnIndex(Book.COL_SHELF)
        val languageIndex = query.getColumnIndex(Book.COL_LANGUAGE)
        val imageIndex = query.getColumnIndex(User.COL_IMAGE)
        val numberOfCopiesIndex = query.getColumnIndex(Book.COL_NUMBER_OF_COPIES)
        val numberOfPagesIndex = query.getColumnIndex(Book.COL_NUMBER_OF_PAGES)

        query.moveToFirst()
        val books: ArrayList<Book> = ArrayList()

        while (!query.isAfterLast) {
            val id = query.getLong(idIndex)
            val name = query.getString(nameIndex)
            val author = query.getString(authorIndex)
            val year = query.getString(yearIndex)
            val categoryId = query.getLong(categoryIdIndex)
            val description = query.getString(descriptionIndex)
            val language = query.getString(languageIndex)
            val numberOfPages = query.getString(numberOfPagesIndex)
            val numberOfCopies = query.getString(numberOfCopiesIndex)
            val shelf = query.getString(shelfIndex)
            val image = query.getString(imageIndex)


            val book = Book(
                name,
                author,
                year,
                categoryId,
                description,
                language,
                numberOfPages,
                numberOfCopies,
                shelf,
                image
            )
            book.id = id
            books.add(book)
            query.moveToNext()
        }
        return books
    }


    fun createCategory(category: Category): Long {
        val contentValues = ContentValues()
        contentValues.put(Category.COL_NAME, category.name)
        contentValues.put(Category.COL_IMAGE, category.image ?: "")
        return writableDatabase.insert(Category.TABLE_NAME, null, contentValues)
    }

//    fun updateCategory(category: Category): Boolean {
//        val contentValues = ContentValues()
//        contentValues.put(Category.COL_NAME, category.name)
//        contentValues.put(Category.COL_IMAGE, category.image ?: "")
//
//        val result = writableDatabase.update(
//            Category.TABLE_NAME,
//            null,
//            "${Category.COL_ID} =?",
//            arrayOf(category.id.toString())
//        )
//        return result != -1
//    }

    fun deleteCategory(categoryId: Int): Boolean {
        val result = writableDatabase.delete(
            Category.TABLE_NAME,
            "${User.COL_ID} =?",
            arrayOf(categoryId.toString())
        )
        return result != -1
    }

//    @SuppressLint("Recycle")
//    fun getCategory(categoryId: Int): Category? {
//        val query = readableDatabase.rawQuery(
//            "SELECT * FROM ${Category.TABLE_NAME} WHERE ${Category.COL_ID} =?",
//            arrayOf(categoryId.toString())
//        )
//        val idIndex = query.getColumnIndex(Category.COL_ID)
//        val nameIndex = query.getColumnIndex(Category.COL_NAME)
//        val imageIndex = query.getColumnIndex(Category.COL_IMAGE)
//
//        query.moveToFirst()
//        var category: Category? = null
//
//        while (!query.isAfterLast) {
//            val id = query.getInt(idIndex)
//            val name = query.getString(nameIndex)
//            val image = query.getString(imageIndex)
//            category = Category(name, image)
//            category.id = id
//            query.moveToNext()
//        }
//        return category
//    }

    fun getAllCategories(): ArrayList<Category> {
        val query = readableDatabase.rawQuery(
            "SELECT * FROM ${Category.TABLE_NAME}", null
        )
        val idIndex = query.getColumnIndex(Category.COL_ID)
        val nameIndex = query.getColumnIndex(Category.COL_NAME)
        val imageIndex = query.getColumnIndex(Category.COL_IMAGE)

        query.moveToFirst()
        val categories: ArrayList<Category> = ArrayList()

        while (!query.isAfterLast) {
            val id = query.getInt(idIndex)
            val name = query.getString(nameIndex)
            val image = query.getString(imageIndex)

            val category = Category(name, image)
            category.id = id
            categories.add(category)
            query.moveToNext()
        }
        return categories
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "LibraryDB"
    }
}

