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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.enca.bl.CleaningAgent;
import com.enca.bl.Tag;
import com.enca.controller.CleaningAgentFetcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CleaningAgentActivity extends AppCompatActivity {

    CleaningAgentAdapter cleaningAgentAdapter;
    RecyclerView recyclerViewItem;
    Set<Tag> tags = new HashSet<>();
    Set<CleaningAgent> cleaningAgentSet = new HashSet<>();
    List<CleaningAgent> cleaningAgentsResult = new ArrayList<>();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaning_agent);

        recyclerViewItem = (RecyclerView) findViewById(R.id.cleaning_agent_recyclerView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (intent.getAction().equals("com.android.enca.search")) {
            int roomTagId = intent.getIntExtra("roomTagId", 0);
            int itemTagId = intent.getIntExtra("itemTagId", 0);
            tags.add(Tag.getTag(roomTagId));
            tags.add(Tag.getTag(itemTagId));
            cleaningAgentSet = CleaningAgentFetcher.fetchCleaningAgentsOfTags(tags);
            cleaningAgentsResult.addAll(cleaningAgentSet);
            getSupportActionBar().setTitle(Tag.getTag(roomTagId).getName().getInterfaceString());
            getSupportActionBar().setSubtitle(Tag.getTag(itemTagId).getName().getInterfaceString());
        }
        // Get the intent, verify the action and get the query
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            getSupportActionBar().setTitle(query);
            cleaningAgentSet = CleaningAgent.getCleaningAgentsAll();
            doMySearch(query);
        }

        cleaningAgentAdapter = new CleaningAgentAdapter(this, cleaningAgentsResult);
        recyclerViewItem.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewItem.setAdapter(cleaningAgentAdapter);


    }
    public void doMySearch(String query) {
            cleaningAgentsResult.addAll(CleaningAgentFetcher.fetchResult(cleaningAgentSet, query));
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

    public boolean onCreateOptionsMenu(Menu menu) {
        SearchView searchView;
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        ComponentName cn = new ComponentName(this, this.getClass());
        SearchableInfo info = searchManager.getSearchableInfo(cn);
        searchView.setSearchableInfo(info);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                cleaningAgentsResult.clear();
                cleaningAgentsResult.addAll(CleaningAgentFetcher.fetchResult(cleaningAgentSet, query));

                cleaningAgentAdapter = new CleaningAgentAdapter(getApplicationContext(), cleaningAgentsResult);
                recyclerViewItem.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerViewItem.setAdapter(cleaningAgentAdapter);
                return true;
            }

        });

        return true;
    }
}
