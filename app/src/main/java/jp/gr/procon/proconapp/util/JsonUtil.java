package jp.gr.procon.proconapp.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

    /**
     * <code>src</code>をjson文字列へ変換する
     *
     * @param src             ソース
     * @param isSerializeNull 値がnullのフィールドをjsonに含む場合はtrue
     * @param <T>
     * @return json文字列
     */
    public static <T> String toJson(T src, boolean isSerializeNull) {
        String res;

        GsonBuilder builder = new GsonBuilder();
        if (isSerializeNull) {
            builder.serializeNulls();
        }

        Gson gson = builder.create();
        res = gson.toJson(src);

        return res;
    }

    public static <T> String toJson(T src) {
        return toJson(src, false);
    }

    public static <T> T fromJson(String responseBodyText, Class<T> clazz) {
        Gson gson = new Gson();
        T obj = gson.fromJson(responseBodyText, clazz);
        return obj;
    }
}