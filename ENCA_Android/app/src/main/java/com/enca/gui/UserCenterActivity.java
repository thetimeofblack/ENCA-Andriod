package com.enca.gui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.enca.bl.LanguageType;
import com.enca.bl.User;

/**
 * Providing functions for user to change name and language
 * @author Xiaoqi.Ma
 * @version 02.07.2016
 */
public class UserCenterActivity extends AppCompatActivity {
    private EditText resetName;
    private Spinner userCenterInterfaceLanguage;
    private Spinner userCenterContentLanguage;
    private Button resetButton;
    private Configuration config ;
    private Toolbar toolbar;
    private int interfaceLanguageIndex = 0x0;
    private int contentLanguageIndex = 0x0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        resetName = (EditText) findViewById(R.id.usercenter_name);
        resetName.setText(User.getName());
        userCenterInterfaceLanguage = (Spinner) findViewById(R.id.usercenter_spinner_interface);
        userCenterContentLanguage = (Spinner) findViewById(R.id.usercenter_spinner_content);
        resetButton = (Button) findViewById(R.id.usercenter_confirm);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.usercenter));
        config = new Configuration(getResources().getConfiguration());
        ConfigureSpinner();
        resetButton.setText(getResources().getString(R.string.reset_confirm));
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(! resetName.getText().toString().equals(User.getName())){
                    User.setName(resetName.getText().toString());
                }
                ConfirmReset();
            }
        });

    }
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(getApplicationContext(),RoomActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }
    private void ConfirmReset() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
               builder.setTitle(getResources().getString(R.string.resetdialog))
                .setMessage(getResources().getString(R.string.resetdialogmessage))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (interfaceLanguageIndex != 0) {
                            User.setInterfaceLanguage(LanguageType.getLanguageType(interfaceLanguageIndex-1));
                            config.locale = LanguageType.getLanguageType(interfaceLanguageIndex-1).getLocale();
                            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
                        }
                        if (contentLanguageIndex != 0) {
                            User.setContentLanguage(LanguageType.getLanguageType(contentLanguageIndex-1));
                        }
                        User.writeUser();
                        Intent homeIntent = new Intent(UserCenterActivity.this, RoomActivity.class);
                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(homeIntent);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }



    private void ConfigureSpinner() {
        ArrayAdapter<CharSequence> languageAdapter = ArrayAdapter.createFromResource(this, R.array.language_type, android.R.layout.simple_spinner_item);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userCenterInterfaceLanguage.setAdapter(new NothingSelectedSpinnerAdapter(languageAdapter,R.layout.spinner_nothing_selected,this));
        userCenterInterfaceLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                interfaceLanguageIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                getResources().updateConfiguration(config, getResources().getDisplayMetrics());
            }
        });
        userCenterInterfaceLanguage.setSelection(User.getInterfaceLanguage().getId() + 1);

        userCenterContentLanguage.setAdapter(new NothingSelectedSpinnerAdapter(languageAdapter,R.layout.spinner_nothing_selected,this));
        userCenterContentLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                contentLanguageIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        userCenterContentLanguage.setSelection(User.getContentLanguage().getId() + 1);
    }

}
