package dev.raincandy.sdk.websocket.websocket;

import dev.raincandy.sdk.websocket.protocol.ConnectionInfo;
import dev.raincandy.sdk.websocket.protocol.v1.bean.SendMainBean;
import okhttp3.WebSocket;
import org.jetbrains.annotations.Nullable;

/**
 * 代表websocket连接的context
 * 警告：不要缓存此对象，需从listener中获取！
 * 仅代表单次会话？
 */
public class WebsocketContext {

    private ConnectionInfo connInfo;
    private SDKAbstractWebsocketListener listener;
    @Nullable
    private WebSocket websocket;
    private boolean isConnected = false;
    private boolean isShutdown = true;

    public WebsocketContext(SDKAbstractWebsocketListener listener,
                            @Nullable WebSocket websocket,
                            ConnectionInfo connInfo) {
        this.listener = listener;
        this.websocket = websocket;
        this.connInfo = connInfo;
    }

    /**
     * //todo 封装一个发送方法？
     * 发送数据
     * <p>
     * 注意，消息会进入websocket缓冲区，会立即返回true，无论缓冲区是否已经发送完毕
     * 当websocket非连接状态时方法会返回false
     * false时应当重新从 listener获取 WebsocketContext
     * 重连需要一定时间
     * <p>
     *
     * @param msgBean
     * @return 与websocket send方法返回一致，如果当前未连接则返回false
     */
    public boolean send(SendMainBean msgBean) {
        if (websocket != null) {
            return websocket.send(msgBean.toString());
        }
        return false;
    }


    public SDKAbstractWebsocketListener getListener() {
        return listener;
    }

    public void setListener(SDKAbstractWebsocketListener listener) {
        this.listener = listener;
    }

    /**
     * 获取raw websocket对象
     *
     * @return
     */
    public @Nullable WebSocket getWebsocket() {
        return websocket;
    }

    public void setWebsocket(@Nullable WebSocket websocket) {
        this.websocket = websocket;
    }

    public ConnectionInfo getConnInfo() {
        return connInfo;
    }

    public void setConnInfo(ConnectionInfo connInfo) {
        this.connInfo = connInfo;
    }

    public void close(int code, String reason) {
        if (websocket != null) {
            websocket.close(code, reason);
        }
    }

    /**
     * 正常关闭
     * code=1000
     */
    public void close() {
        close(1000, "close");
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public boolean isShutdown() {
        return isShutdown;
    }

    public void setShutdown(boolean shutdown) {
        isShutdown = shutdown;
    }

    public boolean isConnected() {
        return isConnected;
    }
}
