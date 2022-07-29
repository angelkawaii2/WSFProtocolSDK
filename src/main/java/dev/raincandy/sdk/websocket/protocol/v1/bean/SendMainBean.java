package dev.raincandy.sdk.websocket.protocol.v1.bean;

import dev.raincandy.sdk.websocket.util.GsonUtil;


public abstract class SendMainBean {


    long timestamp = System.currentTimeMillis();

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) throws IllegalArgumentException {
        if (timestamp < 0) {
            throw new IllegalArgumentException("timestamp cannot be less than 0");
        }
        this.timestamp = timestamp;
    }

    /**
     * 默认实现的序列化，如果子类有特殊需求再自行实现？
     * 可能要自定义gson
     *
     * @return
     */
    @Override
    public String toString() {
        return GsonUtil.getGson().toJson(this);
    }
}
