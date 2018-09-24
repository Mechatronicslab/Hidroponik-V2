
package com.example.omgimbot.hidroponik.features.auth.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SignupResponse {

    @SerializedName("rc")
    private String mRc;
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
