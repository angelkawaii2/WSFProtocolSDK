package dev.raincandy.sdk.websocket.protocol.v1.bean.body.modules.minecraft.command;

import dev.raincandy.sdk.websocket.protocol.v1.bean.body.BodyBean;

import java.util.UUID;

public class MinecraftCommandBodyBean extends BodyBean {

    public MinecraftCommandBodyBean(Object data) {
        super("minecraft-command", 1);
        setData(data);
    }

    public MinecraftCommandBodyBean(String source, String sender, String command) {
        this(UUID.randomUUID().toString(), source, sender, command);
    }

    public MinecraftCommandBodyBean(String requestId, String source, String sender, String command) {
        this(new MinecraftCommandDataBean(requestId, source, sender, command));
    }

    @Override
    public MinecraftCommandDataBean getData() {
        return super.getData(MinecraftCommandDataBean.class);
    }
}
