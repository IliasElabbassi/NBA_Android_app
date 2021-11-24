package com.example.nba_project;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface API_interface {
    @GET("players")
    Call<NbaPlayers> getPlayers();

    @GET("players/{id}")
    Call<NbaPlayer> getPlayers(@Path("id") int id);

    @GET("teams/{id}")
    Call<Team> getTeam(@Path("id") int id);

    @GET("teams")
    Call<Teams> getTeams();
}
