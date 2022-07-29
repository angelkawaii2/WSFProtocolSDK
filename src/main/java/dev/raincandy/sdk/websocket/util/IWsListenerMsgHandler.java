package dev.raincandy.sdk.websocket.util;

import dev.raincandy.sdk.websocket.protocol.v1.bean.received.ReceivedMainBean;
import dev.raincandy.sdk.websocket.websocket.SDKAbstractWebsocketListener;

/**
 * 本接口用于向 AbstractSDKWebsocketListener 注册 消息处理类，便于解耦消息处理与 listener
 * 本类建议所有Handler实现，可使用 #MessageHandler 类辅助 WebsocketListener 处理onMsg事件
 */
public interface IWsListenerMsgHandler {

    /**
     * 处理onMessage事件
     *
     * @param context    上下文的websocket监听器
     * @param mainBean   由监听器解析后的MainBean消息对象
     * @param originText 原始消息，便于特殊情况下的处理
     */
    void onMessage(SDKAbstractWebsocketListener context, ReceivedMainBean mainBean, String originText);
}
