package com.enca.gui;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enca.bl.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 85102 on 6/18/2016.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder>{
    private Context context;
    List<Tag> items = new ArrayList<>();
    int roomTagId;

    public class MyViewHolder extends RecyclerView.ViewHolder  implements  View.OnClickListener{
        TextView itemTagName;
        public MyViewHolder(View view){
            super(view);
            itemTagName = (TextView) view.findViewById(R.id.tag_name);
            itemTagName.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

        }
    }

    public ItemAdapter(Context context, List<Tag> items, int roomTagId){
        this.context = context;
        this.items = items;
        this.roomTagId = roomTagId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_myadapter_recyclerview,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Tag tags = items.get(position);
        holder.itemTagName.setText((tags.getName()).getInterfaceString());
        holder.itemTagName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, CleaningAgentActivity.class);
                Intent intent = new Intent("com.android.enca.search");
                intent.putExtra("roomTagId", roomTagId);
                intent.putExtra("itemTagId",getItemTagId(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public int getItemTagId(int position){
        int count =0;
        Map<Integer,Integer> itemTagId = new HashMap<>();
        for (Tag tag: items){
            itemTagId.put(count++,tag.getTagID());
        }
        return itemTagId.get(position);
    }

}