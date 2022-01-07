package com.example.nba_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nba_project.data.model.Team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerAdapterTeams extends  RecyclerView.Adapter<RecyclerAdapterTeams.MyviewHolder> {

    public static final String EXTRA_MESSAGE = "com.example.api_balldontlie.MESSAGE";
    public static final int VIEW_TYPE_LIST = 0;
    public static final int VIEW_TYPE_GRID = 1;

    private List<Team> teams;
    Context context;
    private int type = VIEW_TYPE_LIST;



    public RecyclerAdapterTeams(Context context, List<Team> teams) {
        this.teams = teams;
        this.context = context;
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

        Map<String,String> teamData = new HashMap<String,String>(){{
            put("division",teams.get(position).getDivision());
            put("city",teams.get(position).getCity());
            put("fullname",teams.get(position).getFullName());
            put("abreviation",teams.get(position).getAbbreviation());
        }};

        if(getItemViewType(position)== VIEW_TYPE_LIST){
            holder.division.setText(teamData.get("division"));
            holder.city.setText(teamData.get("city"));
            holder.abreviation.setText(teamData.get("abreviation"));
        }
        holder.fullname.setText(teams.get(position).getFullName());
        holder.id = teams.get(position).getId();

        holder.constraint_layout.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                Intent intent = new Intent(context, Team_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("division", teamData.get("division"));
                bundle.putString("city",teams.get(position).getCity());
                bundle.putString("fullname", teams.get(position).getFullName());
                bundle.putString("abreviation", teams.get(position).getAbbreviation());
                bundle.putInt("id", holder.id);

                intent.putExtras(bundle);
                context.startActivity(intent);
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

        private ConstraintLayout constraint_layout;
        private TextView abreviation;
        private TextView city;
        private TextView fullname;
        private TextView division;
        private int id;

        public MyviewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            if (viewType == VIEW_TYPE_LIST) {
                abreviation = (TextView) itemView.findViewById(R.id.abreviation);
                city = (TextView) itemView.findViewById(R.id.team_city);
                division = (TextView) itemView.findViewById(R.id.division);
            }
            fullname = (TextView) itemView.findViewById(R.id.team_fullname);
            constraint_layout = (ConstraintLayout) itemView.findViewById(R.id.constraint_layout);

        }
    }

}
