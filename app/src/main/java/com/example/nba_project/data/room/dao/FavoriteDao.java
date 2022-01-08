package com.example.nba_project.data.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nba_project.data.entity.FavoriteEntity;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert
    public void addData(FavoriteEntity favoriteEntity);

    @Query("SELECT * FROM favoritelist")
    public List<FavoriteEntity> getFavoriteData();

    @Query("SELECT EXISTS (SELECT 1 FROM favoritelist WHERE id=:id)")
    public int isFavorite(int id);

    @Delete
    public void delete(FavoriteEntity favoriteEntity);

}
