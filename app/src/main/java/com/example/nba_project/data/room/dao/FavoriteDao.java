package com.example.nba_project.data.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nba_project.data.entity.FavoriteTeam;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert
    public void addData(FavoriteTeam favoriteTeam);

    @Query("SELECT * FROM favoritelist")
    public List<FavoriteTeam> getFavoriteData();

    @Query("SELECT EXISTS (SELECT 1 FROM favoritelist WHERE id=:id)")
    public int isFavorite(int id);

    @Delete
    public void delete(FavoriteTeam favoriteTeam);

    @Query("DELETE FROM favoritelist")
    public  void initialize();

}
