package cn.kk.customview.factory

import cn.kk.base.bean.SectionDetailModel
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

        fun getBookByType(bookType: Int): BookModel {
            for (book in mBooks) {
                if (book.bookType == bookType) {
                    return book
                }
            }
            // 找不到，就默认返回第一个
            if (mBooks.isEmpty()) return BookModel("Unknown", 0, mutableListOf())
            return mBooks[0]
        }

        fun createBook(bookType: Int): BookModel {
            return getBookByType(bookType)
        }

        // region section
        fun getSectionDetailModel(assertFilePath: String?): SectionDetailModel {
            assertFilePath?.let {
                try {
                    val jsonData = AssetsHelper.getSectionOriginalValue(MyApp.application, it)
                    return Gson().fromJson(jsonData, SectionDetailModel::class.java)
                } catch (e: Exception) {
                    return SectionDetailModel()
                }
            }
            return SectionDetailModel()
        }

        // endregion
    }

}