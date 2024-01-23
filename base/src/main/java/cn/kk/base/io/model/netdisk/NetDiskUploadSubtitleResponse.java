package cn.kk.base.io.model.netdisk;

import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

public class NetDiskUploadSubtitleResponse {
    public String id;
    public String msg;
    public int code = -1;
    public String data; // article 的 html & translation 信息

    public NetDiskArticleContent getArticleContent() {
        NetDiskArticleContent articleContent = new NetDiskArticleContent();
        try {

            JSONObject obj = new JSONObject(data);
            articleContent.htmlText = obj.getString("HtmlText");
            // 数据结构和 ArticleModel 的 translation 一样
            JSONObject subtitle = obj.getJSONObject("Subtitle");
            articleContent.translation = subtitle.toString();

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return articleContent;
    }
}
