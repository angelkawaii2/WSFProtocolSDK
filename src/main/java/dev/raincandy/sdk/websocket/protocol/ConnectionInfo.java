package dev.raincandy.sdk.websocket.protocol;

import java.util.Objects;

/**
 * 此类用于存储连接信息
 */
public class ConnectionInfo {
    /**
     * 连接id，可以自己起名
     * todo 注意这里不同的连接必须起不同的名称？
     */
    String connectionId;
    /**
     * 服务器地址
     */
    String serverAddress;
    /**
     * token，身份验证
     */
    String token;
    /**
     * 客户端ID，用于服务器识别
     */
    String clientId;

    public ConnectionInfo(String connectionId, String serverAddress, String token, String clientId) {
        this.connectionId = connectionId;
        this.serverAddress = serverAddress;
        this.token = token;
        this.clientId = clientId;
    }


    public String getConnectionId() {
        return connectionId;
    }


    public String getServerAddress() {
        return serverAddress;
    }


    public String getToken() {
        return token;
    }


    public String getClientId() {
        return clientId;
    }

    @Override
    public String toString() {
        return "ConnectionInfo{" +
                "connectionId='" + connectionId + '\'' +
                ", serverAddress='" + serverAddress + '\'' +
                ", token='" + token + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectionInfo that = (ConnectionInfo) o;
        return Objects.equals(connectionId, that.connectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(connectionId);
    }
}
