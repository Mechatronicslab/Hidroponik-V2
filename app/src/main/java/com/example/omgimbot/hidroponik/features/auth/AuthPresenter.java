package com.example.omgimbot.hidroponik.features.auth;

import android.util.Base64;
import android.util.Log;

import java.util.HashMap;

import com.example.omgimbot.hidroponik.App;
import com.example.omgimbot.hidroponik.Prefs;
import com.example.omgimbot.hidroponik.R;
import com.example.omgimbot.hidroponik.features._common.CommonRequest;
import com.example.omgimbot.hidroponik.features.auth.model.LoginResponse;
import com.example.omgimbot.hidroponik.features.auth.model.RegistrationModel;
import com.example.omgimbot.hidroponik.features.auth.model.SignupResponse;
import com.example.omgimbot.hidroponik.network.NetworkService;
import com.example.omgimbot.hidroponik.network.RestService;
import com.example.omgimbot.hidroponik.utils.DeviceInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by github.com/adip28 on 7/20/2018.
 */
public class AuthPresenter {

    final IAuthView view;
    private final Retrofit restService;
    private final String TAG = "Auth Presenter";

    AuthPresenter(IAuthView view) {
        this.view = view;
        restService = RestService.getRetroftInstance();
    }


    boolean isLoggedIn(){
        return App.getPref().getBoolean(Prefs.PREF_IS_LOGEDIN, false);
    }

    void storeAccessToken(String token){
        App.getPref().put(Prefs.PREF_ACCESS_TOKEN, token);
    }

    void storeProfile(String data){
        App.getPref().put(Prefs.PREF_STORE_PROFILE, data);
        App.getPref().put(Prefs.PREF_IS_LOGEDIN, true);
    }

    void login(String email, String password) {
        String credentials = email + ":" + password;
        String basic = "Basic " + Base64.encodeToString(credentials.getBytes(),Base64.NO_WRAP);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("Authorization", basic)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        }).build();

        view.showLoadingIndicator();
        restService.newBuilder().client(okHttpClient).build().create(NetworkService.class).login().enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                view.hideLoadingIndicator();
                if(response.isSuccessful()) {
                    //Prefs.getInstance(getApplicationContext()).setProfile(response.body().getResult());
                    //App.getPref().put(Prefs2.PREF_IS_LOGEDIN, true);
                    //App.getPref().put(Prefs2.PREF_USERNAME, response.body().getResult().getUsername());
                    view.onSigninSuccess(response.body());
                } else {
                    Log.d("msg","gagal");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                view.onNetworkFailed(t.getLocalizedMessage());
            }
        });
    }

    /*void signup(RegistrationModel model) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("name", model.getName());
        params.put("username", model.getUsername());
        params.put("email", model.getEmail());
        params.put("phone_number", model.getPhone());
        params.put("city_id", model.getCityid());
        params.put("password", model.getPassword());
        params.put("signup_type", model.getSignupType());
        params.put("role", model.getRole());

        view.showLoadingIndicator();
        restService.create(NetworkService.class).signup(params).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(retrofit2.Call<SignupResponse> call, Response<SignupResponse> response) {
                view.hideLoadingIndicator();
                if (response.body().getSuccess()) {
                    view.onSignupSuccess(response.body());
                } else {
                    view.onRequestFailed(response.body().getRm(), response.body().getRc());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<SignupResponse> call, Throwable t) {
                view.hideLoadingIndicator();
                view.onNetworkFailed(t.getLocalizedMessage());
            }
        });
    }*/

}
