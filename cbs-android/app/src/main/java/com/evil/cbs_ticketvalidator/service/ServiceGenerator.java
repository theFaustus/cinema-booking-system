package com.evil.cbs_ticketvalidator.service;

import android.app.Activity;
import android.content.Context;

import com.evil.cbs_ticketvalidator.BuildConfig;
import com.evil.cbs_ticketvalidator.util.ContextHolder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static Retrofit.Builder builder =
            new Retrofit.Builder().client(new OkHttpClient());

    public static final String BASE_URL = "http://35.241.177.112:8080/v1/api/";// 10 MB
    public static final int CACHE_SIZE = 10 * 1024 * 1024;

    public static <S> S createServiceWithInterceptor(Class<S> serviceClass, Activity activity) {
        Cache cache = new Cache(ContextHolder.getContext().getCacheDir(), CACHE_SIZE);

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.cache(cache);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(5, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(interceptor);
        }

        String token = activity.getSharedPreferences("session", Context.MODE_PRIVATE).getString("token", "");
        String type = activity.getSharedPreferences("session", Context.MODE_PRIVATE).getString("type", "");

        builder.addInterceptor(chain -> {
            Request request = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", type + " " + token)
                    .build();
            return chain.proceed(request);
        });

        OkHttpClient client = builder.build();

        Retrofit retrofit =
                new Retrofit.Builder().baseUrl(BASE_URL).client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        return retrofit.create(serviceClass);

    }

    public static <S> S createService(
            Class<S> serviceClass) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(5, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(interceptor);
        }

        OkHttpClient client = builder.build();

        Retrofit retrofit =
                new Retrofit.Builder().baseUrl(BASE_URL).client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
        return retrofit.create(serviceClass);

    }
}