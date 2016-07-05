package com.enca.gui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.enca.bl.LanguageType;
import com.enca.bl.User;
import com.enca.controller.DataInitialize;
import com.enca.dao.DatabaseAccess;

/**
 * Login interface, providing user custom choice of language
 *
 * @author Xiaoqi.Ma
 * @version 02.07.2016
 */
public class LoginActivity extends AppCompatActivity {
    private EditText registerName;
    private Spinner loginInterfaceLanguage;
    private Spinner loginContentLanguage;
    private ImageButton loginLogo;
    Button loginButton;
    TextView loginName;
    Configuration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User.initialize();//Initialize the directory and file location, and read preference
        config = new Configuration(getResources().getConfiguration());
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        //If it is the first time for user to utilize the software, then get into this interface
        if (User.isFirstUse()) {
            setContentView(R.layout.activity_firstlogin);
            registerName = (EditText) findViewById(R.id.login_name);
            loginInterfaceLanguage = (Spinner) findViewById(R.id.login_spinner_interface);
            loginContentLanguage = (Spinner) findViewById(R.id.login_spinner_content);
            loginButton = (Button) findViewById(R.id.login_confirm);
            loginLogo = (ImageButton) findViewById(R.id.login_logo);

            loginLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this, ReadMeActivity.class);
                    startActivity(intent);
                }
            });
            ConfigureSpinner();
            loginButton.setText(getResources().getString(R.string.login_confirm));
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CheckInputName()) {
                        User.setName(registerName.getText().toString());
                        User.writeUser();
                        Intent intent = new Intent(LoginActivity.this, RoomActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            });

        }
        //Otherwise, if the user has registered, then enter this interface
        else {
            setContentView(R.layout.activity_login);
            loginName = (TextView) findViewById(R.id.login_customername);
            String heyName = getResources().getString(R.string.hey) + ", " + User.getName();
            loginName.setText(heyName);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), RoomActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 1500);
        }
        //Get access to the database
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        DataInitialize.initialize();
        databaseAccess.close();

    }

    private boolean CheckInputName() {
        if (registerName.getText().toString().equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.inputinvalid))
                    .setMessage(getResources().getString(R.string.noinput))
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
            return false;
        } else
            return true;
    }


    private void ConfigureSpinner() {
        ArrayAdapter<CharSequence> languageAdapter = ArrayAdapter.createFromResource(this, R.array.language_type, android.R.layout.simple_spinner_item);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loginInterfaceLanguage.setAdapter(new NothingSelectedSpinnerAdapter(languageAdapter, R.layout.spinner_nothing_selected, this));
        loginInterfaceLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {
                    User.setInterfaceLanguage(LanguageType.getLanguageType(position - 1));
                    config.locale = LanguageType.getLanguageType(position - 1).getLocale();
                    getResources().updateConfiguration(config, getResources().getDisplayMetrics());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        loginContentLanguage.setAdapter(new NothingSelectedSpinnerAdapter(languageAdapter, R.layout.spinner_nothing_selected, this));
        loginContentLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {
                    User.setContentLanguage(LanguageType.getLanguageType(position - 1));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


}
