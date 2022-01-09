package com.example.nba_project.data.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "favoritelist")
public class FavoriteTeam {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "logo")
    private String logo;

    @ColumnInfo(name = "name")
    private String fullname;

    @ColumnInfo(name = "division")
    private String division;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "abbrevation")
    private String abbrevation;


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

    public String getAbbrevation() {
        return abbrevation;
    }

    public String getCity() {
        return city;
    }

    public String getDivision() {
        return division;
    }

    public void setAbbrevation(String abbrevation) {
        this.abbrevation = abbrevation;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDivision(String division) {
        this.division = division;
    }

}
