package dev.raincandy.sdk.websocket.protocol.v1.bean.body.modules.minecraft.command;

import dev.raincandy.sdk.websocket.protocol.v1.bean.body.modules.DataBean;

public class MinecraftCommandDataBean extends DataBean {

    String requestId;
    String source;
    String sender;
    String command;

    public MinecraftCommandDataBean(String requestId, String source, String sender, String command) {
        this.requestId = requestId;
        this.source = source;
        this.sender = sender;
        this.command = command;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
