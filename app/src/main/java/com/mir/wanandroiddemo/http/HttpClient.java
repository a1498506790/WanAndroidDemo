package com.mir.wanandroiddemo.http;

import com.mir.wanandroiddemo.constants.Api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author by lx
 * @github https://github.com/a1498506790
 * @data 2018/3/2
 * @desc 封装Retrofit
 */

public class HttpClient {

    private static HttpClient mInstance;
    private Retrofit mRetrofit;

    public HttpClient(String baseUrl) {
        if (baseUrl == null) {
            configRetrofit(Api.BASE_SERVICE_URL);
        }else{
            configRetrofit(baseUrl);
        }
    }

    public static HttpClient getInstance(){
        return getInstance(null);
    }

    public static HttpClient getInstance(String baseUrl){
        if (mInstance == null) {
            synchronized (HttpClient.class){
                if (mInstance == null) {
                    mInstance = new HttpClient(baseUrl);
                }
            }
        }
        return mInstance;
    }

    private void configRetrofit(String baseUrl){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(20, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        OkHttpClient client = builder.build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public <T> T createService(Class<T> clz){
        return mRetrofit.create(clz);
    }
}
