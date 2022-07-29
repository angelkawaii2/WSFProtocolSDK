package dev.raincandy.sdk.websocket.protocol.v1.bean.send;

import dev.raincandy.sdk.websocket.protocol.v1.ActionEnum;
import dev.raincandy.sdk.websocket.protocol.v1.bean.body.BodyBean;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ForwardBean extends SendMainBean {

    Set<String> targetClientId = new HashSet<>();

    BodyBean body;

    public ForwardBean() {
        super.setAction(ActionEnum.FORWARD);
    }

    public ForwardBean(BodyBean body) {
        this();
        setBody(body);
    }

    public Set<String> getTargetClientId() {
        return targetClientId;
    }

    public void addTargetClientId(Collection<String> targetClientId) {
        this.targetClientId.addAll(targetClientId);
    }
    public void addTargetClientId(String targetClientId){
        this.targetClientId.add(targetClientId);
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

}
