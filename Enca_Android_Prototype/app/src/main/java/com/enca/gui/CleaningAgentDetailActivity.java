package com.enca.gui;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
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
    private TextView caName, caDecripition, caRate, caInstruction;
    private ImageView caImage;
    private Spinner contentSpinner;
    private Configuration config ;
    Toolbar toolbar;
    private int cleaningAgentId;
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
        caRate = (TextView) findViewById(R.id.cleaning_agent_rate_detail);
        caInstruction = (TextView) findViewById(R.id.cleaning_agent_instruction_detail);
        caImage = (ImageView) findViewById(R.id.cleaning_agent_image_detail);
        contentSpinner = (Spinner) findViewById(R.id.cleaning_agent_spinner_content);
        config = new Configuration(getResources().getConfiguration());
        ConfigureSpinner();
        Intent intent = getIntent();
        cleaningAgentId= intent.getIntExtra("CleaningAgentId",0);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        showDetailInformation(cleaningAgentId);
    }

    private void showDetailInformation(int cleaningAgentId) {
        caName.setMovementMethod(ScrollingMovementMethod.getInstance());
        caName.setText(CleaningAgent.getCleaningAgent(cleaningAgentId).getName().getContentString());
        caRate.setText(String.valueOf(CleaningAgent.getCleaningAgent(cleaningAgentId).getRate()));
        caInstruction.setText(CleaningAgent.getCleaningAgent(cleaningAgentId).getInstruction().getContentString());
        caDecripition.setText(CleaningAgent.getCleaningAgent(cleaningAgentId).getDescription().getContentString());
        caImage.setImageBitmap(CleaningAgentFetcher.fetchImageOfCleaningAgent(cleaningAgentId));
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
//        contentSpinner.setAdapter(languageAdapter);
        contentSpinner.setAdapter(new NothingSelectedSpinnerAdapter(languageAdapter,R.layout.spinner_nothing_selected,this));
        contentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0){
                    User.setContentLanguage(User.getContentLanguage());
                }else
                User.setContentLanguage(LanguageType.getLanguageType(position-1));
                showDetailInformation(cleaningAgentId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

}
