package cn.kk.base.fragment

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cn.kk.base.R
import cn.kk.base.UIHelper
import cn.kk.base.activity.BaseActivity
import cn.kk.base.bean.ListItemAction
import java.io.Serializable

abstract class BaseFragment: Fragment() {

    val TAG = this.javaClass.simpleName
    protected lateinit var rootView: View
    protected lateinit var mProgressDialog: ProgressDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(getLayoutId(), container, false)
        mProgressDialog = ProgressDialog(context)
        return rootView
    }

    abstract fun getLayoutId(): Int

    open fun getItemActionList(names: Array<String>): MutableList<ListItemAction> {
        val list = mutableListOf<ListItemAction>()
        for (name in names) {
            list.add(ListItemAction(name))
        }
        return list
    }

    protected open fun getTestStringArray(): Array<String> {
        return resources.getStringArray(R.array.test_list)
    }

    /**
     * 生成测试数据
     * @param count: 数据量
     */
    protected open fun getTestStringList(count: Int): MutableList<String> {
        val items = mutableListOf<String>()
        for (index in 1 .. count) {
            items.add(index.toString())
        }
        return items;
    }

    protected fun showToast(msg: String){
        if (context == null) return
        UIHelper.toast(msg, context!!)
    }

    protected fun showProgressDialog(msg: String){
        mProgressDialog.setCancelable(true)
        mProgressDialog.setMessage(msg)
        mProgressDialog.show()
    }

    protected fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }


    protected fun <T: Activity> startNextUI(targetActivity: Class<T>, title: String){
        (activity as BaseActivity).openNextUI(targetActivity, title)
    }

    protected fun <T: Activity> startNextUI(targetActivity: Class<T>, title: String, type: Int){
        (activity as BaseActivity).openNextUI(targetActivity, title, type)
    }


    open fun <T: Activity> startNextUI(targetActivity: Class<T>, title: String, type: Int, webUrl: String){
        (activity as BaseActivity).openNextUI(targetActivity, title, type, webUrl)
    }


    open fun <T: Activity, K: Serializable> startNextUI(targetActivity: Class<T>, title: String, model: K){
        (activity as BaseActivity).openNextUI(targetActivity, title, model)
    }
}