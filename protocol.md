# Websocket Forward Protocol

last update: 20220221

客户端数据格式

开发协议版本: v1.0.0

------------

## 客户端

### 发送数据

字段:

| 字段         | 类型     | 说明                                 |
|------------|--------|------------------------------------|
| action     | string | 标识请求类型，值为(``forward``/``command``) |
| encryption | object | 对称加密(可选)                           |

#### 转发数据(forward)

```json5
{
  "action": "forward",
  //请求服务器转发
  "timestamp": 1145141919,
  //客户端发送时的时间戳
  "targetClientId": [
    //目标客户端ID
    "client-mc-server1",
    "client-mc-server2"
  ],
  "encryption": {
    "method": "AES",
    "key": ""
  },
  "body": {
    //数据
  }
}
```

### 接收数据

```json5
{
  "sourceClientId": "client-c1",
  //消息来源
  "timestamp": 1145141919,
  //客户端发送时的时间戳
  //加密方案
  "encryption": {
    "method": "AES",
    "key": ""
  },
  "body": {
    //数据
  }
}
```

-------------

## 服务端

服务端转发时应移除无关和敏感信息，转发发送的消息格式参考上文 【接收数据】

服务端通常情况下不需要处理body部分，只负责原样转发

### 认证

服务端对于认证成功的客户端应该响应``2000`` (见下文)

客户端应在第一个switch protocol阶段进行认证，由http header携带数据

必要字段:

| 字段       | 数据                       |
|----------|--------------------------|
| clientId | 客户端id, e.g "myclient-01" |
| protocol | 协议版本, e.g "v1.3.1"       |

注意：服务器应该禁止``clientId``为``server``的客户端进行认证，返回4030错误码

### 响应

响应复用上文客户端【接收数据】部分

body:

```json5
{
  "sourceClientId": "server",
  //注意这里要改成server
  "timestamp": 1145141919,
  //服务器时间戳
  "body": {
    "type": "server-response",
    "version": 1,
    "data": {
      "code": 2000,
      "msg": "ok"
    }
  }
}
```

#### 服务端指令(draft)

暂时不用实现，有条件预留设计

获取所有已连接的客户端列表  
获取延迟  
获取最新的协议版本信息  
etc..

#### 错误码

一般情况下仅在错误情况下返回消息，部分场景视情况需要切断连接

see:  [rfc6455-The WebSocket Protocol](https://datatracker.ietf.org/doc/html/rfc6455#section-7.4)

错误码由 http code + 自定义code 组成

消息走send(msg)，部分需要关闭连接的发送后再调用close(code,msg)

```json5
{
  "code": 4000,
  "msg": "xx",
  "data": {}
  //可选字段，部分场景需要data补充信息
}
```

msg仅用于描述错误信息，可能随时改变

| code | msg                           | 切断连接 | desc                   |
|------|-------------------------------|------|------------------------|
| 2000 | OK                            | x    | 请求成功处理                 |
| 4000 | Bad Request                   | x    | 请求格式错误                 |
| 4001 | Bad Request-outdated-protocol | √    | 协议版本已经过时(protocol字段)   |
| 4010 | Unauthorized                  | √    | 身份验证格式错误或解析失败          |
| 4011 | Unauthorized-clientId         | √    | 未授权的clientId           |
| 4012 | Unauthorized-token            | √    | token验证失败              |
| 4013 | Unauthorized-target           | x    | 权限不足，消息无法转发到目标客户端(预留)  |
| 4030 | Forbidden-clientId            | √    | 被禁止的客户端id，可能是预留ID      |
| 4031 | Duplicate-clientId-registered | √    | 已有同名的客户端成功建立了连接，此连接被关闭 |
| 4290 | Too Many Requests             | 待定   | 请求过于频繁(预留)             |
| 5000 | Internal Server Error         | 待定   | 服务器错误                  |

## Body

body由``type``和``data``组成，其中``type``用于客户端自己识别数据内容

body可以自行实现，对于无法完成的请求，客户端视情况决定是否告知请求来源

### 模块化

> 20220403注意：新版本的body可能加密，服务端应判断是否存在加密字段

body部分可根据实际情况自行实现或拓展，服务端原则上不需要解析此部分（除非限制转发类型）

以下是必要字段：

| 字段      | 说明   | 类型            |
|---------|------|---------------|
| type    | 类型   | string        |
| version | 协议版本 | int(unsigned) |
| data    | 数据   | json object   |

#### minecraft-chat 聊天转发

从 Minecraft 客户端发送的消息

```json5
{
  "type": "minecraft-chat",
  "version": 1,
  "data": {
    //请求的UUID
    "requestId": "9e4cfaa3-27df-4b05-943c-f69461c0d104",
    //回复某个其他来源(QQ等)消息的ID，注意可能为null
    "replyId": "11451419-1919-8810-1919-f69461c0d104",
    //消息来源
    "source": {
      "worldName": "world",
      "worldUid": "11451419-1919-8810-1919-f69461c0d104",
      "worldEnvironment": "THE_END"
    },
    //发送者信息
    "sender": {
      "uuid": "11451419-1919-8810-1919-f69461c0d104",
      "displayName": "YajyuuSenpai",
      "isOp": false,
      "isWhitelist": true,
      "level": 514,
      "hp": 19.19,
      "ping": 810
    },
    //消息内容
    "msg": "哼哼啊啊啊啊啊！！！"
  }
}
```

#### minecraft-command 指令转发

```json5
{
  "type": "minecraft-command",
  "version": 1,
  "data": {
    "requestId": "9e4cfaa3-27df-4b05-943c-f69461c0d104",
    //唯一uuid用于标识来源，便于发送方处理指令回显
    "source": "qq-group",
    //消息来源
    "sender": "admin",
    //发送者名称
    "command": "set time day"
    //指令内容，不带斜杠
  }
}
```

#### minecraft-command-echo 回复

```json5
{
  "type": "minecraft-command-echo",
  "version": 1,
  "data": {
    //回复的ID，便于发送方判断是哪条指令
    "replyId": "9e4cfaa3-27df-4b05-943c-f69461c0d104",
    "echo": "time has been set to 1000"
  }
}
```

#### qq-chat 聊天转发？

```json5
{
  "type": "qq-chat",
  "version": 1,
  "data": {
    //本次请求的 UUID
    "requestId": "9e4cfaa3-27df-4b05-943c-f69461c0d104",
    //可能为null,如果有，则为回复上一条消息的ID
    "replyId": "11451419-1919-8810-1919-f69461c0d104",
    //发送者，如果是匿名用户，则为0
    "senderQQ": 1145141919,
    //群，如果非群聊，则为0
    "group": 123456789,
    //发送者名称
    "senderName": "admin",
    //消息内容
    "msg": "hello world!"
  }
}
```