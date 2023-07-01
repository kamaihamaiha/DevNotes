package cn.kk.base.bean;

import java.util.List;

public class ChapterModel {

    public List<NodeModel> nodeModelList;
    public String title;

    public static class NodeModel {
        public String title;
        public String index;
        public String content;
    }
}
