package dev.raincandy.sdk.websocket.protocol.v1.bean.body;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dev.raincandy.sdk.websocket.protocol.v1.bean.body.modules.DataBean;
import dev.raincandy.sdk.websocket.util.GsonUtil;

/**
 * 通用的body类？可能要抽象一个data的可序列化接口?
 * 子类应该继承此类，通过 getReflectBody 快速转换
 */
public class BodyBean {
    //类型？
    String type;

    //协议版本
    int version;

    //数据
    Object data = null;


    public BodyBean(String type, int version) {
        this.type = type;
        this.version = version;
    }

    public BodyBean(String type, int version, Object data) {
        this.type = type;
        this.version = version;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 子类有需要可以自己实现，返回自定义的dataBean
     * 推荐使用getData(class)
     * @return
     */
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 反序列化后获取指定类型的body
     *
     * @param clazz 类，需要继承BodyBean
     * @param <T>   泛型
     * @return
     */
    public <T extends BodyBean> T convert(Class<T> clazz) throws JsonSyntaxException {
        return convert(this, clazz);
    }

    /**
     * 反序列化后获取指定类型的body
     *
     * @param obj   对象
     * @param clazz 类，需要继承BodyBean
     * @param <T>   泛型
     * @return
     */
    public static <T extends BodyBean> T convert(Object obj, Class<T> clazz) throws JsonSyntaxException {
        Gson gson = GsonUtil.getGson();
        return gson.fromJson(gson.toJson(obj), clazz);
    }

    public <T extends DataBean> T getData(Class<T> dataClass) {
        Gson gson = GsonUtil.getGson();
        return gson.fromJson(gson.toJson(data), dataClass);
    }

}
