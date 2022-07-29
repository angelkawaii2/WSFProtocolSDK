package dev.raincandy.sdk.websocket.util;

import dev.raincandy.sdk.websocket.websocket.SDKAbstractWebsocketListener;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 内置的管理监听器的类，可以通过监听器获取到websocket连接
 * 提供基础的重复检测，关闭连接功能
 * 2022-04-03
 * 重构为实例化对象,增加泛型
 * 2022-04-01
 * 增加了线程池用于处理自动重连
 */
public class SDKWebsocketConnManager<T extends SDKAbstractWebsocketListener> {

    private final Map<String, T> listenerMap = new HashMap<>();
    private final ScheduledThreadPoolExecutor AUTO_RECONNECT_WATCHDOG_THREAD_POOL
            = new ScheduledThreadPoolExecutor(10, r -> {
        Thread thread = new Thread(r);
        thread.setName("auto-reconnect-watchdog-thread");
        return thread;
    });

    /**
     * 添加websocket连接 //todo 需要增加一个是否使用重连的选项
     *
     * @param connectionId
     * @param listener
     */
    public void addConnection(String connectionId, T listener) {
        //shutdown重复连接
        Optional.ofNullable(listenerMap.get(connectionId))
                .ifPresent(t -> t.getWebsocketContext().close());
        listenerMap.put(connectionId, listener);
        AUTO_RECONNECT_WATCHDOG_THREAD_POOL.scheduleWithFixedDelay(
                () -> {
                    if (listener.getWebsocketContext().isShutdown()) {
                        return;
                    }
                    if (listener.getWebsocketContext().isConnected()) {
                        return;
                    }
                    WSClientUitl.createConnection(listener.getWebsocketContext().getConnInfo(),
                            listener);
                    listener.onReconnectTrigger();
                }, 0, 10, TimeUnit.SECONDS);
    }

    /**
     * 通过connId获取连接
     *
     * @param connectionId
     * @return
     */
    @Nullable
    public T getConnection(String connectionId) {
        return listenerMap.get(connectionId);
    }

    /**
     * 关闭所有连接，code 1000正常关闭
     */
    public void shutdownAllConnection() {
        for (SDKAbstractWebsocketListener l : listenerMap.values()) {
            l.getWebsocketContext().close();
        }
    }

}
