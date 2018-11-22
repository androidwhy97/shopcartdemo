package com.baway.shopcart2;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 文件描述：
 * 作者：王恒钰
 * 创建时间：2018/11/21
 */
public class OkHttpUtil {
    private static final String MEDIA_TYPE="application/json; charset=utf-8";
    private static final String METHOD_POST="POST";
    private static final String METHOD_GET="GET";
    private static OkHttpClient okHttpClient;
    private static Gson gson = new Gson();
    private OkHttpUtil() {
    }

    public static void init(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(3000, TimeUnit.MILLISECONDS);
        builder.writeTimeout(3000, TimeUnit.MILLISECONDS);
        builder.connectTimeout(5000, TimeUnit.MILLISECONDS);
        okHttpClient = builder.build();
    }

    public static Request getRequest(String url,String method,BaseRequest baseRequest){
        RequestBody requestBody=null;
        if (requestBody != null) {
            String json = gson.toJson(baseRequest);
            MediaType mediaType = MediaType.parse(MEDIA_TYPE);
            requestBody = RequestBody.create(mediaType, json);
        }

        Request.Builder builder = new Request.Builder().url(url);
        Request request=null;
        switch (method){
            case METHOD_POST:
                request = builder.post(requestBody).build();
                break;
            case METHOD_GET:
                request = builder.get().build();
                break;
        }
        return request;
    }

    public static void enqueueGet(String url, Callback callback){
        Request request = getRequest(url, METHOD_GET, null);
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static void enqueuepOST(String url,BaseRequest baseRequest,Callback callback){
        Request request = getRequest(url, METHOD_POST, baseRequest);
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }
}
