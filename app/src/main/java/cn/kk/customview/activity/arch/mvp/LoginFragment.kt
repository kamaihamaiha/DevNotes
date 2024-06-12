package cn.kk.customview.activity.arch.mvp

import android.os.Bundle
import android.view.View
import android.widget.Button
import cn.kk.base.fragment.BaseFragment
import cn.kk.customview.R
import cn.kk.customview.activity.arch.mvp.model.LoginInfoModel
import cn.kk.customview.io.URLApi

class LoginFragment: BaseFragment(), LoginContract.View {

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }

    lateinit var mPresenter: LoginContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_login).setOnClickListener {
            mPresenter.login(URLApi.TAOBAO_TEL_URL)
        }
    }

    override fun showLoading() {
        showProgressDialog("loading...")
    }

    override fun hideLoading() {
       hideProgressDialog()
    }

    override fun showError(msg: String) {
        showToast("error: ${msg}")
    }

    override fun isActive(): Boolean {
       return isActive()
    }

    override fun setLoginInfo(loginInfoModel: LoginInfoModel) {
       view?.findViewById<Button>(R.id.btn_login)?.text = loginInfoModel.info
    }

    override fun setPresenter(presenter: LoginContract.Presenter) {
        mPresenter = presenter
    }


}