package com.example.a12dash;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class activity_fill_information extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_information);
        Bundle b = getIntent().getExtras();
        TextView txtName = findViewById(R.id.txtxName1);
        String GameType = b.get("Game_Type").toString();
        System.out.println(GameType);
        if (GameType.equals("SinglePlayer")) {
            txtName.setText("system");

        }
        Button btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Game_Page_Activity.class);
                startActivity(intent);
            }
        });
    }
}