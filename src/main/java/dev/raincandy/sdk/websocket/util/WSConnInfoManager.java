package dev.raincandy.sdk.websocket.util;

import dev.raincandy.sdk.websocket.protocol.ConnectionInfo;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理 ConnectionInfo 的工具类，可由第三方使用
 */
public class WSConnInfoManager {
    /**
     * key为connectionId，用户提供
     */
    private static final Map<String, ConnectionInfo> map = new HashMap<>();

    /**
     * 获取connectionInfo
     *
     * @param connectionId 添加时包含在内的connectionId
     * @return
     */
    @Nullable
    public static ConnectionInfo getConnInfo(String connectionId) {
        return map.get(connectionId);
    }

    /**
     * 添加连接信息
     *
     * @param info
     */
    public static void addConnInfo(ConnectionInfo info) {
        map.put(info.getConnectionId(), info);
    }
}
