package com.example.nba_project.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.nba_project.data.entity.FavoriteList;
import com.example.nba_project.data.room.dao.FavoriteDao;
import com.example.nba_project.data.room.dao.FavoriteDao;


@Database(entities = {FavoriteList.class}, version = 1)
public abstract class FavoriteDatabase extends RoomDatabase {
    public abstract FavoriteDao favoriteDao();
}