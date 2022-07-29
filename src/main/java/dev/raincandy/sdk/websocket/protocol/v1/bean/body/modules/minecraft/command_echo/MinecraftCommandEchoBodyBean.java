package dev.raincandy.sdk.websocket.protocol.v1.bean.body.modules.minecraft.command_echo;

import dev.raincandy.sdk.websocket.protocol.v1.bean.body.BodyBean;

public class MinecraftCommandEchoBodyBean extends BodyBean {

    public MinecraftCommandEchoBodyBean() {
        super("minecraft-command-echo", 1);
    }

    public MinecraftCommandEchoBodyBean(MinecraftCommandEchoDataBean data) {
        this();
        setData(data);
    }

    @Override
    public Object getData() {
        return super.getData(MinecraftCommandEchoDataBean.class);
    }
}
