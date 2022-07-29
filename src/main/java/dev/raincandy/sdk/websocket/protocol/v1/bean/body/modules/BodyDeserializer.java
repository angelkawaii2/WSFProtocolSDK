package dev.raincandy.sdk.websocket.protocol.v1.bean.body.modules;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import dev.raincandy.sdk.websocket.protocol.v1.bean.body.BodyBean;
import dev.raincandy.sdk.websocket.util.DataTypeClassRegister;

import java.lang.reflect.Type;

public class BodyDeserializer implements JsonDeserializer<BodyBean> {
    @Override
    public BodyBean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        var bodyjson = json.getAsJsonObject();

        var type = bodyjson.get("type").getAsString();
        var ver = bodyjson.get("version").getAsInt();

        var data = bodyjson.get("data").getAsJsonObject();

        return new BodyBean(type, ver, DataTypeClassRegister.deserializeWithType(type, data));
    }
}
