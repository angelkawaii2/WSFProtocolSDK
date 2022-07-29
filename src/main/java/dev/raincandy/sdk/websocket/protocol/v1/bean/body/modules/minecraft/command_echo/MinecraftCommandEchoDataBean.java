package dev.raincandy.sdk.websocket.protocol.v1.bean.body.modules.minecraft.command_echo;

import dev.raincandy.sdk.websocket.protocol.v1.bean.body.modules.DataBean;

public class MinecraftCommandEchoDataBean extends DataBean {
    String replyId;
    String echo;


    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getEcho() {
        return echo;
    }

    public void setEcho(String echo) {
        this.echo = echo;
    }

    public MinecraftCommandEchoDataBean(String replyId, String echo) {
        this.replyId = replyId;
        this.echo = echo;
    }
}
