package com.example.nba_project;


import androidx.appcompat.app.AppCompatActivity;
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
    private Button favoritesListButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.teams = new ArrayList<Team>();
        favoriteDatabase= Room.databaseBuilder(getApplicationContext(),FavoriteDatabase.class,"FavoriteDatabase").allowMainThreadQueries().build();
        favoriteDatabase.favoriteDao().initialize();

        setAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();

        callTeams();
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

        this.recyclerAdapter = new RecyclerAdapterTeams(this, teams);
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
                Toast.makeText(MainActivity.this, "RedVelvet", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()){
            case R.id.favorites:
                startActivity(new Intent(MainActivity.this,FavoritesListActivity.class));
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