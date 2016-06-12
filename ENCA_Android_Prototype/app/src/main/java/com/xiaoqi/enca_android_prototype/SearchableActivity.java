package com.xiaoqi.enca_android_prototype;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class SearchableActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
        else if(intent.getAction().equals("com.android.search")){
            String kitchen_query = intent.getStringExtra("Kitchen");
            String bathroom_query = intent.getStringExtra("Bathroom");
//            Toast.makeText(getApplicationContext(), kitchen_query, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), bathroom_query, Toast.LENGTH_SHORT).show();
            doMySearch(kitchen_query);
            doMySearch(bathroom_query);
        }

    }

    public void doMySearch(String query) {
        Toast.makeText(SearchableActivity.this, query, Toast.LENGTH_SHORT).show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return  true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, ItemSelectionActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
    return super.onOptionsItemSelected(menuItem);
    }
}