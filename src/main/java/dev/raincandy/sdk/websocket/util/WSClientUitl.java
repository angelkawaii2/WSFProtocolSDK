package dev.raincandy.sdk.websocket.util;

import dev.raincandy.sdk.websocket.protocol.ConnectionInfo;
import dev.raincandy.sdk.websocket.websocket.SDKAbstractWebsocketListener;
import okhttp3.*;

import java.util.concurrent.TimeUnit;

/**
 * 创建websocket连接的工具类
 */
public class WSClientUitl {

    /**
     * 默认的okhttp客户端，默认10s的timeout
     */
    private static OkHttpClient client = new OkHttpClient
            .Builder()
            .pingInterval(10, TimeUnit.SECONDS)
            .build();


    public static WebSocket createConnection(SDKAbstractWebsocketListener listener) {
        return createConnection(listener.getWebsocketContext().getConnInfo(), listener);
    }

    /**
     * 建立websocket连接，并进行身份验证
     *
     * @param connInfo 连接信息
     * @param listener 监听器
     * @return websocket对象
     */
    public static WebSocket createConnection(ConnectionInfo connInfo, WebSocketListener listener) {

        if (client == null) {
            initDefaultOkHttpClient();
        }

        var urlb = HttpUrl.parse(connInfo.getServerAddress()).newBuilder();

        urlb.addQueryParameter("token", connInfo.getToken())
                .addQueryParameter("clientId", connInfo.getClientId())
                .addQueryParameter("protocol", "0.6.0")
                .addQueryParameter("timestamp", String.valueOf(System.currentTimeMillis()));

        var req = new Request.Builder()
                .url(urlb.build())
                .build();

        return client.newWebSocket(req, listener);
    }

    public static void setOkHttp(OkHttpClient c) {
        client = c;
    }


    private static void initDefaultOkHttpClient() {
        client = new OkHttpClient
                .Builder()
                .pingInterval(10, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 显式关闭okhttp，避免某些情况需等待自动关闭
     */
    public static void shutdown() {
        client.dispatcher().executorService().shutdown();
        client = null;
    }
}
