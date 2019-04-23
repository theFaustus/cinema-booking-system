package com.evil.cbs_ticketvalidator.service;

import android.content.Context;
import android.util.Base64;

import com.evil.cbs_ticketvalidator.BuildConfig;
import com.evil.cbs_ticketvalidator.data.AccessToken;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static Retrofit.Builder builder =
            new Retrofit.Builder().client(new OkHttpClient());

    public static final String BASE_URL = "http://10.0.2.2:8083/v1/api/";


//    public static <S> S createService(Class<S> serviceClass, final AccessToken accessToken) {
//
//        builder.baseUrl(BASE_URL);
//
//        if (accessToken != null) {
//            builder.(new RequestInterceptor() {
//                @Override
//                public void intercept(RequestFacade request) {
//                    request.addHeader("Accept", "application/json");
//                    request.addHeader("Authorization",
//                            accessToken.getTokenType() + " " +
//                                    accessToken.getAccessToken());
//                }
//            });
//        }
//
//        Retrofit adapter = builder.build();
//        return adapter.create(serviceClass);
//    }

    public static <S> S createServiceWithInterceptor(Class<S> serviceClass) {

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(5, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(interceptor);
        }

        builder.addInterceptor(chain -> {
            Request request = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization",
                            "Bearer" + " " +
                                    "Kisda măsă")
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