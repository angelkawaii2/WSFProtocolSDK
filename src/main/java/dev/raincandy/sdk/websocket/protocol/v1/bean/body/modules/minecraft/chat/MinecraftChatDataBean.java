package dev.raincandy.sdk.websocket.protocol.v1.bean.body.modules.minecraft.chat;

import dev.raincandy.sdk.websocket.protocol.v1.bean.body.modules.DataBean;

public class MinecraftChatDataBean extends DataBean {

    private String requestId;
    private String replyId;
    /**
     * todo 20210816 不应该实现在这里，移植很麻烦
     */
    @Deprecated
    private SourceDTO source;
    /**
     * todo 20210816 不应该实现在这里，移植很麻烦
     */
    @Deprecated
    private SenderDTO sender;
    private String msg;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public boolean hasReplyId() {
        return replyId != null && !"".equals(replyId);
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public SourceDTO getSource() {
        return source;
    }

    public void setSource(SourceDTO source) {
        this.source = source;
    }

    public SenderDTO getSender() {
        return sender;
    }

    public void setSender(SenderDTO sender) {
        this.sender = sender;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * todo 20210816 不应该实现在这里，移植很麻烦
     */
    public static class SourceDTO {
        private String worldName;
        private String worldUid;
        private String worldEnvironment;

        public String getWorldName() {
            return worldName;
        }

        public void setWorldName(String worldName) {
            this.worldName = worldName;
        }

        public String getWorldUid() {
            return worldUid;
        }

        public void setWorldUid(String worldUid) {
            this.worldUid = worldUid;
        }

        public String getWorldEnvironment() {
            return worldEnvironment;
        }

        public void setWorldEnvironment(String worldEnvironment) {
            this.worldEnvironment = worldEnvironment;
        }
    }

    /**
     * todo 20210816 不应该实现在这里，移植很麻烦
     */
    public static class SenderDTO {
        private String uuid;
        private String displayName;
        private Boolean isOp;
        private Boolean isWhitelist;
        private Integer level;
        private Double hp;
        private Integer ping;


        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public Boolean getIsOp() {
            return isOp;
        }

        public void setIsOp(Boolean isOp) {
            this.isOp = isOp;
        }

        public Boolean getIsWhitelist() {
            return isWhitelist;
        }

        public void setIsWhitelist(Boolean isWhitelist) {
            this.isWhitelist = isWhitelist;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public Double getHp() {
            return hp;
        }

        public void setHp(Double hp) {
            this.hp = hp;
        }

        public Integer getPing() {
            return ping;
        }

        public void setPing(Integer ping) {
            this.ping = ping;
        }
    }
}
