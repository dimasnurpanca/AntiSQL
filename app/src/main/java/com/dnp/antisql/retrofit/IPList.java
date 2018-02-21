package com.dnp.antisql.retrofit;

/**
 * Created by dimasnurpanca on 10/2/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IPList {

    @SerializedName("id")
    @Expose
    public String id;


    @SerializedName("ip")
    @Expose
    public String ip;

    @SerializedName("timestamp")
    @Expose
    public String date;
    @SerializedName("status")
    @Expose
    public String status;


    public IPList(String id, String ip, String date, String status) {
        this.id = id;
        this.ip = ip;
        this.date = date;
        this.status = status;

    }
    public String getID() {
        return id;
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
