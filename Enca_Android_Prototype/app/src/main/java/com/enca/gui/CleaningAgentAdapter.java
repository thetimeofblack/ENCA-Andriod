package com.enca.gui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.enca.bl.CleaningAgent;
import com.enca.bl.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 85102 on 6/21/2016.
 */
public class CleaningAgentAdapter extends RecyclerView.Adapter<CleaningAgentAdapter.MyViewHolder>{
    private Context context;
    List<CleaningAgent> cleaningAgents = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView cleaningAgentName;
        TextView cleaningAgentDescription;
        CardView cardView;
        public MyViewHolder(View view){
            super(view);
            cleaningAgentName = (TextView) view.findViewById(R.id.cleaning_agent);
            cleaningAgentDescription = (TextView) view.findViewById(R.id.cleaning_agent_description);
            cardView = (CardView) view.findViewById(R.id.card_view_layout);

        }

    }

    public  CleaningAgentAdapter(Context context, List<CleaningAgent> cleaningAgents){
        this.context = context;
        this.cleaningAgents = cleaningAgents;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,  int position) {
        CleaningAgent cleaningAgent= cleaningAgents.get(position);
        holder.cleaningAgentName.setText((cleaningAgent.getName()).getContentString());
        holder.cleaningAgentDescription.setText(cleaningAgent.getDescription().getContentString());
        holder.cardView.setOnClickListener(OnClickListener(position));
    }

    private View.OnClickListener OnClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CleaningAgentDetailActivity.class);
                intent.putExtra("CleaningAgentId", getCleaningAgentId(position));
                context.startActivity(intent);
            }
        };
    }

    @Override
    public int getItemCount() {
        return cleaningAgents.size();
    }

    public int getCleaningAgentId(int position){
        int count =0;
        Map<Integer,Integer> cleaningAgentIdMap = new HashMap<>();
        for (CleaningAgent cleaningAgent: cleaningAgents){
            cleaningAgentIdMap.put(count++,cleaningAgent.getCleaningAgentID());
        }
        return cleaningAgentIdMap.get(position);
    }
}