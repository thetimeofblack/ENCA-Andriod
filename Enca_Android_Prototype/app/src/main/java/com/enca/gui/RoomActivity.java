package com.enca.gui;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.enca.bl.Tag;
import com.enca.bl.TagType;
import com.enca.bl.User;
import com.enca.controller.TagFetcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoomActivity extends AppCompatActivity {
    MyAdapter myAdapterRoom;
    RecyclerView recyclerViewRoom;
    Set<Tag> roomTag= new HashSet<>();
    List<Tag> rooms = new ArrayList<>();
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_action_usercenter);
            getSupportActionBar().setTitle(getResources().getString(R.string.rooms));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent userIntent = new Intent(getApplicationContext(), UserCenterActivity.class);
                    userIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(userIntent);
                }
            });
        }

        recyclerViewRoom = (RecyclerView) findViewById(R.id.room_recyclerView);
        roomTag = TagFetcher.fetchTagsAllOfCertainType(TagType.ROOM);
        rooms.addAll(roomTag);
        myAdapterRoom = new MyAdapter(this, rooms);
        recyclerViewRoom.hasFixedSize();
        recyclerViewRoom.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRoom.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        recyclerViewRoom.setAdapter(myAdapterRoom);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        SearchView searchView;
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        ComponentName cn = new ComponentName(this, CleaningAgentActivity.class);
        SearchableInfo info = searchManager.getSearchableInfo(cn);
        searchView.setSearchableInfo(info);
        searchView.setSubmitButtonEnabled(true);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//
//            @Override
//            public boolean onQueryTextChange(String queryText) {
//
//                return true;
//            }
//
//
//            @Override
//            public boolean onQueryTextSubmit(String queryText) {
//
//                return true;
//            }
//        });
        return true;
    }

}
