package cn.wxxlamp.yanque.controller.req;

import cn.wxxlamp.yanque.service.model.DocDetailSerializer;

/**
 * @author chenkai
 * @version 2023/4/9 16:22
 */
public class YuqueWebhookReq {

    private String path;

    private String push;

    private String action_type;

    private DocDetailSerializer docDetailSerializer;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPush() {
        return push;
    }

    public void setPush(String push) {
        this.push = push;
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public DocDetailSerializer getDocDetailSerializer() {
        return docDetailSerializer;
    }

    public void setDocDetailSerializer(DocDetailSerializer docDetailSerializer) {
        this.docDetailSerializer = docDetailSerializer;
    }
}
