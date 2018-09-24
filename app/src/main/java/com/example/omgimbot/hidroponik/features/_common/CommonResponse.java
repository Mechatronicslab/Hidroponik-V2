package com.example.omgimbot.hidroponik.features._common;

import com.google.gson.annotations.SerializedName;


/**
 * Created by hynra [github.com/hynra] on 07/02/2018.
 */

public class CommonResponse {

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
