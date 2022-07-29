package dev.raincandy.sdk.websocket.protocol.v1.bean.body.modules.server;

import dev.raincandy.sdk.websocket.protocol.v1.bean.body.BodyBean;

public class ServerResponseBean extends BodyBean {
    public ServerResponseBean(String responseDataBean) {
        super("server-response", 1);
        setData(responseDataBean);
    }

    @Override
    public ServerResponseDataBean getData() {
        return super.getData(ServerResponseDataBean.class);
    }
}
