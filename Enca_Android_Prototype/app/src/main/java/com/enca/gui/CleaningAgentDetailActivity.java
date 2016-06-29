package com.enca.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enca.bl.CleaningAgent;
import com.enca.bl.Tag;
import com.enca.controller.CleaningAgentFetcher;
import com.enca.dao.DatabaseAccess;

import org.w3c.dom.Text;

import java.util.Set;

public class CleaningAgentDetailActivity extends AppCompatActivity {
    private TextView caName, caDecripition, caLocation, caTag, caRate, caInstruction;
    private ImageView caImage;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaning_agent_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        caName = (TextView) findViewById(R.id.cleaning_agent_name_detail);
        caDecripition = (TextView) findViewById(R.id.cleaning_agent_description_detail);
        caLocation = (TextView) findViewById(R.id.cleaning_agent_mainlanguage_detail);
        caTag = (TextView) findViewById(R.id.cleaning_agent_tag_detail);
        caRate = (TextView) findViewById(R.id.cleaning_agent_rate_detail);
        caInstruction = (TextView) findViewById(R.id.cleaning_agent_instruction_detail);
        caImage = (ImageView) findViewById(R.id.cleaning_agent_image_detail);

        Intent intent = getIntent();
        int cleaningAgentId= intent.getIntExtra("CleaningAgentId",0);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        caName.setMovementMethod(ScrollingMovementMethod.getInstance());
        caName.setText(CleaningAgent.getCleaningAgent(cleaningAgentId).getName().getContentString());
        caLocation.setText(CleaningAgent.getCleaningAgent(cleaningAgentId).getMainLanguage().toString());
        caTag.setMovementMethod(ScrollingMovementMethod.getInstance());
//        Set<Tag> tags = CleaningAgent.getCleaningAgent(cleaningAgentId).getTags();
//        Log.d("Tags",tags.toString());
//        caTag.setText(String.format("%-12s",tags));
        caRate.setText(CleaningAgent.getCleaningAgent(cleaningAgentId).getRate());
        caInstruction.setText(CleaningAgent.getCleaningAgent(cleaningAgentId).getInstruction().getContentString());
        caDecripition.setText(CleaningAgent.getCleaningAgent(cleaningAgentId).getDescription().getContentString());
        caImage.setImageBitmap(CleaningAgentFetcher.fetchImageOfCleaningAgent(cleaningAgentId));
//        caImage.setImageBitmap(CleaningAgent.getCleaningAgent(cleaningAgentId).getImage());
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cleaningagent_detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.cleaning_agent_add:
                Toast.makeText(this, "ADD", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cleaning_agent_modify:
                Toast.makeText(this, "Modify", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cleaning_agent_delete:
                Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
