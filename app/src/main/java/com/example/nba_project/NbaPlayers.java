package com.example.nba_project;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NbaPlayers {

    @SerializedName("data")
    @Expose
    private List<NbaPlayer> data = null;

    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<NbaPlayer> getData() {
        return data;
    }

    public void setData(List<NbaPlayer> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}