package dev.raincandy.sdk.websocket.websocket;

import dev.raincandy.sdk.websocket.protocol.v1.bean.body.modules.minecraft.command_echo.MinecraftCommandEchoBodyBean;
import dev.raincandy.sdk.websocket.protocol.v1.bean.body.modules.minecraft.command_echo.MinecraftCommandEchoDataBean;
import dev.raincandy.sdk.websocket.protocol.v1.bean.send.ForwardBean;

/**
 * //todo 20220221 以后可以改成更通用的回调类
 * 处理 指令回显 的回调类
 * 这里使用了 SDKWebsocketListener ，利用 send 方法内置的队列，避免发送时掉线
 */
public class WSCommandCallback {

    /**
     * 这里的sourceClientId指的是来源端的clientId
     */
    private final String originClientId;
    /**
     * 原始请求的ID，而非本次请求的ID
     * 用于构建消息，target处理回复状态等
     */
    private final String originRequestId;

    private final SDKAbstractWebsocketListener listener;


    public WSCommandCallback(String originClientId, String originRequestId,
                             SDKAbstractWebsocketListener listener) {
        this.originClientId = originClientId;
        this.originRequestId = originRequestId;
        this.listener = listener;
    }


    public void callback(String msg) {
        var fb = new ForwardBean(
                new MinecraftCommandEchoBodyBean(
                        new MinecraftCommandEchoDataBean(originRequestId, msg)));
        //目标客户端ID
        fb.addTargetClientId(originClientId);

        WebsocketContext wsContext = listener.getWebsocketContext();
        wsContext.send(fb);

    }

}
