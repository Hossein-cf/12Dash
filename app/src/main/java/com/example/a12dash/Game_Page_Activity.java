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
import com.example.a12dash.models.taw.PlayGround;
import com.example.a12dash.models.taw.Position;
import com.example.a12dash.models.taw.Taw;
import com.example.a12dash.models.taw.TawCondition;
import com.example.a12dash.models.taw.TawPlace;

import java.util.List;
import java.util.Objects;
import java.util.TooManyListenersException;

public class Game_Page_Activity extends AppCompatActivity {
    private boolean flag = false;
    private int firstPlayerColor = 0, secondPlayerColor = 0;
    private String firstPlayerName, secondPlayerName, gameStarter;
    private int firstPlayerTawIndex = 0, secondPlayerTawIndex = 0;
    private Player playerFirst;
    private Player playerSecond;
    private PlayGround ground;
    private TawPlace[][] places;
    private int gameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__page_);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Objects.requireNonNull(getSupportActionBar()).hide();

        playerFirst = new Player();
        playerSecond = new Player();
        ground = new PlayGround();
        places = ground.getTawPlaces();
        gameState = GameState.ENTER_TAW.value;

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

        String id = view.getResources().getResourceEntryName(view.getId());
        Position position = getTowSelectedPosition(id);
        if (flag && view.getTag().toString().contains("#ffadad85")) {


            findViewById(view.getId()).setBackgroundColor(firstPlayerColor);
            findViewById(view.getId()).setTag(firstPlayerColor);
            flag = false;

            // set condition to taw
            playerFirst.getTawList().get(firstPlayerTawIndex).setCondition(TawCondition.IN_GAME);
            // set place to taw
            playerFirst.getTawList().get(firstPlayerTawIndex).setPlace(places[position.getY()][position.getX()]);
            // set taw to play ground
            places[position.getY()][position.getX()].setCurrentTaw(playerFirst.getTawList().get(firstPlayerTawIndex++));


        } else if (view.getTag().toString().contains("#ffadad85")) {
            findViewById(view.getId()).setBackgroundColor(secondPlayerColor);
            findViewById(view.getId()).setTag(secondPlayerColor);

            flag = true;

            // set condition to taw
            playerSecond.getTawList().get(secondPlayerTawIndex).setCondition(TawCondition.IN_GAME);
            // set place to taw
            playerSecond.getTawList().get(secondPlayerTawIndex).setPlace(places[position.getY()][position.getX()]);
            // set taw to play ground
            places[position.getY()][position.getX()].setCurrentTaw(playerSecond.getTawList().get(secondPlayerTawIndex++));


        } else {
            Toast.makeText(getApplicationContext(), "choose another taw", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean checkForGoal(Position position) {


        TawPlace place = places[position.getY()][position.getX()];
        //place.getCurrentTaw().getPlayer().getId();
        if (place.getTop().getPosition().getX() == 0 && place.getTop().getPosition().getY() == 0) {
            //TODO check down right and s-e


        } else if (place.getTop().getPosition().getX() == 3 && place.getTop().getPosition().getY() == 0) {
            if(down_left_right(place,place.getCurrentTaw().getPlayer().getId())){

            }


        } else if (place.getTop().getPosition().getX() == 3 && place.getTop().getPosition().getY() == 4) {

            if(down_left_right(place,place.getCurrentTaw().getPlayer().getId())){

            }


        } else if (place.getTop().getPosition().getX() == 6 && place.getTop().getPosition().getY() == 0) {
            //TODO check down left and s-w

        } else if (place.getTop().getPosition().getX() == 0 && place.getTop().getPosition().getY() == 6) {
            //TODO check top right and n-e


        } else if (place.getTop().getPosition().getX() == 3 && place.getTop().getPosition().getY() == 6) {
            //TODO check top right and left(Done)
            if(top_left_right(place,place.getCurrentTaw().getPlayer().getId())){

            }

        } else if (place.getTop().getPosition().getX() == 3 && place.getTop().getPosition().getY() == 2) {
            //TODO check top right and left(Done)
            if(top_left_right(place,place.getCurrentTaw().getPlayer().getId())){

            }


        } else if (place.getTop().getPosition().getX() == 6 && place.getTop().getPosition().getY() == 6) {
            //TODO check top left and n-w

        } else if (place.getTop().getPosition().getY() == 3 && place.getTop().getPosition().getX() == 6) {
            //TODO check left top down(Done)
            if(top_down_left(place,place.getCurrentTaw().getPlayer().getId())){

            }

        } else if (place.getTop().getPosition().getY() == 3 && place.getTop().getPosition().getX() == 2) {
            //TODO check left top down(Done)
            if(top_down_left(place,place.getCurrentTaw().getPlayer().getId())){

            }


        } else if (place.getTop().getPosition().getY() == 3 && place.getTop().getPosition().getX() == 0) {
            //TODO check right top down(Done)
            if(top_down_right(place,place.getCurrentTaw().getPlayer().getId())){

            }


        } else if (place.getTop().getPosition().getY() == 3 && place.getTop().getPosition().getX() == 4) {
            //TODO check right top down(Done)
            if(top_down_right(place,place.getCurrentTaw().getPlayer().getId())){

            }

        } else if (place.getTop().getPosition().getY() == 2 && place.getTop().getPosition().getX() == 2) {

            //TODO check left down n-w

        } else if (place.getTop().getPosition().getY() == 2 && place.getTop().getPosition().getX() == 4) {

            //TODO check down Right n-e

        } else if (place.getTop().getPosition().getY() == 4 && place.getTop().getPosition().getX() == 2) {
            //TODO check top Right s-w

        } else if (place.getTop().getPosition().getY() == 4 && place.getTop().getPosition().getX() == 4) {
            //TODO check top left s-e

        } else if (place.getTop().getPosition().getY() == 1 && place.getTop().getPosition().getX() == 1) {
            //TODO check down right n-W and s-e


        } else if (place.getTop().getPosition().getY() == 1 && place.getTop().getPosition().getX() == 3) {
            //TODO check top left right down(Done)
            if(top_down_left_right(place,place.getCurrentTaw().getPlayer().getId())){

            }


        } else if (place.getTop().getPosition().getY() == 1 && place.getTop().getPosition().getX() == 5) {
            //TODO check down left s-w n_e

        } else if (place.getTop().getPosition().getY() == 3 && place.getTop().getPosition().getX() == 1) {
            //TODO check top left right down(Done)
            if(top_down_left_right(place,place.getCurrentTaw().getPlayer().getId())){

            }

        } else if (place.getTop().getPosition().getY() == 3 && place.getTop().getPosition().getX() == 5) {
            //TODO check top left right down(Done)
            if(top_down_left_right(place,place.getCurrentTaw().getPlayer().getId())){

            }


        } else if (place.getTop().getPosition().getY() == 5 && place.getTop().getPosition().getX() == 1) {
            //TODO check top left  n-e s-w

        } else if (place.getTop().getPosition().getY() == 5 && place.getTop().getPosition().getX() == 3) {
            //TODO check top left right down(Done)
            if(top_down_left_right(place,place.getCurrentTaw().getPlayer().getId())){

            }


        } else if (place.getTop().getPosition().getY() == 5 && place.getTop().getPosition().getX() == 5) {
            //TODO check top  right n-w s-e

        }

        return false;
    }

    public boolean down_left_right(TawPlace place , int playerId) {
            if(place.getDown().getCurrentTaw() != null && place.getDown().getCurrentTaw().getPlayer().getId() == playerId){
                //check down
                    return (place.getDown()).getDown().getCurrentTaw() !=null && (place.getDown()).getDown().getCurrentTaw().getPlayer().getId() == playerId ;
            }
            if(place.getLeft().getCurrentTaw() != null && place.getLeft().getCurrentTaw().getPlayer().getId() == playerId){
                // check right
                    return place.getRight().getCurrentTaw() != null && place.getRight().getCurrentTaw().getPlayer().getId() == playerId;
            }
        return false;
    }

    public boolean top_down_left_right(TawPlace place , int playerId) {
            if(place.getDown().getCurrentTaw() != null && place.getDown().getCurrentTaw().getPlayer().getId() == playerId){
                // check top
                return place.getTop().getCurrentTaw() !=null && place.getTop().getCurrentTaw().getPlayer().getId() == playerId;
            }
            if(place.getLeft().getCurrentTaw() !=null && place.getLeft().getCurrentTaw().getPlayer().getId() == playerId){
                // check right
                return place.getRight().getCurrentTaw() !=null && place.getRight().getCurrentTaw().getPlayer().getId() == playerId;
            }

        return false;
    }



    public boolean top_left_right(TawPlace place , int playerId) {
        if(place.getTop().getCurrentTaw() !=null && place.getTop().getCurrentTaw().getPlayer().getId() == playerId){
            //check down
            return place.getDown().getCurrentTaw() !=null && place.getDown().getCurrentTaw().getPlayer().getId() == playerId;
        }
        if(place.getLeft().getCurrentTaw() !=null && place.getLeft().getCurrentTaw().getPlayer().getId() == playerId){
            //check right
            return place.getRight().getCurrentTaw() !=null && place.getRight().getCurrentTaw().getPlayer().getId() == playerId;
        }


        return false;
    }

    public boolean top_down_right(TawPlace place , int playerId) {
        if(place.getRight().getCurrentTaw() !=null && place.getRight().getCurrentTaw().getPlayer().getId() == playerId){
            //check right
            return (place.getRight()).getRight().getCurrentTaw() !=null && (place.getRight()).getRight().getCurrentTaw().getPlayer().getId() == playerId;
        }
        if(place.getTop().getCurrentTaw() !=null && place.getTop().getCurrentTaw().getPlayer().getId() == playerId){
            //check down
            return place.getDown().getCurrentTaw() !=null && place.getDown().getCurrentTaw().getPlayer().getId() == playerId;
        }

        return false;
    }

    public boolean top_down_left(TawPlace place , int playerId) {
        if(place.getLeft().getCurrentTaw() !=null && place.getLeft().getCurrentTaw().getPlayer().getId() == playerId){
            //check right
            return (place.getLeft()).getLeft().getCurrentTaw() !=null && (place.getLeft()).getLeft().getCurrentTaw().getPlayer().getId() == playerId;
        }
        if(place.getTop().getCurrentTaw() !=null && place.getTop().getCurrentTaw().getPlayer().getId() == playerId){
            //check down
            return place.getDown().getCurrentTaw() !=null && place.getDown().getCurrentTaw().getPlayer().getId() == playerId;
        }

        return false;
    }

    public void autoPlay() {
        //TODO
    }

    public void attack() {
        //TODO
    }

    public void defend() {
        //TODO
    }


    public Position getTowSelectedPosition(String id) {
        Position position = new Position();
        position.setX(Integer.parseInt(id.charAt(id.length() - 1) + ""));
        position.setY(Integer.parseInt(id.charAt(id.length() - 3) + ""));

        return position;
    }

    public int getItemColor(View view) {
        return (int) view.getTag();
    }
}