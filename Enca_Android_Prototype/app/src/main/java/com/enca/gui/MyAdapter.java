package com.enca.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.enca.bl.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 85102 on 6/18/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private Context context;
    List<Tag> rooms = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder  implements  View.OnClickListener{
        TextView roomTagName;
        public MyViewHolder(View view){
            super(view);
            roomTagName = (TextView) view.findViewById(R.id.tag_name);
            roomTagName.setOnClickListener(this);

        }

//        @Override
        public void onClick(View view) {
//            if(view.getId() == roomTagName.getId()){
//                Intent intent = new Intent(context, ItemActivity.class);
//                intent.putExtra("position", view.getVerticalScrollbarPosition());
//                context.startActivity(intent);
//            }
        }
    }

    public  MyAdapter(Context context, List<Tag> rooms){
        this.context = context;
        this.rooms = rooms;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_myadapter_recyclerview,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Tag tags = rooms.get(position);
        holder.roomTagName.setText((tags.getName()).getInterfaceString());
        holder.roomTagName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemActivity.class);
                int roomTagId = getRoomTagId(position);
                intent.putExtra("roomTagId", roomTagId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }


    public int getRoomTagId(int position){
        int count =0;
        Map<Integer,Integer> roomTagId = new HashMap<>();
        for (Tag tag: rooms){
            roomTagId.put(count++,tag.getTagID());
        }
        return roomTagId.get(position);
    }
}