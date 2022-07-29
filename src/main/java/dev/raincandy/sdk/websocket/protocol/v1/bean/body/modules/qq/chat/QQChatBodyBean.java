package dev.raincandy.sdk.websocket.protocol.v1.bean.body.modules.qq.chat;

import dev.raincandy.sdk.websocket.protocol.v1.bean.body.BodyBean;

public class QQChatBodyBean extends BodyBean {
    public QQChatBodyBean(QQChatDataBean dataBean) {
        super("qq-chat", 1);
        setData(dataBean);
    }

    @Override
    public QQChatDataBean getData() {
        return super.getData(QQChatDataBean.class);
    }
}
