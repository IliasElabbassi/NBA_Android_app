package com.example.nba_project;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;


import com.example.nba_project.data.API.API_Client;
import com.example.nba_project.data.API.API_interface;
import com.example.nba_project.data.model.Team;
import com.example.nba_project.data.model.Teams;
import com.example.nba_project.data.room.FavoriteDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    API_interface apiService = API_Client.getClient().create(API_interface.class);

    private List<Team> teams;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private RecyclerAdapterTeams recyclerAdapter;
    public static FavoriteDatabase favoriteDatabase;
    public int teams_logos [] = {
            R.drawable._0atl, R.drawable._1bos, R.drawable._2bkl, R.drawable._3cha, R.drawable._4chi, R.drawable._5cle, R.drawable._6dal, R.drawable._7den, R.drawable._8dep, R.drawable._9gsw,R.drawable._10hrl, R.drawable._11ind, R.drawable._12cli, R.drawable._13lal, R.drawable._14mem,
            R.drawable._15mia, R.drawable._16mil, R.drawable._17min, R.drawable._18nop, R.drawable._19nyc, R.drawable._20oct, R.drawable._21olm, R.drawable._22p76, R.drawable._23pho, R.drawable._24por, R.drawable._25skl, R.drawable._26sas, R.drawable._27terl, R.drawable._28uta, R.drawable._29was
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.teams = new ArrayList<Team>();
        favoriteDatabase = Room.databaseBuilder(getApplicationContext(),FavoriteDatabase.class,"FavoriteDatabase")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        favoriteDatabase.favoriteDao().initialize();

        setAdapter();
        initRecyclerViewDataForSearch();
    }

    @Override
    protected void onResume() {
        super.onResume();

        callTeams();
    }

    private void initRecyclerViewDataForSearch(){
        recyclerAdapter.setAllTeams(recyclerAdapter.getTeams());
    }
    private void callTeams(){
        Call<Teams> call_teams = apiService.getTeams();

        call_teams.enqueue(new Callback<Teams>() {
               @Override
               public void onResponse(Call<Teams> call, Response<Teams> response) {
                   Log.d("ILIAS","on response");
                   displayListOfTeams(response.body());
               }

               @Override
               public void onFailure(Call<Teams> call, Throwable t) {
                   Log.d("ILIAS","on failure :" + t.toString());
               }
           }
        );
    }

    private void displayListOfTeams(Teams Teams){
        StringBuilder stringBuilder = new StringBuilder();
        for (Team team : Teams.getData()){
            stringBuilder.append("-"+team.getFullName()+"\n");
            teams.add(team);
            recyclerAdapter.notifyItemInserted(teams.size());
        }
        Log.d("ILIAS", "réponse = \n" + stringBuilder.toString());
    }

    private void setAdapter() {
        this.recyclerView = (RecyclerView) findViewById(R.id.teams_recyclerView);
        this.recyclerAdapter = new RecyclerAdapterTeams(this, teams,teams_logos);
        this.linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        getSupportActionBar().setIcon(R.drawable.ic_favorite_shadow_24);

        MenuItem menuItem = menu.findItem(R.id.search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recyclerAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapter.setTeams(recyclerAdapter.getAllTeams());
                recyclerAdapter.notifyDataSetChanged();
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()){
            case R.id.favorites:
                Intent FavoritesListIntent = new Intent(MainActivity.this,FavoritesListActivity.class);
                FavoritesListIntent.putExtra("teams_logos",this.teams_logos);
                startActivity(FavoritesListIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void switchLayout(View view){
        Switch sw = (Switch)findViewById(R.id.switchLayout);

        if(sw.isChecked()){
            this.recyclerAdapter.setType(RecyclerAdapterTeams.VIEW_TYPE_GRID);
            this.gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
            this.recyclerView.setLayoutManager(gridLayoutManager);
        }else{
            this.recyclerAdapter.setType(RecyclerAdapterTeams.VIEW_TYPE_LIST);
            this.linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            this.recyclerView.setLayoutManager(linearLayoutManager);
        }
        this.recyclerAdapter.notifyDataSetChanged();
    }
}