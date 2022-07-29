package dev.raincandy.sdk.websocket.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.raincandy.sdk.websocket.protocol.v1.bean.body.BodyBean;
import dev.raincandy.sdk.websocket.protocol.v1.bean.body.modules.BodyDeserializer;

/**
 * 工具类，未来可能随时变化
 */
public class GsonUtil {

    private static Gson dev = new GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(BodyBean.class, new BodyDeserializer()).create();

    private static Gson production = new GsonBuilder()
            .registerTypeAdapter(BodyBean.class, new BodyDeserializer()).create();

    private static boolean isDev = false;

    public static void setIsDev(boolean isDev) {
        GsonUtil.isDev = isDev;
    }

    public static Gson getGson() {
        return isDev ? dev : production;
    }

}
