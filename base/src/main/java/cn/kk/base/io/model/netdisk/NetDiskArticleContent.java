package cn.kk.base.io.model.netdisk;

import android.text.TextUtils;

public class NetDiskArticleContent {

    public String htmlText;
    public String translation;


    public boolean hasTranslation(){ // subtitle 里的译文数据
        if (TextUtils.isEmpty(translation)) return false;
        return false;
    }
}
