package com.example.nba_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.nba_project.data.entity.FavoriteTeam;

import java.util.List;

public class FavoritesListActivity extends AppCompatActivity {

    private RecyclerView favoritesListRV;
    private FavoriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_list);

        favoritesListRV = (RecyclerView) findViewById(R.id.favorites_list_recyclerView);
        favoritesListRV.setHasFixedSize(true);
        favoritesListRV.setLayoutManager(new LinearLayoutManager(this));
        getFavoritesList();
    }

    private void getFavoritesList() {
        List<FavoriteTeam> FavoritesList=MainActivity.favoriteDatabase.favoriteDao().getFavoriteData();

        adapter=new FavoriteAdapter(FavoritesList,getApplicationContext());
        favoritesListRV.setAdapter(adapter);
    }
}