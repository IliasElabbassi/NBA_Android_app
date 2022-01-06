package com.example.nba_project.data.API;

import com.example.nba_project.data.model.NbaPlayers;
import com.example.nba_project.data.model.Teams;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API_interface {
    @GET("players")
    Call<NbaPlayers> getPlayers();

    @GET("teams")
    Call<Teams> getTeams();

    @GET("players?per_page=100")
    Call<NbaPlayers> getPlayersWithPage(@Query("page") int page);
}
