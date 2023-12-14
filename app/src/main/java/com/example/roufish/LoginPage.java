package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.roufish.activities.LoginBuyer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LoginPage extends AppCompatActivity {

    Button buttonPembeli,buttonPenjual;
    FloatingActionButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        buttonPembeli = findViewById(R.id.buttonLoginPembeli);
        buttonPenjual = findViewById(R.id.buttonLoginPenjual);
        back = findViewById(R.id.btn_back);
        buttonPembeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(LoginPage.this, LoginBuyer.class);
                startActivity(loginIntent);
            }
        });
        buttonPenjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(LoginPage.this, LoginSeller.class);
                startActivity(loginIntent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent backIntent = new Intent(LoginPage.this, Homepage.class);
                startActivity(backIntent);
            }
        });
    }
}