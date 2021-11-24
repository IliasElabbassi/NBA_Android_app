package com.example.nba_project;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Teams {

    @SerializedName("data")
    @Expose
    private List<Team> data = null;

    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<Team> getData() {
        return data;
    }

    public void setData(List<Team> data) {
        this.data = data;
    }
}