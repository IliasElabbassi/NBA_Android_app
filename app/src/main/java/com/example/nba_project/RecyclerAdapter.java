package com.example.nba_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter  extends  RecyclerView.Adapter<RecyclerAdapter.MyviewHolder> {

    public static final String EXTRA_MESSAGE = "com.example.api_balldontlie.MESSAGE";
    private List<Team> teams;
    Context context;


    public RecyclerAdapter(Context context, List<Team> teams) {
        this.teams = teams;
        this.context = context;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_layout, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyviewHolder holder, int position) {
        holder.division.setText(teams.get(position).getDivision());
        holder.city.setText(teams.get(position).getCity());
        holder.fullname.setText(teams.get(position).getFullName());
        holder.abreviation.setText(teams.get(position).getAbbreviation());

        holder.constraint_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Team_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("division", holder.division.getText().toString());
                bundle.putString("city", holder.city.getText().toString());
                bundle.putString("fullname", holder.fullname.getText().toString());
                bundle.putString("abreviation", holder.abreviation.getText().toString());

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

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            abreviation = (TextView) itemView.findViewById(R.id.abreviation);
            city = (TextView) itemView.findViewById(R.id.city);
            fullname = (TextView) itemView.findViewById(R.id.fullname);
            division = (TextView) itemView.findViewById(R.id.division);
            constraint_layout = (ConstraintLayout) itemView.findViewById(R.id.constraint_layout);
        }
    }
}
