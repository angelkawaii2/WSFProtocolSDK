package dev.raincandy.sdk.websocket.util;

import com.google.gson.JsonElement;
import dev.raincandy.sdk.websocket.protocol.v1.bean.body.modules.DataBean;

import java.util.HashMap;
import java.util.Map;

/**
 * 由于gson会将object中的数字类型转换错误，在getBody().convert(xxx.class)前可能会出现数据类型错误
 * 为了保证body的data部分反序列化后，convert前类型正确，建议提前在这里注册body中type和data类的映射关系
 * 如果未在此处注册，则gson会使用默认的Object.class进行转换，将不保证body的data object字段在convert前的数据类型正确
 * 对于已经注册的，BodyDeserializer将自动处理data（GsonUtil的gson已经包含了BodyDeserializer）
 * todo 20210816 不注册的话用convert会转换失败，拿到全null的data对象
 */
public class DataTypeClassRegister {


    static Map<String, Class<? extends DataBean>> dataTypeMap = new HashMap<>();

    /**
     * 注册类型
     *
     * @param type          类型，例: "server-response"
     * @param dataBeanClass 对应的data类，例: ServerResponseDataBean.class
     */
    public static void registerDataType(String type, Class<? extends DataBean> dataBeanClass) {
        dataTypeMap.put(type, dataBeanClass);
    }

    public static void registerDataType(Map<String, Class<? extends DataBean>> map) {
        dataTypeMap.putAll(map);
    }

    public static DataBean deserializeWithType(String type, JsonElement dataJsonObj) {
        return GsonUtil.getGson().fromJson(dataJsonObj, dataTypeMap.getOrDefault(type, DataBean.class));
    }
}
