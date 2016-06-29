package com.enca.gui;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.enca.bl.CleaningAgent;
import com.enca.bl.LanguageType;
import com.enca.bl.User;
import com.enca.controller.CleaningAgentFetcher;
import com.enca.dao.DatabaseAccess;



public class CleaningAgentDetailActivity extends AppCompatActivity {
    private TextView caName, caDecripition, caLocation, caTag, caRate, caInstruction;
    private ImageView caImage;
    private Spinner contentSpinner;
    private Configuration config ;
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
        contentSpinner = (Spinner) findViewById(R.id.cleaning_agent_spinner_content);
        config = new Configuration(getResources().getConfiguration());
        ConfigureSpinner();
        Intent intent = getIntent();
        int cleaningAgentId= intent.getIntExtra("CleaningAgentId",0);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        caName.setMovementMethod(ScrollingMovementMethod.getInstance());
        caName.setText(CleaningAgent.getCleaningAgent(cleaningAgentId).getName().getContentString());
//        caLocation.setText(CleaningAgent.getCleaningAgent(cleaningAgentId).getMainLanguage().toString());
//        Log.d("Location",CleaningAgent.getCleaningAgent(cleaningAgentId).getMainLanguage().toString());
//        caTag.setMovementMethod(ScrollingMovementMethod.getInstance());
//        Set<Tag> tags = CleaningAgent.getCleaningAgent(cleaningAgentId).getTags();
//        Log.d("Tags",tags.toString());
        caRate.setText(String.valueOf(CleaningAgent.getCleaningAgent(cleaningAgentId).getRate()));
        caInstruction.setText(CleaningAgent.getCleaningAgent(cleaningAgentId).getInstruction().getContentString());
        caDecripition.setText(CleaningAgent.getCleaningAgent(cleaningAgentId).getDescription().getContentString());
        caImage.setImageBitmap(CleaningAgentFetcher.fetchImageOfCleaningAgent(cleaningAgentId));
//        caImage.setImageBitmap(CleaningAgent.getCleaningAgent(cleaningAgentId).getImage());
    }



    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void ConfigureSpinner() {
        ArrayAdapter<CharSequence> languageAdapter = ArrayAdapter.createFromResource(this, R.array.language_type, android.R.layout.simple_spinner_item);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contentSpinner.setAdapter(languageAdapter);
        contentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                User.setContentLanguage(LanguageType.getLanguageType(position));
                config.locale = LanguageType.getLanguageType(position).getLocale();
                getResources().updateConfiguration(config, getResources().getDisplayMetrics());
                User.writeUser();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

}
