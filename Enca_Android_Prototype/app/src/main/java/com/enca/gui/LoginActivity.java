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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.enca.bl.LanguageType;
import com.enca.bl.User;
import com.enca.controller.DataInitialize;
import com.enca.dao.DatabaseAccess;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    private EditText registerName;
    private Spinner loginInterfaceLanguage;
    private Spinner loginContentLanguage;
    private Button loginButton;
    private TextView loginName;
    Configuration config ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User.initialize();//Initialize the directory and file location, and read preference
        config = new Configuration(getResources().getConfiguration());
        config.locale = Locale.ENGLISH;
        if (User.isFirstUse()) {
            setContentView(R.layout.activity_firstlogin);
            registerName = (EditText) findViewById(R.id.login_name);
            loginInterfaceLanguage = (Spinner) findViewById(R.id.login_spinner_interface);
            loginContentLanguage = (Spinner) findViewById(R.id.login_spinner_content);
            loginButton = (Button) findViewById(R.id.login_comfirm);

            getResources().updateConfiguration(config,getResources().getDisplayMetrics());

            ConfigureSpinner();
            loginButton.setText(getResources().getString(R.string.login_confirm));
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if (CheckInputName()){
                       User.setName(registerName.getText().toString());
                       User.writeUser();
                       Intent intent = new Intent(LoginActivity.this, RoomActivity.class);
                       startActivity(intent);
                   }
                }
            });

        } else {
            setContentView(R.layout.activity_login);
            loginName = (TextView) findViewById(R.id.login_customername);
            loginName.setText(User.getName());
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), RoomActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 2000);
        }

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
                            // do nothing
                        }
                    })
                    .show();
            return false;
        }else
        return true;
    }


    private void ConfigureSpinner() {
        ArrayAdapter<CharSequence> languageAdapter = ArrayAdapter.createFromResource(this, R.array.language_type, android.R.layout.simple_spinner_item);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loginInterfaceLanguage.setAdapter(languageAdapter);
        loginInterfaceLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                User.setInterfaceLanguage(LanguageType.getLanguageType(position));
                config.locale = LanguageType.getLanguageType(position).getLocale();
                getResources().updateConfiguration(config,getResources().getDisplayMetrics());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        loginContentLanguage.setAdapter(languageAdapter);
        loginContentLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                User.setContentLanguage(LanguageType.getLanguageType(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


}
