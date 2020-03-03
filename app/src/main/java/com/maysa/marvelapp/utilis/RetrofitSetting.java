package com.maysa.marvelapp.utilis;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

// Retrofit Service class for creating service
public class RetrofitSetting {

    private ApiServices api = null;

    private static final RetrofitSetting ourInstance = new RetrofitSetting();

    public static RetrofitSetting getInstance() {
        return ourInstance;
    }

    private RetrofitSetting() {
        buildRetrofit();
    }

    private void buildRetrofit(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient client = new OkHttpClient
                .Builder()
                .cache(new Cache(MyApplication.context.getCacheDir(), 10 * 1024 * 1024)) // 10 MB
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    if (isOnline()) {
                        request = request.newBuilder().header("Cache-Control",
                                "public, max-age=" + 60).build();
                    } else {
                        request = request.newBuilder().header("Cache-Control",
                                "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                    }
                    return chain.proceed(request);
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.base_url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        this.api = retrofit.create(ApiServices.class);
    }


    public ApiServices getApi(){
        return this.api;
    }

    private static boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) MyApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}
