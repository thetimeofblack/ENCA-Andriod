package com.enca.gui;

import android.content.Intent;
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

import java.util.Date;

public class LoginActivity extends AppCompatActivity {
    private EditText registerName;
    private Spinner loginInterfaceLanguage;
    private Spinner loginContentLanguage;
    private Button loginButton;
    private TextView loginName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (User.isFirstUse()) {
            setContentView(R.layout.activity_firstlogin);
            registerName = (EditText) findViewById(R.id.login_name);
            loginInterfaceLanguage = (Spinner) findViewById(R.id.login_spinner_interface);
            loginContentLanguage = (Spinner) findViewById(R.id.login_spinner_content);

            ArrayAdapter<CharSequence> languageAdapter = ArrayAdapter.createFromResource(this, R.array.language_type, android.R.layout.simple_spinner_item);
            languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            loginInterfaceLanguage.setAdapter(languageAdapter);
            loginInterfaceLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    User.setInterfaceLanguage(LanguageType.getLanguageType(position));
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



        } else {
            setContentView(R.layout.activity_login);
            loginName = (TextView) findViewById(R.id.login_customername);
            loginName.setText(User.getName());
            Toast.makeText(this,User.getName(), Toast.LENGTH_SHORT).show();
        }
        loginButton = (Button) findViewById(R.id.login_comfirm);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (User.isFirstUse()) {
                    User.setFirstUse(false);
                    User.setName(registerName.getText().toString());
                    User.setRegDate(new Date());
                    User.writeUser();
                }
                Intent intent = new Intent(LoginActivity.this, RoomActivity.class);
                startActivity(intent);
            }
        });
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        DataInitialize.initialize();
        databaseAccess.close();
    }
}
