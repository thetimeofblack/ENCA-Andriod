package com.enca.gui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enca.bl.CleaningAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 85102 on 6/21/2016.
 */
public class CleaningAgentAdapter extends RecyclerView.Adapter<CleaningAgentAdapter.MyViewHolder>{
    private Context context;
    List<CleaningAgent> cleaningAgents = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder  implements  View.OnClickListener{
        TextView cleaningAgentName;
        TextView cleaningAgentDescription;
        public MyViewHolder(View view){
            super(view);
            cleaningAgentName = (TextView) view.findViewById(R.id.cleaning_agent);
            cleaningAgentDescription = (TextView) view.findViewById(R.id.cleaning_agent_description);
            cleaningAgentName.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

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
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        CleaningAgent cleaningAgent= cleaningAgents.get(position);
        holder.cleaningAgentName.setText((cleaningAgent.getName()).getContentString());
        holder.cleaningAgentDescription.setText(cleaningAgent.getDescription().getContentString());

    }

    @Override
    public int getItemCount() {
        return cleaningAgents.size();
    }

}