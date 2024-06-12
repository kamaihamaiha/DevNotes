package cn.kk.base.io.model.netdisk;


/**
 * 发起AI字幕任务/查询是否有AI字幕 的响应
 */
public class NetDiskAISubtitleResponse extends NetDiskUploadSubtitleResponse {

    private static final int PROCESSING_CODE = 1;
    private static final int REGENERATE_SUBTITLE_CODE = -3;

    public NetDiskArticleContent articleContent;

    public boolean showAiTipDialog(){ // 没有添加过AI字幕，需要弹出提示框
        return code == -1;
    }

    public boolean processing() {
        return code == PROCESSING_CODE;
    }

    public boolean needRegenerateSubtitle() {
        return code == REGENERATE_SUBTITLE_CODE;
    }

    public boolean hasSubtitle() {
        return code == 0 && data != null && data.length() > 0;
    }

    public String getSubtitle(){
        articleContent = getArticleContent();
        if (articleContent == null) return "";
        return articleContent.htmlText;
    }

    public String getTranslation(){
        if (articleContent == null) {
            articleContent = getArticleContent();
        }
        if (articleContent == null) return "";
        return articleContent.translation;
    }

    public boolean hasTrans() {
        if (articleContent == null) {
            articleContent = getArticleContent();
        }
        if (articleContent == null) return false;
        return articleContent.hasTranslation();
    }
}
