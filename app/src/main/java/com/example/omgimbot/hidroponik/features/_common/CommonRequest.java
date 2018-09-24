package com.example.omgimbot.hidroponik.features._common;

import android.util.Log;

import java.util.HashMap;

import com.example.omgimbot.hidroponik.App;
import com.example.omgimbot.hidroponik.Prefs;
import com.example.omgimbot.hidroponik.network.NetworkService;
import com.example.omgimbot.hidroponik.network.RestService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by hynra [github.com/hynra] on 07/02/2018.
 */

public class CommonRequest {

    public static void storeToken(String token){
        Prefs prefs = App.getPref();
        prefs.put(token, Prefs.PREF_FIREBASE_TOKEN);
        if(prefs.getBoolean(Prefs.PREF_IS_LOGEDIN, false)) {
            final Retrofit restService = RestService.getRetroftInstance();
            String access_token = App.getPref().getString(Prefs.PREF_ACCESS_TOKEN, "");
            HashMap<String, Object> params = new HashMap<>();
            params.put("token", token);
            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .header("access_token", access_token)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }).build();
            restService
                    .newBuilder()
                    .client(okHttpClient)
                    .build()
                    .create(NetworkService.class).updateToken(params)
                    .enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                            Log.i("Store Token", "onResponse: "+ response.body().getRm());
                            if(response.body().getSuccess())
                                App.getPref().put(Prefs.PREF_FIREBASE_STORED, true);
                        }

                        @Override
                        public void onFailure(Call<CommonResponse> call, Throwable t) {
                            Log.i("Store Token", "onFailure: "+ t.getMessage());
                        }
                    });
        }
    }
}
