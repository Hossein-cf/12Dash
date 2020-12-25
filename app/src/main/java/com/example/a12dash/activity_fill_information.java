package com.example.a12dash;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class activity_fill_information extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_information);

        String[] Colors = new String[]{"Pick a Color","Blue", "Red", "Green"};
        String[] Players = new String[]{"Pick a player","First one", "Second one"};

        Spinner firstPlayerColorDropdown = findViewById(R.id.firstPlayerColor);
        ArrayAdapter<String> firstAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Colors);
        firstPlayerColorDropdown.setAdapter(firstAdapter);


        Spinner secondPlayerColorDropdown = findViewById(R.id.secondPlayerColor);
        ArrayAdapter<String> secondAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Colors);
        secondPlayerColorDropdown.setAdapter(secondAdapter);


        Spinner playersDropdown = findViewById(R.id.playersDropdown);
        ArrayAdapter<String> playersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Players);
        playersDropdown.setAdapter(playersAdapter);
//        Bundle b = getIntent().getExtras();
//        TextView txtName = findViewById(R.id.txtxNameFirst);
//        String GameType = b.get("Game_Type").toString();
//        System.out.println(GameType);
//        if (GameType.equals("SinglePlayer")) {
//            txtName.setText("system");
//
//        }
//        Button btnStart = findViewById(R.id.btnStart);
//        btnStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), Game_Page_Activity.class);
//                startActivity(intent);
//            }
//        });
    }
}