package dev.raincandy.sdk.websocket.protocol.v1.bean.body.modules.qq.chat;

import dev.raincandy.sdk.websocket.protocol.v1.bean.body.modules.DataBean;

public class QQChatDataBean extends DataBean {

    private String requestId;
    private String replyId = null;
    private Long senderQQ = 0L;
    private Long group = 0L;
    private String senderName;
    private String msg;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public boolean isAnonymousQQ() {
        return getSenderQQ() == 0;
    }

    public Long getSenderQQ() {
        return senderQQ;
    }

    public void setSenderQQ(Long senderQQ) {
        this.senderQQ = senderQQ;
    }

    public boolean isGroupChat() {
        return getGroup() != 0;
    }

    public Long getGroup() {
        return group;
    }

    public void setGroup(Long group) {
        this.group = group;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
