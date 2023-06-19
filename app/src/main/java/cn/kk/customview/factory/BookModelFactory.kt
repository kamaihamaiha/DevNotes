package cn.kk.customview.factory

import cn.kk.base.utils.AssetsHelper
import cn.kk.customview.MyApp
import cn.kk.customview.bean.BookModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * 课本工厂
 */
class BookModelFactory {


    companion object {

        private val mBooks by lazy {
            getBooks(AssetsHelper.getBooksValue(MyApp.application))
        }

        fun getBooks(bookLisJson: String): MutableList<BookModel> {
            val typeToken = object : TypeToken<List<BookModel>>() {}.type
            return Gson().fromJson<List<BookModel>>(bookLisJson, typeToken).toMutableList()
        }

        fun getBookByAction(itemAction: Int): BookModel {
            for (book in mBooks) {
                if (book.itemAction == itemAction) {
                    return book
                }
            }
            // 找不到，就默认返回第一个
            if (mBooks.isEmpty()) return BookModel("Unknow", 0, mutableListOf())
            return mBooks[0]
        }

        fun createBook(actionBook: Int): BookModel {
            return getBookByAction(actionBook)
        }
    }

}