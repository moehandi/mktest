package com.moehandi.moktest.data.remote;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import com.moehandi.moktest.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by moehandi on 22/5/19.
 */

public interface RestServiceFactory {

    String API_BASE_URL = "https://jsonplaceholder.typicode.com/";

    <T> T create(Class<T> clazz);

    class Impl implements RestServiceFactory {
        @Override
        public <T> T create(Class<T> clazz) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                // set your desired log level
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                // add logging as last interceptor
                httpClient.addInterceptor(logging);
            }

            // add Timeouts
            httpClient.connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS);

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

            Retrofit retrofit = builder
                    .client(httpClient.build())
                    .build();
            return retrofit.create(clazz);
        }
    }

}
