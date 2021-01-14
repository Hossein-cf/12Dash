package com.example.a12dash;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class activity_fill_information extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_information);

        Bundle bundle = getIntent().getExtras();

        String Game_Type = bundle.getString("Game_Type");

        getSupportActionBar().hide();
        String[] Colors = new String[]{"Pick a Color", "Blue", "Red", "Green"};
        String[] Players = new String[]{"Pick a player", "First one", "Second one"};
        int firstColor, secondColor;
        Button button = findViewById(R.id.btnStartGame);
        EditText txtFirstPlayerName = findViewById(R.id.txtFirstPlayerName);
        EditText txtSecondPlayerName = findViewById(R.id.txtSecondPlayerName);
        Spinner firstPlayerColorDropdown = findViewById(R.id.firstPlayerColor);
        ArrayAdapter<String> firstAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Colors);
        firstPlayerColorDropdown.setAdapter(firstAdapter);


        Spinner secondPlayerColorDropdown = findViewById(R.id.secondPlayerColor);
        ArrayAdapter<String> secondAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Colors);
        secondPlayerColorDropdown.setAdapter(secondAdapter);


        Spinner playersDropdown = findViewById(R.id.playersDropdown);
        ArrayAdapter<String> playersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Players);
        playersDropdown.setAdapter(playersAdapter);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstPlayerColorDropdown.getSelectedItem().toString().equals(secondPlayerColorDropdown.getSelectedItem().toString()) || txtFirstPlayerName.getText().equals(txtSecondPlayerName.getText()))
                    alert();
                else {

                    System.out.println(firstPlayerColorDropdown.getSelectedItem().toString());
                    Intent intent = new Intent(getApplicationContext(), Game_Page_Activity.class);
                    intent.putExtra("firstPlayerColor", getColor(firstPlayerColorDropdown.getSelectedItem().toString()));
                    intent.putExtra("secondPlayerColor", getColor(secondPlayerColorDropdown.getSelectedItem().toString()));
                    intent.putExtra("firstPlayerName", txtFirstPlayerName.getText().toString());
                    intent.putExtra("secondPlayerName", txtSecondPlayerName.getText().toString());
                    intent.putExtra("gameStarter", playersDropdown.getSelectedItem().toString());
                    intent.putExtra("Game_Type", Game_Type);
                    startActivity(intent);
                }
            }
        });

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


    public void alert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity_fill_information.this);

        alert.setTitle("Color are equal")
                .setMessage("please choose diferent color")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }


    public int getColor(String colorName) {

        if (colorName.equals("Green"))
            return Color.parseColor("#65FA32");
        if (colorName.equals("Red"))
            return Color.parseColor("#FF0026");
        if (colorName.equals("Blue"))
            return Color.parseColor("#4DB2F6");
        return 0;
    }
}