package com.example.nba_project.data.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "favoritelist")
public class FavoriteEntity {
    @PrimaryKey
    private int id;


    @ColumnInfo(name = "logo")
    private String logo;

    @ColumnInfo(name = "name")
    private String fullname;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
