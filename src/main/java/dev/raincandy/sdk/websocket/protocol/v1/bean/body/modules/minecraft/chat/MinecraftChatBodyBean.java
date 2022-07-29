package dev.raincandy.sdk.websocket.protocol.v1.bean.body.modules.minecraft.chat;

import dev.raincandy.sdk.websocket.protocol.v1.bean.body.BodyBean;

public class MinecraftChatBodyBean extends BodyBean {

    public MinecraftChatBodyBean(MinecraftChatDataBean dataBean) {
        super("minecraft-chat", 1);
        setData(dataBean);
    }

    @Override
    public MinecraftChatDataBean getData() {
        return super.getData(MinecraftChatDataBean.class);
    }
}
