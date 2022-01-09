package com.example.nba_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nba_project.data.entity.FavoriteTeam;
import com.example.nba_project.data.model.Team;

import java.util.List;

public class RecyclerAdapterTeams extends  RecyclerView.Adapter<RecyclerAdapterTeams.MyviewHolder> {
    public static final String EXTRA_MESSAGE = "com.example.api_balldontlie.MESSAGE";
    public static final int VIEW_TYPE_LIST = 0;
    public static final int VIEW_TYPE_GRID = 1;

    private List<Team> teams;
    private int teams_logos[];
    Context context;
    private int type = VIEW_TYPE_LIST;



    public RecyclerAdapterTeams(Context context, List<Team> teams, int[] teams_logos) {
        this.teams = teams;
        this.context = context;
        this.teams_logos = teams_logos;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerAdapterTeams.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType==VIEW_TYPE_LIST){
            view = LayoutInflater.from(parent.getContext()).inflate (R.layout.team_layout, parent, false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate (R.layout.team_layout_grid, parent, false);
        }
        return new MyviewHolder(view,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterTeams.MyviewHolder holder, @SuppressLint("RecyclerView") int position) {

        if(getItemViewType(position)== VIEW_TYPE_LIST){
            holder.fullname.setText(teams.get(position).getFullName());
        }else {
            holder.abreviation.setText(teams.get(position).getAbbreviation());
        }
        holder.logo.setImageResource(teams_logos[position]);

        holder.constraint_layout.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                Intent intent = new Intent(context, Team_Activity.class);
                intent.putExtra("team_logo",teams_logos[position]);
                Bundle bundle = new Bundle();
                bundle.putString("division", teams.get(position).getDivision());
                bundle.putString("city",teams.get(position).getCity());
                bundle.putString("fullname", teams.get(position).getFullName());
                bundle.putString("abreviation", teams.get(position).getAbbreviation());
                bundle.putInt("id", teams.get(position).getId());

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        }
        );

        holder.favorite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = teams.get(position).getId();
                FavoriteTeam favoriteTeam = new FavoriteTeam();
                favoriteTeam.setId(id);
                favoriteTeam.setFullname(teams.get(position).getFullName());
                favoriteTeam.setAbbrevation(teams.get(position).getAbbreviation());
                favoriteTeam.setCity(teams.get(position).getCity());
                favoriteTeam.setDivision(teams.get(position).getDivision());

                if (MainActivity.favoriteDatabase.favoriteDao().isFavorite(id)!=1){
                    holder.favorite_button.setImageResource(R.drawable.ic_favorite_red_24);
                    MainActivity.favoriteDatabase.favoriteDao().addData(favoriteTeam);
                }else {
                    holder.favorite_button.setImageResource(R.drawable.ic_favorite_shadow_24);
                    MainActivity.favoriteDatabase.favoriteDao().delete(favoriteTeam);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (teams != null) {
            return teams.size();
        }
        return 0;
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        private CardView constraint_layout;
        private TextView abreviation;
        private TextView fullname;
        private ImageView logo;
        private ImageView favorite_button;

        public MyviewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            if (viewType == VIEW_TYPE_LIST) {
                fullname = (TextView) itemView.findViewById(R.id.team_fullname);
            }else{
                abreviation = (TextView) itemView.findViewById(R.id.abreviation_team);
            }
            constraint_layout = (CardView) itemView.findViewById(R.id.constraint_layout_favorite);
            logo = (ImageView) itemView.findViewById(R.id.logo);
            favorite_button = itemView.findViewById(R.id.favorite_team_button);

        }
    }

}
