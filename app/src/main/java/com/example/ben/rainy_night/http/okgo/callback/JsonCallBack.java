package com.example.ben.rainy_night.http.okgo.callback;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;

import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author Ben
 * @date 2018/3/28
 */

public abstract class JsonCallBack<T> extends AbsCallback<T> {
    private Type mType;
    private Class<T> mClass;

    public JsonCallBack(Type mType) {
        this.mType = mType;
    }

    protected JsonCallBack(Class<T> mClass) {
        this.mClass = mClass;
    }

    @Override
    public T convertResponse(Response response) throws Throwable {
        ResponseBody body = response.body();
        if (body == null) {
            return null;
        }
        T data = null;
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(body.charStream());
        if (mType != null) {
            data = gson.fromJson(reader, mType);
        }
        if (mClass != null) {
            data = gson.fromJson(reader, mClass);
        }
        return data;
    }
}
