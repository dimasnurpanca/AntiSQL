package com.dnp.antisql.retrofit;

/**
 * Created by dimasnurpanca on 10/2/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IPList {


    @SerializedName("ip")
    @Expose
    public String ip;

    @SerializedName("timestamp")
    @Expose
    public String date;
    @SerializedName("status")
    @Expose
    public String status;


    public IPList(String ip, String date, String status) {
        this.ip = ip;
        this.date = date;
        this.status = status;

    }

    public String getIP() {
        return ip;
    }

    public String getDate() {
        return date;
    }
    public String getStatus() {
        return status;
    }

}
