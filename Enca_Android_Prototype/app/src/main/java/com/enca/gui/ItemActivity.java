package com.enca.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;

import com.enca.bl.Tag;
import com.enca.bl.TagType;
import com.enca.controller.TagFetcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemActivity extends AppCompatActivity {

    ItemAdapter itemAdapter;
    RecyclerView recyclerViewItem;
    Set<Tag> itemTag = new HashSet<>();
    List<Tag> items = new ArrayList<>();
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_item);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_action_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerViewItem = (RecyclerView) findViewById(R.id.item_recyclerView);
        Intent intent = getIntent();
        int roomTagId = intent.getIntExtra("roomTagId",0);
        itemTag.add(Tag.getTag(roomTagId));
        itemTag = TagFetcher.fetchTagsOfCertainType(TagFetcher.fetchTagsRelated(itemTag),TagType.ITEM);
        items.addAll(itemTag);
        itemAdapter = new ItemAdapter(this, items,roomTagId);
//        recyclerViewItem.hasFixedSize();
        recyclerViewItem.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewItem.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        recyclerViewItem.setAdapter(itemAdapter);
        getSupportActionBar().setTitle(Tag.getTag(roomTagId).getName().getInterfaceString());
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, RoomActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
