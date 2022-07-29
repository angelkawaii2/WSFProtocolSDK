package dev.raincandy.sdk.websocket.util;

import dev.raincandy.sdk.websocket.protocol.v1.bean.received.ReceivedMainBean;
import dev.raincandy.sdk.websocket.websocket.SDKAbstractWebsocketListener;

import java.util.*;

/**
 * 功能类
 * 可在onMsg（）调用handler（）方法，根据消息类型选择不同的处理类
 */
public class MessageHandler {

    /**
     * key 是消息类型，value是存放 handler 的 链表，处理消息时遍历链表
     */
    private final Map<String, List<IWsListenerMsgHandler>> map = new HashMap<>(8);


    /**
     * 注册消息处理器
     *
     * @param type    消息type
     * @param handler 消息handler
     */
    public void registerType(String type, IWsListenerMsgHandler handler) {
        if (!map.containsKey(type)) {
            map.put(type, new LinkedList<>());
        }
        map.get(type).add(handler);
    }

    /**
     * 处理消息
     *
     * @param context    websocket listener
     * @param mainBean   解析过的消息 Mainbean
     * @param originText 原始消息数据，如果需要额外处理可用上
     */
    public void handleMsg(SDKAbstractWebsocketListener context, ReceivedMainBean mainBean, String originText) {
        var type = mainBean.getBody().getType();
        if (map.containsKey(type)) {
            for (IWsListenerMsgHandler handler : map.get(type)) {
                handler.onMessage(context, mainBean, originText);
            }
        }
    }

    //todo
    private void handleBinary() {

    }
}
