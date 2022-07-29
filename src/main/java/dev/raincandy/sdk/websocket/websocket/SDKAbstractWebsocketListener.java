package dev.raincandy.sdk.websocket.websocket;

import dev.raincandy.sdk.websocket.protocol.ConnectionInfo;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 20220401
 * u
 */
public abstract class SDKAbstractWebsocketListener extends WebSocketListener {

    private final WebsocketContext context;

    public SDKAbstractWebsocketListener(ConnectionInfo connectionInfo) {
        this.context = new WebsocketContext(this, null, connectionInfo);
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        context.setWebsocket(webSocket);
        context.setConnected(true);
        context.setShutdown(false);
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        context.setConnected(false);
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        if (code == 1000) {
            context.setShutdown(true);
        }
        context.setConnected(false);
    }

    /**
     * 获取当前的websocket连接上下文
     *
     * @return
     */
    public WebsocketContext getWebsocketContext() {
        return context;
    }

    /**
     * 当触发重连时调用此方法，便于子类打日志
     */
    public void onReconnectTrigger() {
    }


}
