package com.example.omgimbot.hidroponik.features.auth.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by github.com/adip28 on 7/20/2018.
 */
@Generated("net.hexar.json2pojo")
public class LoginResponse {
    @SerializedName("rc")
    private String mRc;
    @SerializedName("result")
    private Result mResult;
    @SerializedName("rm")
    private String mRm;
    @SerializedName("success")
    private Boolean mSuccess;

    public String getRc() {
        return mRc;
    }

    public void setRc(String rc) {
        mRc = rc;
    }

    public Result getResult() {
        return mResult;
    }

    public void setResult(Result result) {
        mResult = result;
    }

    public String getRm() {
        return mRm;
    }

    public void setRm(String rm) {
        mRm = rm;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

}