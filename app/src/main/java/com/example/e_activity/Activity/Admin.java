package com.example.e_activity.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.e_activity.R;

public class Admin extends AppCompatActivity {

    CardView RegisterAkun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        RegisterAkun = findViewById(R.id.register_akun);

        RegisterAkun.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view1) {
                startActivity(new Intent(Admin.this, Register.class));
            }
        });
    }
}