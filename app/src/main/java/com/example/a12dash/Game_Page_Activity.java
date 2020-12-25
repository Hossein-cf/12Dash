package com.example.a12dash;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.a12dash.models.player.Player;

import java.util.Objects;
import java.util.TooManyListenersException;

public class Game_Page_Activity extends AppCompatActivity {
    private boolean flag = false;
    private int firstPlayerColor = 0, secondPlayerColor = 0;
    private String firstPlayerName, secondPlayerName, gameStarter;
    private Player playerFirst = new Player();
    private Player playerSecond = new Player();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__page_);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Bundle b = getIntent().getExtras();
        assert b != null;
        firstPlayerColor = b.getInt("firstPlayerColor");
        secondPlayerColor = b.getInt("secondPlayerColor");
        firstPlayerName = b.getString("firstPlayerName");
        secondPlayerName = b.getString("secondPlayerName");
        gameStarter = b.getString("gameStarter");

        assert gameStarter != null;
        if (gameStarter.equals("First one"))
            flag = true;

        System.out.println(firstPlayerColor);
        System.out.println(secondPlayerColor);
        System.out.println(Color.parseColor("#adad85"));

    }

    public void onClick(View view) {

        if (flag&& view.getTag().toString().contains("#ffadad85")) {

            findViewById(view.getId()).setBackgroundColor(firstPlayerColor);
            findViewById(view.getId()).setTag(firstPlayerColor);
            flag = false;
        } else if ( view.getTag().toString().contains("#ffadad85")){
            findViewById(view.getId()).setBackgroundColor(secondPlayerColor);
            findViewById(view.getId()).setTag(secondPlayerColor);

            flag = true;
        }else {
            Toast.makeText(getApplicationContext(),"choose another taw",Toast.LENGTH_SHORT).show();
        }
    }

    public int getItemColor(View view) {
        return (int)view.getTag();
    }
}