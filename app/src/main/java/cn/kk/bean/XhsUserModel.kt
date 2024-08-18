package cn.kk.bean

class XhsUserModel(val userId: String, val userName: String) {
    fun getDeepLinkUserHome(): String {
        return "xhsdiscover://user/$userId"
    }
}