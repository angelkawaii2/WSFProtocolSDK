package dev.raincandy.sdk.websocket.protocol.v1.bean.received;

import dev.raincandy.sdk.websocket.protocol.v1.bean.SendMainBean;
import dev.raincandy.sdk.websocket.protocol.v1.bean.body.BodyBean;

public class ReceivedMainBean extends SendMainBean {


    String sourceClientId;

    BodyBean body;

    transient String originJson = null;

    public ReceivedMainBean(String sourceClientId, BodyBean body) {
        this.sourceClientId = sourceClientId;
        this.body = body;
    }

    /**
     * 因为gson的原因，data作为object在转换时会将int处理为double
     * 如果需要正确的类型数值，应该调用convert转换成对应的javaBean以保证类型正确
     * @return
     */
    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public String getSourceClientId() {
        return sourceClientId;
    }

    public void setSourceClientId(String sourceClientId) {
        this.sourceClientId = sourceClientId;
    }

    /**
     * 判断此消息是否为服务器消息（sourceClientId==server）
     *
     * @return
     */
    public boolean isServerMsg() {
        return "server".equalsIgnoreCase(sourceClientId);
    }
}
