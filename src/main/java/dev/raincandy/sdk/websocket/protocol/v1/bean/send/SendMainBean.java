package dev.raincandy.sdk.websocket.protocol.v1.bean.send;

import dev.raincandy.sdk.websocket.protocol.v1.ActionEnum;

import java.util.Locale;

public class SendMainBean extends dev.raincandy.sdk.websocket.protocol.v1.bean.SendMainBean {

    String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ActionEnum getActionEnum() {
        try {
            return ActionEnum.valueOf(this.action);
        } catch (Exception e) {
            return ActionEnum.UNDEFINED;
        }
    }

    public void setAction(ActionEnum action) {
        this.action = action.name().toLowerCase(Locale.ROOT);
    }


}
