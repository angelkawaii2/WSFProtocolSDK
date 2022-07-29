# Websocket Forward SDK - Java

基于 [Websocket Forward SDK](https://github.com/angelkawaii2/RaincandyWsForwardProtocol)
的消息转发协议 SDK

具体协议参考 [protocol.md](protocol.md)

## Version

v1.0.0

## Requirements

- Java 17+

## To-do

- [ ] reformat error code
- [ ] 给 requestId 和 replyId 的 data类 实现一个标识接口
- [ ] API接口供第三方调用
- [ ] 支持加密消息body

## Usage

结构:

``protocol``包: 定义协议所需的bean

``util``包: 定义工具类

``websocket``包: 定义websocket相关的类

```java

class test {
    public static void main(String[] args) {
        //启动时先注册对应的消息bean
        DataTypeClassRegister.registerDataType("minecraft-chat", MinecraftChatDataBean.class);
        DataTypeClassRegister.registerDataType("minecraft-command", MinecraftCommandDataBean.class);
        DataTypeClassRegister.registerDataType("qq-chat", QQChatDataBean.class);

        //创建连接配置
        var connInfo = new ConnectionInfo(connectionId, url
                , setting.getToken(), setting.getClientId());

        //可选：使用内置的类管理connInfo对象
        WSConnInfoManager.addConnInfo(connInfo);

        //可选: websocket消息的解析器工具类
        MessageHandler msgHandler = new MessageHandler();
        //注册解析qq聊天和mc指令
        msgHandler.registerType("qq-chat", new BukkitQQChatHandler(this));
        msgHandler.registerType("minecraft-command", new BukkitMcCmdHandler(this));

        //创建监听器
        var wsListener = new ExampleListener(connInfo);
        //可选:设置处理onMsg事件的消息解析器（需要自己实现getter/setter方法）
        wsListener.setMsgHandler(msgHandler);
        //与服务器建立连接（注意第一次连接时必须显式调用，否则 WSConnInfoManager 将视为此连接尚未初始化而不处理重连(和连接）逻辑
        WSClientUitl.createConnection(connInfo, wsListener);

        //可选：添加连接到manager，便于一键关闭
        //此工具类还提供了自动重连的处理，推荐使用
        SDKWebsocketConnManager.addConnection(connInfo.getID(), wsListener);

    }
}


```

监听器

```java
/**
 *继承 AbstractWebSocketListener，用于sdk的处理
 */
public class ExampleListener extends AbstractWebSocketListener {

    /**
     * 可选，用于解耦消息解析和处理器
     */
    private MessageHandler msgHandler = new MessageHandler();

    private final Logger logger;

    public ExampleListener(ConnectionInfo connectionInfo) {
        super(connectionInfo);
    }

    /**
     * 注意调用super的方法，否则父类无法处理上下文的更新
     * @param webSocket
     * @param response
     */
    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super(webSocket, response);
        //更新websocket对象
        this.context = new WebsocketContext(this, webSocket, connInfo);
        logger.info(String.format("连接 %s 已建立", connInfo.getConnectionId()));
    }

    /**
     * 注意调用super的方法，否则父类无法处理上下文的更新
     * @param webSocket
     * @param t
     * @param response
     */
    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        super(webSocket, t, response);
        logger.warn(String.format("连接: %s 出现错误已断线", connInfo.getConnectionId()));
        //如果使用了 SDKWebsocketConnManager 可以不需要手动重连，会有WatchDog线程自动处理重连
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        logger.info("连接: %s 被服务器关闭，理由: " + reason);
        //非正常原因关闭的，需要重连
        switch (code) {
            case 1000 -> logger.info(connInfo.getConnectionId() + " 1000 连接正常关闭，不执行重连。");
            case 4010 -> logger.warn(connInfo.getConnectionId() + " 4010 身份验证失败！" + reason);
            case 5000 -> logger.warn(connInfo.getConnectionId() + " 5000 服务器错误: " + reason);
            default -> logger.warn(connInfo.getConnectionId() + " " + code + " 其他错误: " + reason);
        }
        //如果使用了 SDKWebsocketConnManager 可以不需要手动重连，会有WatchDog线程自动处理重连
        //reconnect();
    }


    /**
     * 在这里处理消息解析
     * @param webSocket
     * @param text
     */
    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        try {
            var mb = GsonUtil.getGson().fromJson(text, ReceivedMainBean.class);
            msgHandler.handleMsg(this, mb, text);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            logger.warn("解析错误，无法正确解析的json: \n" + text);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("处理时发生其他异常: \n" + text);
        }
    }

}

```
