package cn.kk.customview.factory

import cn.kk.base.bean.ChapterModel
import cn.kk.base.utils.AssetsHelper
import cn.kk.customview.MyApp
import cn.kk.customview.bean.BookModel
import com.google.gson.Gson

/**
 * 课本工厂
 */
class BookModelFactory {


    companion object {

        private val mBooks by lazy {
            getBooks()
        }

        fun getBooks(): MutableList<BookModel> {
            val bookList = mutableListOf<BookModel>()
            val booksNameList = AssetsHelper.getBooksNameList(MyApp.application)
            for (name in booksNameList) {
                val bookOriginalValue = AssetsHelper.getBookOriginalValue(MyApp.application, name)
                try {
                    val bookModel = Gson().fromJson<BookModel>(bookOriginalValue, BookModel::class.java)
                    bookList.add(bookModel)
                } catch (e: Exception) {
                }
            }
            return bookList
        }

        fun getBookByAction(itemAction: Int): BookModel {
            for (book in mBooks) {
                if (book.itemAction == itemAction) {
                    return book
                }
            }
            // 找不到，就默认返回第一个
            if (mBooks.isEmpty()) return BookModel("Unknown", 0, mutableListOf())
            return mBooks[0]
        }

        fun createBook(actionBook: Int): BookModel {
            return getBookByAction(actionBook)
        }

        // region chapter
        fun getChapterModel(assertFilePath: String): ChapterModel {
            val jsonData = AssetsHelper.getChapterOriginalValue(MyApp.application, assertFilePath)
            return Gson().fromJson(jsonData, ChapterModel::class.java)
        }

        // endregion
    }

}