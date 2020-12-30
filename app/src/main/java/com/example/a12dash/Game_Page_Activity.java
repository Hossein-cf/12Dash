
package com.example.a12dash;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a12dash.models.player.Player;
import com.example.a12dash.models.player.PlayerState;
import com.example.a12dash.models.taw.PlayGround;
import com.example.a12dash.models.taw.Position;
import com.example.a12dash.models.taw.TawCondition;
import com.example.a12dash.models.taw.TawPlace;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private List<Integer> ids;
    private List<String> btnNames;
    private int convertedColor;

    @SuppressLint({"SetTextI18n", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__page_);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        playerFirst = new Player(1);

        playerSecond = new Player(2);

        ground = new PlayGround();
        places = ground.getTawPlaces();
        gameState = GameState.ENTER_TAW.value;
        ids = new ArrayList<>();
        btnNames = new ArrayList<>();
        addIdOfButtonsToList();

        Bundle b = getIntent().getExtras();
        assert b != null;
        firstPlayerColor = b.getInt("firstPlayerColor");
        secondPlayerColor = b.getInt("secondPlayerColor");

        TextView txtfirstPlayerName = findViewById(R.id.txtfirstPlayerName);
        txtfirstPlayerName.setText(b.getString("firstPlayerName"));

        TextView txtfirstPlayerInHandTawsNumber = findViewById(R.id.txtfirstPlayerInHandTawsNumber);
        txtfirstPlayerInHandTawsNumber.setText("taws in hand :" + "\n" + playerFirst.getTawList().size());

        TextView txtfirstPlayerOutTawsNumber = findViewById(R.id.txtfirstPlayerOutTawsNumber);
        txtfirstPlayerOutTawsNumber.setText("out taws :" + "\n" + 0);

        TextView txtSecondPlayerName = findViewById(R.id.txtsecondPlayerName);
        txtSecondPlayerName.setText(b.getString("secondPlayerName"));

        TextView txtsecondPlayerInHandTawsNumber = findViewById(R.id.txtsecondPlayerInHandTawsNumber);
        txtsecondPlayerInHandTawsNumber.setText("taws in hand " + "\n" + playerSecond.getTawList().size());

        TextView txtsecondPlayerOutTawsNumber = findViewById(R.id.txtsecondPlayerOutTawsNumber);
        txtsecondPlayerOutTawsNumber.setText("out taws :" + "\n" + 0);

        gameStarter = b.getString("gameStarter");

        assert gameStarter != null;
        if (gameStarter.equals("First one")) {
            flag = true;
            TextView txtfirstPlayerCondition = findViewById(R.id.txtfirstPalyerCondition);
            txtfirstPlayerCondition.setText(PlayerState.MY_TURN.name());
            TextView txtsecondPalyerCondition = findViewById(R.id.txtsecondPalyerCondition);
            txtsecondPalyerCondition.setText(PlayerState.WAITING.name());
        } else {
            TextView txtfirstPlayerCondition = findViewById(R.id.txtfirstPalyerCondition);
            txtfirstPlayerCondition.setText(PlayerState.WAITING.name());
            TextView txtsecondPalyerCondition = findViewById(R.id.txtsecondPalyerCondition);
            txtsecondPalyerCondition.setText(PlayerState.MY_TURN.name());
        }
        System.out.println(firstPlayerColor);
        System.out.println(secondPlayerColor);
        System.out.println(Color.parseColor("#adad85"));

    }

    @SuppressLint("SetTextI18n")
    public void onClick(View view) {

        String id = view.getResources().getResourceEntryName(view.getId());
        Position position = getTowSelectedPosition(id);
        if (flag && view.getTag().toString().contains("#ffadad85") && gameState == GameState.ENTER_TAW.value && playerFirst.getNumberOfTawInHand() > 0) {


            findViewById(view.getId()).setBackgroundColor(firstPlayerColor);
            findViewById(view.getId()).setTag(firstPlayerColor);

            // set condition to taw
            playerFirst.getTawList().get(firstPlayerTawIndex).setCondition(TawCondition.IN_GAME);
            // set place to taw
            playerFirst.getTawList().get(firstPlayerTawIndex).setPlace(places[position.getY()][position.getX()].getPosition());
            // set taw to play ground
            places[position.getY()][position.getX()].setCurrentTaw(playerFirst.getTawList().get(firstPlayerTawIndex++));
            playerFirst.setNumberOfTawInGame(playerFirst.getNumberOfTawInGame() + 1);
            playerFirst.setNumberOfTawInHand(playerFirst.getNumberOfTawInHand() - 1);


            boolean goal = checkForGoal(position);
            if (goal) {
                if (playerSecond.getNumberOfTawInHand() > 0) {
                    playerSecond.getTawList().remove(playerSecond.getTawList().size() - 1);
                    playerSecond.setNumberOfTawInHand(playerSecond.getNumberOfTawInHand() - 1);

                } else {
                    gameState = GameState.DELETE_TAW.value;
                    enableTheRivalTaw(flag);
                }
                Toast.makeText(getApplicationContext(), "Goooaaaallllllllll", Toast.LENGTH_SHORT).show();
            }


            flag = false;

            updateDataInGameLand();

            // change game state
            if (playerSecond.getNumberOfTawInHand() == 0 && playerFirst.getNumberOfTawInHand() == 0) {
                changeGameStateToMoveTow();
            }
        } else if (view.getTag().toString().contains("#ffadad85") && gameState == GameState.ENTER_TAW.value && playerSecond.getNumberOfTawInHand() > 0) {
            findViewById(view.getId()).setBackgroundColor(secondPlayerColor);
            findViewById(view.getId()).setTag(secondPlayerColor);


            // set condition to taw
            playerSecond.getTawList().get(secondPlayerTawIndex).setCondition(TawCondition.IN_GAME);
            // set place to taw
            playerSecond.getTawList().get(secondPlayerTawIndex).setPlace(places[position.getY()][position.getX()].getPosition());
            // set taw to play ground
            places[position.getY()][position.getX()].setCurrentTaw(playerSecond.getTawList().get(secondPlayerTawIndex++));
            // update number of taw in or out of game
            playerSecond.setNumberOfTawInGame(playerSecond.getNumberOfTawInGame() + 1);
            playerSecond.setNumberOfTawInHand(playerSecond.getNumberOfTawInHand() - 1);

            boolean goal = checkForGoal(position);
            if (goal) {
                if (playerFirst.getNumberOfTawInHand() > 0) {
                    playerFirst.getTawList().remove(playerFirst.getTawList().size() - 1);
                    playerFirst.setNumberOfTawInHand(playerFirst.getNumberOfTawInHand() - 1);
                } else {
                    gameState = GameState.DELETE_TAW.value;
                    enableTheRivalTaw(flag);
                    //TODO enable the button for delete
                }
                Toast.makeText(getApplicationContext(), "Goooaaaallllllllll", Toast.LENGTH_SHORT).show();

            }
            flag = true;

            updateDataInGameLand();

            //change game state
            if (playerSecond.getNumberOfTawInHand() == 0 && playerFirst.getNumberOfTawInHand() == 0) {
                changeGameStateToMoveTow();
            }
        } else if (gameState == GameState.DELETE_TAW.value) {
            Toast.makeText(getApplicationContext(), "choose another taw", Toast.LENGTH_SHORT).show();
            //change game state
            if (playerSecond.getNumberOfTawInHand() == 0 && playerFirst.getNumberOfTawInHand() == 0) {
                changeGameStateToMoveTow();
            } else {
                changeGameStateToEnterTow();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void updateDataInGameLand() {
        TextView txtfirstPlayerInHandTawsNumber = findViewById(R.id.txtfirstPlayerInHandTawsNumber);
        txtfirstPlayerInHandTawsNumber.setText("taws in hand :" + "\n" + playerFirst.getNumberOfTawInHand());


        TextView txtfirstPlayerOutTawsNumber = findViewById(R.id.txtfirstPlayerOutTawsNumber);
        txtfirstPlayerOutTawsNumber.setText("out taws :" + "\n" + playerFirst.getNumberOfTawDeleted());

        TextView txtsecondPlayerInHandTawsNumber = findViewById(R.id.txtsecondPlayerInHandTawsNumber);
        txtsecondPlayerInHandTawsNumber.setText("taws in hand " + "\n" + playerSecond.getNumberOfTawInHand());

        TextView txtsecondPlayerOutTawsNumber = findViewById(R.id.txtsecondPlayerOutTawsNumber);
        txtsecondPlayerOutTawsNumber.setText("out taws :" + "\n" + playerSecond.getNumberOfTawDeleted());
    }

    public void changeGameStateToMoveTow() {
        gameState = GameState.MOVE_TAW.value;
    }

    public void changeGameStateToDeleteTow() {
        gameState = GameState.DELETE_TAW.value;
    }

    public void changeGameStateToEnterTow() {
        gameState = GameState.ENTER_TAW.value;
    }

    public boolean checkForGoal(Position position) {


        TawPlace place = places[position.getY()][position.getX()];
        int playerId = place.getCurrentTaw().getPlayerId();
        //place.getCurrentTaw().getPlayer();
        if (place.getPosition().getX() == 0 && place.getPosition().getY() == 0) {
            // check down right and s-e
            if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw().getPlayerId() == playerId)
                    return true;

            } else if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw().getPlayerId() == playerId)
                    return true;

            } else if (getTawByPosition(place.getS_E()).getCurrentTaw() != null && getTawByPosition(place.getS_E()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getS_E()).getS_E()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getS_E()).getS_E()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }

            // done
        } else if (place.getPosition().getX() == 3 && place.getPosition().getY() == 0) {
            //check down_left_right
            return down_left_right(place, place.getCurrentTaw().getPlayerId());
            //done

        } else if (place.getPosition().getX() == 3 && place.getPosition().getY() == 4) {
            //check down_left_right

            return down_left_right(place, place.getCurrentTaw().getPlayerId());
            //done

        } else if (place.getPosition().getX() == 6 && place.getPosition().getY() == 0) {
            // check down left and s-w
            if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }

            if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }
            if (getTawByPosition(place.getS_W()).getCurrentTaw() != null && getTawByPosition(place.getS_W()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getS_W()).getS_W()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getS_W()).getS_W()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }
            // done
        } else if (place.getPosition().getX() == 0 && place.getPosition().getY() == 6) {
            //check top right and n-e
            if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }

            if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }
            if (getTawByPosition(place.getN_E()).getCurrentTaw() != null && getTawByPosition(place.getN_E()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getN_E()).getN_E()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getN_E()).getN_E()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }
            //done

        } else if (place.getPosition().getX() == 3 && place.getPosition().getY() == 6) {
            // check top right and left
            return top_left_right(place, place.getCurrentTaw().getPlayerId());
            //done

        } else if (place.getPosition().getX() == 3 && place.getPosition().getY() == 2) {
            // check top right and left
            return top_left_right(place, place.getCurrentTaw().getPlayerId());
            //done

        } else if (place.getPosition().getX() == 6 && place.getPosition().getY() == 6) {
            // check top left and n-w
            if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }

            if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }
            if (getTawByPosition(place.getN_W()).getCurrentTaw() != null && getTawByPosition(place.getN_W()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getN_W()).getN_W()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getN_W()).getN_W()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }
            //done
        } else if (place.getPosition().getY() == 3 && place.getPosition().getX() == 6) {
            // check left top down
            return top_down_left(place, place.getCurrentTaw().getPlayerId());
            // done

        } else if (place.getPosition().getY() == 3 && place.getPosition().getX() == 2) {
            // check left top down(Done)
            return top_down_left(place, place.getCurrentTaw().getPlayerId());

            //done
        } else if (place.getPosition().getY() == 3 && place.getPosition().getX() == 0) {
            // check right top down(Done)
            return top_down_right(place, place.getCurrentTaw().getPlayerId());

            //done
        } else if (place.getPosition().getY() == 3 && place.getPosition().getX() == 4) {
            // check right top down(Done)
            return top_down_right(place, place.getCurrentTaw().getPlayerId());

            //done

        } else if (place.getPosition().getY() == 2 && place.getPosition().getX() == 2) {

            // check left down n-w
            if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }

            if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }
            if (getTawByPosition(place.getN_W()).getCurrentTaw() != null && getTawByPosition(place.getN_W()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getN_W()).getN_W()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getN_W()).getN_W()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }
            // done
        } else if (place.getPosition().getY() == 2 && place.getPosition().getX() == 4) {

            // check down left n-e
            if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }

            if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }
            if (getTawByPosition(place.getN_E()).getCurrentTaw() != null && getTawByPosition(place.getN_E()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getN_E()).getN_E()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getN_E()).getN_E()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }
            //done
        } else if (place.getPosition().getY() == 4 && place.getPosition().getX() == 2) {
            // check top Right s-w
            if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }

            if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }
            if (getTawByPosition(place.getS_W()).getCurrentTaw() != null && getTawByPosition(place.getS_W()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getS_W()).getS_W()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getS_W()).getS_W()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }
            //ended
        } else if (place.getPosition().getY() == 4 && place.getPosition().getX() == 4) {
            // check top left s-e
            if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }

            if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }
            if (getTawByPosition(place.getS_E()).getCurrentTaw() != null && getTawByPosition(place.getS_E()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getS_E()).getS_E()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getS_E()).getS_E()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }
            //ended
        } else if (place.getPosition().getY() == 1 && place.getPosition().getX() == 1) {
            // check down right n-W and s-e
            if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }

            if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }
            if (getTawByPosition(place.getS_E()).getCurrentTaw() != null && getTawByPosition(place.getS_E()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(place.getN_W()).getCurrentTaw() != null && getTawByPosition(place.getN_W()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }
            // done
        } else if (place.getPosition().getY() == 1 && place.getPosition().getX() == 3) {
            // check top left right down
            return top_down_left_right(place, place.getCurrentTaw().getPlayerId());

            //done
        } else if (place.getPosition().getY() == 1 && place.getPosition().getX() == 5) {
            // check down left s-w n_e
            if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }

            if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }
            if (getTawByPosition(place.getN_E()).getCurrentTaw() != null && getTawByPosition(place.getN_E()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(place.getS_W()).getCurrentTaw() != null && getTawByPosition(place.getS_W()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }
            // done
        } else if (place.getPosition().getY() == 3 && place.getPosition().getX() == 1) {
            // check top left right dow
            return top_down_left_right(place, place.getCurrentTaw().getPlayerId());

            //done
        } else if (place.getPosition().getY() == 3 && place.getPosition().getX() == 5) {
            // check top left right down
            return top_down_left_right(place, place.getCurrentTaw().getPlayerId());
            //done

        } else if (place.getPosition().getY() == 5 && place.getPosition().getX() == 1) {
            // check top left  n-e s-w
            if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }

            if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }
            if (getTawByPosition(place.getN_E()).getCurrentTaw() != null && getTawByPosition(place.getN_E()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(place.getS_W()).getCurrentTaw() != null && getTawByPosition(place.getS_W()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }
            //done
        } else if (place.getPosition().getY() == 5 && place.getPosition().getX() == 3) {
            // check top left right down
            return top_down_left_right(place, place.getCurrentTaw().getPlayerId());
            //done

        } else if (place.getPosition().getY() == 5 && place.getPosition().getX() == 5) {
            // check top  right n-w s-e
            if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }

            if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }
            if (getTawByPosition(place.getN_W()).getCurrentTaw() != null && getTawByPosition(place.getN_W()).getCurrentTaw().getPlayerId() == playerId) {
                if (getTawByPosition(place.getS_E()).getCurrentTaw() != null && getTawByPosition(place.getS_E()).getCurrentTaw().getPlayerId() == playerId)
                    return true;
            }
            //done
        }

        return false;
    }

    public boolean down_left_right(TawPlace place, int playerId) {
        if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() == playerId) {
            //check down
            if (getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw().getPlayerId() == playerId)
                return true;
        }
        if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() == playerId) {
            // check right
            if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() == playerId)
                return true;
        }
        return false;
    }

    public boolean top_down_left_right(TawPlace place, int playerId) {
        if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() == playerId) {
            // check top
            if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() == playerId)
                return true;
        }
        if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() == playerId) {
            // check right
            if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() == playerId)
                return true;
        }

        return false;
    }

    public boolean top_left_right(TawPlace place, int playerId) {
        if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() == playerId) {
            //check down
            if (getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw().getPlayerId() == playerId)
                return true;
        }
        if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() == playerId) {
            //check right
            if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() == playerId)
                return true;
        }


        return false;
    }

    public boolean top_down_right(TawPlace place, int playerId) {
        if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() == playerId) {
            //check right
            if (getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw().getPlayerId() == playerId)
                return true;
        }
        if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() == playerId) {
            //check down
            if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() == playerId)
                return true;
        }

        return false;
    }

    public boolean top_down_left(TawPlace place, int playerId) {
        if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() == playerId) {
            //check left
            if (getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw().getPlayerId() == playerId)
                return true;
        }
        if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() == playerId) {
            //check down
            if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() == playerId)
                return true;
        }

        return false;
    }

    public void autoPlay() {
        /*TODO
        بررسی احتمال دوز زدن حرف
        جاوگیری از دوز زدن حریف
         جاوگیری از دوز زدن حریف و ایجاد دوز یک طرفه
         جاوگیری از دوز زدن حریف و ایجاد دوز دو طرفه
         جاوگیری از دوز زدن حریف و ایجاد دوز سه طرفه
         جاوگیری از دوز زدن حریف و زدن دوز
         */
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

    public int getDarkColors(int color) {

        if (Color.parseColor("#65FA32") == color)
            return Color.parseColor("#285C03");
        if (Color.parseColor("#FF0026") == color)
            return Color.parseColor("#5C0311");
        if (Color.parseColor("#4DB2F6") == color)
            return Color.parseColor("#023353");
        return 0;
    }

    public int getBrightColors(int color) {

        if (Color.parseColor("#285C03") == color)
            return Color.parseColor("#65FA32");
        if (Color.parseColor("#5C0311") == color)
            return Color.parseColor("#FF0026");
        if (Color.parseColor("#023353") == color)
            return Color.parseColor("#4DB2F6");
        return 0;
    }

    public void addIdOfButtonsToList() {
        ids.add(R.id.btn0_0);
        ids.add(R.id.btn0_3);
        ids.add(R.id.btn0_6);
        ids.add(R.id.btn1_1);
        ids.add(R.id.btn1_3);
        ids.add(R.id.btn1_5);
        ids.add(R.id.btn2_2);
        ids.add(R.id.btn2_3);
        ids.add(R.id.btn2_4);
        ids.add(R.id.btn3_0);
        ids.add(R.id.btn3_1);
        ids.add(R.id.btn3_2);
        ids.add(R.id.btn3_4);
        ids.add(R.id.btn3_5);
        ids.add(R.id.btn3_6);
        ids.add(R.id.btn4_2);
        ids.add(R.id.btn4_3);
        ids.add(R.id.btn4_4);
        ids.add(R.id.btn5_1);
        ids.add(R.id.btn5_3);
        ids.add(R.id.btn5_5);
        ids.add(R.id.btn6_0);
        ids.add(R.id.btn6_3);
        ids.add(R.id.btn6_6);
        addNameOfButtonsToList(ids);

    }

    public void addNameOfButtonsToList(List<Integer> ids) {
        ids.forEach(id -> {
            String name = getResources().getResourceName(id);
            name = name.substring(name.length() - 6);
            btnNames.add(name);
        });
    }

    public void enableTheRivalTaw(boolean flag) {
        int currentColor;
        // if flag == true delete taw of second player taw list else delete taw of first player taw list
        if (flag) {
            currentColor = secondPlayerColor;
        } else {
            currentColor = firstPlayerColor;
        }
        convertedColor = getDarkColors(currentColor);
        ids.forEach(integer -> {
            if ((int) findViewById(integer).getTag() == (currentColor)) {
                findViewById(integer).setBackgroundColor(convertedColor);
            }
        });

    }

    public void disableTheRivalTaw() {
        ids.forEach(integer -> {
            if ((int) findViewById(integer).getTag() == (convertedColor)) {
                findViewById(integer).setBackgroundColor(getBrightColors(convertedColor));
            }
        });
    }

    public TawPlace getTawByPosition(Position position) {

        return places[position.getY()][position.getX()];
    }



    public List<TawPlace> findWay1(TawPlace tawPlaces) {
        List<TawPlace> placeList = new ArrayList<>();
        if (tawPlaces.getLeft() != null) {
            if (getTawByPosition(tawPlaces.getLeft()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlaces.getLeft()));
        } else if (tawPlaces.getRight() != null) {
            if (getTawByPosition(tawPlaces.getRight()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlaces.getRight()));
        } else if (tawPlaces.getDown() != null) {
            if (getTawByPosition(tawPlaces.getDown()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlaces.getDown()));
        } else if (tawPlaces.getTop() != null) {
            if (getTawByPosition(tawPlaces.getTop()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlaces.getTop()));
        } else if (tawPlaces.getS_E() != null) {
            if (getTawByPosition(tawPlaces.getS_E()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlaces.getS_E()));
        } else if (tawPlaces.getS_W() != null) {
            if (getTawByPosition(tawPlaces.getS_W()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlaces.getS_W()));
        } else if (tawPlaces.getN_E() != null) {
            if (getTawByPosition(tawPlaces.getN_E()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlaces.getN_E()));
        } else if (tawPlaces.getN_W() != null) {
            if (getTawByPosition(tawPlaces.getN_W()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlaces.getN_W()));
        }
        return placeList;
    }
    public void move1(TawPlace tawPlaceOrigin, TawPlace target) {
        target.setCurrentTaw(tawPlaceOrigin.getCurrentTaw());
        tawPlaceOrigin.setCurrentTaw(null);
        String name = "btn" + target.getPosition().getY() + "_" + target.getPosition().getX();
        //TODO set default color to  tawPlaceOrigin
        int id = ids.get(btnNames.indexOf(name));
        findViewById(id).setBackgroundColor(firstPlayerColor);
        findViewById(id).setTag(firstPlayerColor);

    }

    public void findWay(TawPlace tawPlaces) {

        if (tawPlaces.getPosition().getX() == 0 && tawPlaces.getPosition().getY() == 0) {
            if (getTawByPosition(tawPlaces.getRight()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getDown()).getCondition().value == 0) {
                //TODO
            }

        } else if (tawPlaces.getPosition().getX() == 0 && tawPlaces.getPosition().getY() == 3) {
            if (getTawByPosition(tawPlaces.getRight()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getDown()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getLeft()).getCondition().value == 0) {
                //TODO
            }

        } else if (tawPlaces.getPosition().getX() == 0 && tawPlaces.getPosition().getY() == 6) {
            if (getTawByPosition(tawPlaces.getLeft()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getDown()).getCondition().value == 0) {
                //TODO
            }

        } else if (tawPlaces.getPosition().getX() == 1 && tawPlaces.getPosition().getY() == 1) {
            if (getTawByPosition(tawPlaces.getRight()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getN_W()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getS_E()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaces.getPosition().getX() == 1 && tawPlaces.getPosition().getY() == 3) {
            if (getTawByPosition(tawPlaces.getRight()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getTop()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getLeft()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaces.getPosition().getX() == 1 && tawPlaces.getPosition().getY() == 5) {
            if (getTawByPosition(tawPlaces.getLeft()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getS_W()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getN_E()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaces.getPosition().getX() == 2 && tawPlaces.getPosition().getY() == 2) {
            if (getTawByPosition(tawPlaces.getRight()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getN_W()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaces.getPosition().getX() == 2 && tawPlaces.getPosition().getY() == 3) {
            if (getTawByPosition(tawPlaces.getRight()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getTop()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getLeft()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaces.getPosition().getX() == 2 && tawPlaces.getPosition().getY() == 4) {
            if (getTawByPosition(tawPlaces.getLeft()).getCurrentTaw().getId() == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getS_E()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaces.getPosition().getX() == 3 && tawPlaces.getPosition().getY() == 0) {
            if (getTawByPosition(tawPlaces.getRight()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getTop()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaces.getPosition().getX() == 3 && tawPlaces.getPosition().getY() == 1) {
            if (getTawByPosition(tawPlaces.getRight()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getTop()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getLeft()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaces.getPosition().getX() == 3 && tawPlaces.getPosition().getY() == 2) {
            if (getTawByPosition(tawPlaces.getLeft()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getTop()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaces.getPosition().getX() == 3 && tawPlaces.getPosition().getY() == 4) {
            if (getTawByPosition(tawPlaces.getRight()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getTop()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaces.getPosition().getX() == 3 && tawPlaces.getPosition().getY() == 5) {
            if (getTawByPosition(tawPlaces.getRight()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getTop()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getLeft()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaces.getPosition().getX() == 3 && tawPlaces.getPosition().getY() == 6) {
            if (getTawByPosition(tawPlaces.getRight()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getTop()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaces.getPosition().getX() == 4 && tawPlaces.getPosition().getY() == 2) {
            if (getTawByPosition(tawPlaces.getRight()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getTop()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getS_W()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaces.getPosition().getX() == 4 && tawPlaces.getPosition().getY() == 3) {
            if (getTawByPosition(tawPlaces.getRight()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getLeft()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaces.getPosition().getX() == 4 && tawPlaces.getPosition().getY() == 4) {
            if (getTawByPosition(tawPlaces.getTop()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getLeft()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getS_E()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaces.getPosition().getX() == 5 && tawPlaces.getPosition().getY() == 1) {
            if (getTawByPosition(tawPlaces.getRight()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getS_W()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getN_E()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getTop()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaces.getPosition().getX() == 5 && tawPlaces.getPosition().getY() == 3) {
            if (getTawByPosition(tawPlaces.getRight()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getTop()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getLeft()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaces.getPosition().getX() == 5 && tawPlaces.getPosition().getY() == 5) {
            if (getTawByPosition(tawPlaces.getLeft()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getN_W()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getS_E()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getTop()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaces.getPosition().getX() == 6 && tawPlaces.getPosition().getY() == 0) {
            if (getTawByPosition(tawPlaces.getRight()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getTop()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaces.getPosition().getX() == 6 && tawPlaces.getPosition().getY() == 3) {
            if (getTawByPosition(tawPlaces.getRight()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getTop()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getLeft()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaces.getPosition().getX() == 6 && tawPlaces.getPosition().getY() == 6) {
            if (getTawByPosition(tawPlaces.getTop()).getCondition().value == 0) {
                //TODO
            }
            if (getTawByPosition(tawPlaces.getLeft()).getCondition().value == 0) {
                //TODO
            }
        }

    }

    // TODO move method is incurrect
    public void move(TawPlace tawPlaceOrigin, TawPlace targetPlace) {
        if (tawPlaceOrigin.getPosition().getX() == 0 && tawPlaceOrigin.getPosition().getY() == 0) {
            if (getTawByPosition(tawPlaceOrigin.getRight()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getRight()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getDown()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getDown()).getCondition().value == 0) {
                //TODO
            }

        } else if (tawPlaceOrigin.getPosition().getX() == 0 && tawPlaceOrigin.getPosition().getY() == 3) {
            if (getTawByPosition(tawPlaceOrigin.getRight()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getRight()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getDown()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getDown()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getLeft()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getLeft()).getCondition().value == 0) {
                //TODO
            }

        } else if (tawPlaceOrigin.getPosition().getX() == 0 && tawPlaceOrigin.getPosition().getY() == 6) {
            if (getTawByPosition(tawPlaceOrigin.getLeft()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getLeft()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getDown()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getDown()).getCondition().value == 0) {
                //TODO
            }

        } else if (tawPlaceOrigin.getPosition().getX() == 1 && tawPlaceOrigin.getPosition().getY() == 1) {
            if (getTawByPosition(tawPlaceOrigin.getRight()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getRight()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getN_W()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getN_W()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getS_E()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getS_E()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getDown()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaceOrigin.getPosition().getX() == 1 && tawPlaceOrigin.getPosition().getY() == 3) {
            if (getTawByPosition(tawPlaceOrigin.getRight()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getRight()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getTop()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getTop()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getLeft()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getLeft()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getDown()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaceOrigin.getPosition().getX() == 1 && tawPlaceOrigin.getPosition().getY() == 5) {
            if (getTawByPosition(tawPlaceOrigin.getLeft()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getLeft()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getS_W()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getS_W()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getN_E()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getN_E()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getDown()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaceOrigin.getPosition().getX() == 2 && tawPlaceOrigin.getPosition().getY() == 2) {
            if (getTawByPosition(tawPlaceOrigin.getRight()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getRight()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getN_W()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getN_W()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getDown()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaceOrigin.getPosition().getX() == 2 && tawPlaceOrigin.getPosition().getY() == 3) {
            if (getTawByPosition(tawPlaceOrigin.getRight()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getRight()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getTop()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getTop()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getLeft()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getLeft()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaceOrigin.getPosition().getX() == 2 && tawPlaceOrigin.getPosition().getY() == 4) {
            if (getTawByPosition(tawPlaceOrigin.getLeft()).getCurrentTaw().getId() == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getS_E()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getS_E()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getDown()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaceOrigin.getPosition().getX() == 3 && tawPlaceOrigin.getPosition().getY() == 0) {
            if (getTawByPosition(tawPlaceOrigin.getRight()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getRight()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getTop()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getTop()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getDown()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaceOrigin.getPosition().getX() == 3 && tawPlaceOrigin.getPosition().getY() == 1) {
            if (getTawByPosition(tawPlaceOrigin.getRight()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getRight()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getTop()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getTop()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getLeft()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getLeft()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getDown()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaceOrigin.getPosition().getX() == 3 && tawPlaceOrigin.getPosition().getY() == 2) {
            if (getTawByPosition(tawPlaceOrigin.getLeft()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getLeft()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getTop()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getTop()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getDown()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaceOrigin.getPosition().getX() == 3 && tawPlaceOrigin.getPosition().getY() == 4) {
            if (getTawByPosition(tawPlaceOrigin.getRight()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getRight()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getTop()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getTop()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getDown()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaceOrigin.getPosition().getX() == 3 && tawPlaceOrigin.getPosition().getY() == 5) {
            if (getTawByPosition(tawPlaceOrigin.getRight()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getRight()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getTop()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getTop()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getLeft()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getLeft()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getDown()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaceOrigin.getPosition().getX() == 3 && tawPlaceOrigin.getPosition().getY() == 6) {
            if (getTawByPosition(tawPlaceOrigin.getRight()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getRight()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getTop()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getTop()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getDown()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaceOrigin.getPosition().getX() == 4 && tawPlaceOrigin.getPosition().getY() == 2) {
            if (getTawByPosition(tawPlaceOrigin.getRight()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getRight()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getTop()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getTop()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getS_W()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getS_W()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaceOrigin.getPosition().getX() == 4 && tawPlaceOrigin.getPosition().getY() == 3) {
            if (getTawByPosition(tawPlaceOrigin.getRight()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getRight()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getLeft()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getLeft()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getDown()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaceOrigin.getPosition().getX() == 4 && tawPlaceOrigin.getPosition().getY() == 4) {
            if (getTawByPosition(tawPlaceOrigin.getTop()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getTop()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getLeft()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getLeft()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getS_E()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getS_E()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaceOrigin.getPosition().getX() == 5 && tawPlaceOrigin.getPosition().getY() == 1) {
            if (getTawByPosition(tawPlaceOrigin.getRight()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getRight()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getS_W()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getS_W()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getN_E()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getN_E()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getTop()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getTop()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaceOrigin.getPosition().getX() == 5 && tawPlaceOrigin.getPosition().getY() == 3) {
            if (getTawByPosition(tawPlaceOrigin.getRight()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getRight()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getTop()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getTop()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getLeft()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getLeft()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getDown()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getDown()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaceOrigin.getPosition().getX() == 5 && tawPlaceOrigin.getPosition().getY() == 5) {
            if (getTawByPosition(tawPlaceOrigin.getLeft()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getLeft()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getN_W()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getN_W()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getS_E()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getS_E()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getTop()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getTop()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaceOrigin.getPosition().getX() == 6 && tawPlaceOrigin.getPosition().getY() == 0) {
            if (getTawByPosition(tawPlaceOrigin.getRight()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getRight()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getTop()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getTop()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaceOrigin.getPosition().getX() == 6 && tawPlaceOrigin.getPosition().getY() == 3) {
            if (getTawByPosition(tawPlaceOrigin.getRight()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getRight()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getTop()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getTop()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getLeft()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getLeft()).getCondition().value == 0) {
                //TODO
            }
        } else if (tawPlaceOrigin.getPosition().getX() == 6 && tawPlaceOrigin.getPosition().getY() == 6) {
            if (getTawByPosition(tawPlaceOrigin.getTop()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getTop()).getCondition().value == 0) {
                //TODO
            } else if (getTawByPosition(tawPlaceOrigin.getLeft()).getPosition() == targetPlace.getPosition() && getTawByPosition(tawPlaceOrigin.getLeft()).getCondition().value == 0) {
                //TODO
            }
        }

    }
}