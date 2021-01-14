
package com.example.a12dash;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a12dash.models.player.GameStateForPlayer;
import com.example.a12dash.models.player.Player;
import com.example.a12dash.models.player.PlayerState;
import com.example.a12dash.models.taw.PlayGround;
import com.example.a12dash.models.taw.Position;
import com.example.a12dash.models.taw.TawCondition;
import com.example.a12dash.models.taw.TawPlace;
import com.example.a12dash.service.DeleteTaw;
import com.example.a12dash.service.EvaluateTheGoal;
import com.example.a12dash.service.MinMaxAlgorithm;
import com.example.a12dash.service.Move;
import com.example.a12dash.service.NeighborPlaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Game_Page_Activity extends AppCompatActivity {
    private boolean flag = false;
    public static int firstPlayerColor = 0, secondPlayerColor = 0;
    private String firstPlayerName, secondPlayerName, gameStarter;
    private int firstPlayerTawIndex = 0, secondPlayerTawIndex = 0;
    public static Player playerFirst;
    public static Player playerSecond;
    private PlayGround ground;
    public static TawPlace[][] places;
    private int gameState;
    public static List<Integer> ids;
    public static List<String> btnNames;
    public String Game_Type;
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
        Game_Type = b.getString("Game_Type");

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

    boolean chooseTheTargetTaw = false;
    TawPlace target, source;
    List<TawPlace> neighbor_places;

    @SuppressLint("SetTextI18n")
    public void onClick(View view) {
        String id = view.getResources().getResourceEntryName(view.getId());
        Position position = getTowSelectedPosition(id);

        if (flag) {
            // enter taw to play ground
            if (Game_Type.equals("MultiPlayer")) {
                if (playerFirst.getGameStateForPlayer().value == GameStateForPlayer.ENTER_TAW.value) {
                    if (view.getTag().toString().contains("#ADAD85") && playerFirst.getNumberOfTawInHand() > 0) {
                        firstPlayerPlay(view, position);
                        if (playerSecond.getNumberOfTawInHand() == 0 && playerFirst.getNumberOfTawInHand() == 0) {
                            enableThePlayerTaw(playerSecond);
                            disableThePlayerTaw(playerFirst);
                        }
                    }
                } else if (playerFirst.getGameStateForPlayer().value == GameStateForPlayer.MOVE_TAW.value) {

                    boolean goal = moving(view.getRootView(), position);
                    if (goal) {
                        disableThePlayerTaw(playerFirst);
                        enableThePlayerTaw(playerSecond);
                        chooseTheTargetTaw = false;
                        playerFirst.setGameStateForPlayer(GameStateForPlayer.DELETE_TAW);
                    }
                } else if (playerFirst.getGameStateForPlayer().value == GameStateForPlayer.DELETE_TAW.value) {
                    new DeleteTaw().deleteTaw(view.getRootView(), playerSecond.getId(), position);
                    if (playerFirst.getNumberOfTawInHand() > 0)
                        playerFirst.setGameStateForPlayer(GameStateForPlayer.ENTER_TAW);
                    else {

                        flag = false;
                        enableThePlayerTaw(playerSecond);
                        disableThePlayerTaw(playerFirst);
                        playerFirst.setGameStateForPlayer(GameStateForPlayer.MOVE_TAW);
                    }
                }
            }
        } else {
            if (playerSecond.getGameStateForPlayer().value == GameStateForPlayer.ENTER_TAW.value) {
                if (view.getTag().toString().contains("#ADAD85") && playerSecond.getNumberOfTawInHand() > 0) {
                    secondPlayerPlay(view, position);
                    if (playerSecond.getNumberOfTawInHand() == 0 && playerFirst.getNumberOfTawInHand() == 0) {
                        enableThePlayerTaw(playerFirst);
                        disableThePlayerTaw(playerSecond);
                    }
                }
            } else if (playerSecond.getGameStateForPlayer().value == GameStateForPlayer.MOVE_TAW.value) {

                boolean goal = moving(view.getRootView(),position);
                if (goal) {
                    disableThePlayerTaw(playerSecond);
                    enableThePlayerTaw(playerFirst);
                    chooseTheTargetTaw =false;
                    playerSecond.setGameStateForPlayer(GameStateForPlayer.DELETE_TAW);
                }

            } else if (playerSecond.getGameStateForPlayer().value == GameStateForPlayer.DELETE_TAW.value) {
                new DeleteTaw().deleteTaw(view.getRootView(),playerFirst.getId(), position);
                if (playerSecond.getNumberOfTawInHand() > 0)
                    playerSecond.setGameStateForPlayer(GameStateForPlayer.ENTER_TAW);
                else {
                    flag = true;
                    enableThePlayerTaw(playerFirst);
                    disableThePlayerTaw(playerSecond);
                    playerSecond.setGameStateForPlayer(GameStateForPlayer.MOVE_TAW);
                }
            }
        }

        if (flag&& Game_Type.equals("SinglePlayer")){
            TawPlace place =  new MinMaxAlgorithm(places).getBestWay(places,playerFirst.getGameStateForPlayer(),view.getRootView());
            if (playerFirst.getGameStateForPlayer().value == GameStateForPlayer.ENTER_TAW.value){
                firstPlayerPlay(view , place.getPosition());
            }

        }

        if (!flag) {
            if (playerSecond.getNumberOfTawInHand() > 0) {
                enableTheEmptyPlaces();
            }
            if (playerSecond.getGameStateForPlayer().value == GameStateForPlayer.MOVE_TAW.value)
                disableThePlayerTaw(playerFirst);

        }
        if (flag) {
            if (playerFirst.getNumberOfTawInHand() > 0) {
                enableTheEmptyPlaces();
            }
            if (playerFirst.getGameStateForPlayer().value == GameStateForPlayer.MOVE_TAW.value)
                disableThePlayerTaw(playerSecond);
        }

    }

    public void firstPlayerPlay(View view, Position position) {
        String current_place = "btn" + position.getY() + "_" + position.getX();
        int id = ids.get(btnNames.indexOf(current_place));

        findViewById(id).setBackgroundColor(firstPlayerColor);
        findViewById(id).setTag(firstPlayerColor);

        // set condition to taw
        playerFirst.getTawList().get(firstPlayerTawIndex).setCondition(TawCondition.IN_GAME);
        // set place to taw
        playerFirst.getTawList().get(firstPlayerTawIndex).setPlace(places[position.getY()][position.getX()].getPosition());
        // set taw to play ground
        places[position.getY()][position.getX()].setCurrentTaw(playerFirst.getTawList().get(firstPlayerTawIndex++));
        playerFirst.setNumberOfTawInGame(playerFirst.getNumberOfTawInGame() + 1);
        playerFirst.setNumberOfTawInHand(playerFirst.getNumberOfTawInHand() - 1);

        
        boolean goal = new EvaluateTheGoal(places).checkForGoal(position);
        if (goal) {
            if (playerSecond.getNumberOfTawInHand() > 0) {
                playerSecond.getTawList().remove(playerSecond.getTawList().size() - 1);
                playerSecond.setNumberOfTawInHand(playerSecond.getNumberOfTawInHand() - 1);

            } else {
//                gameState = GameState.DELETE_TAW.value;
                playerFirst.setGameStateForPlayer(GameStateForPlayer.DELETE_TAW);
//                enableTheRivalTaw(flag);
                enableThePlayerTaw(playerSecond);
                disableThePlayerTaw(playerFirst);

            }
            Toast.makeText(getApplicationContext(), "Goooaaaallllllllll", Toast.LENGTH_SHORT).show();
        } else {
            flag = false;
        }
        updateDataInGameLand();

        // change game state
        if (playerFirst.getNumberOfTawInHand() == 0 && playerFirst.getGameStateForPlayer().value != GameStateForPlayer.DELETE_TAW.value) {
            playerFirst.setGameStateForPlayer(GameStateForPlayer.MOVE_TAW);
            disableTheEmptyPlaces();
        }
        if (playerSecond.getNumberOfTawInHand() == 0 && playerSecond.getGameStateForPlayer().value != GameStateForPlayer.DELETE_TAW.value) {
            playerSecond.setGameStateForPlayer(GameStateForPlayer.MOVE_TAW);
            disableTheEmptyPlaces();
        }
    }

    public void secondPlayerPlay(View view, Position position) {

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

        boolean goal = new EvaluateTheGoal(places).checkForGoal(position);
        if (goal) {
            if (playerFirst.getNumberOfTawInHand() > 0) {
                playerFirst.getTawList().remove(playerFirst.getTawList().size() - 1);
                playerFirst.setNumberOfTawInHand(playerFirst.getNumberOfTawInHand() - 1);
            } else {
//                gameState = GameState.DELETE_TAW.value;
                playerSecond.setGameStateForPlayer(GameStateForPlayer.DELETE_TAW);

//                enableTheRivalTaw(flag);
                enableThePlayerTaw(playerFirst);
                disableThePlayerTaw(playerSecond);

            }
            Toast.makeText(getApplicationContext(), "Goooaaaallllllllll", Toast.LENGTH_SHORT).show();

        } else {
            flag = true;
        }
        updateDataInGameLand();

        //change game state
        if (playerSecond.getNumberOfTawInHand() == 0 && playerSecond.getGameStateForPlayer().value != GameStateForPlayer.DELETE_TAW.value) {
            disableTheEmptyPlaces();
            playerSecond.setGameStateForPlayer(GameStateForPlayer.MOVE_TAW);
        }
        if (playerFirst.getNumberOfTawInHand() == 0 && playerFirst.getGameStateForPlayer().value != GameStateForPlayer.DELETE_TAW.value) {
            playerFirst.setGameStateForPlayer(GameStateForPlayer.MOVE_TAW);
            disableTheEmptyPlaces();
        }
    }

    public boolean moving(View view ,Position position) {
        target = null;
        if (chooseTheTargetTaw) {
            target = getTawByPosition(position);

            if (neighbor_places.contains(target)) {
                neighbor_places.forEach(tawPlace -> {
                    String neighbor_placeName = "btn" + tawPlace.getPosition().getY() + "_" + tawPlace.getPosition().getX();
                    String color = findViewById(ids.get(btnNames.indexOf(neighbor_placeName))).getTag().toString();
                    findViewById(ids.get(btnNames.indexOf(neighbor_placeName))).setBackgroundColor(getBrightColors(Color.parseColor(color)));
                    findViewById(ids.get(btnNames.indexOf(neighbor_placeName))).setTag(color);
                });
                //todo check the goal after move taw
                boolean goal = new Move().move(view,source, target,flag);
                if (!goal) {
                    chooseTheTargetTaw = false;
                        if (flag) {
                        enableThePlayerTaw(playerSecond);
                        disableThePlayerTaw(playerFirst);
                        flag = false;
                    } else {
                        enableThePlayerTaw(playerFirst);
                        disableThePlayerTaw(playerSecond);
                        flag = true;
                    }
                }
                neighbor_places = new ArrayList<>();

                return goal;
            } else {
                Toast.makeText(getApplicationContext(), "choose current place", Toast.LENGTH_SHORT).show();

            }

        } else {
            //تغییر رنگ خانه های همسایه قابل حرکت بر روی آن ها
            source = getTawByPosition(position);
            neighbor_places = new NeighborPlaces().findNeighbors(source);
            neighbor_places.forEach(tawPlace -> {

                String neighbor_placeName = "btn" + tawPlace.getPosition().getY() + "_" + tawPlace.getPosition().getX();
                int index = btnNames.indexOf(neighbor_placeName);
                String color = findViewById(ids.get(index)).getTag().toString();
                findViewById(ids.get(index)).setBackgroundColor(getDarkColors(Color.parseColor(color)));
                findViewById(ids.get(index)).setEnabled(true);
                findViewById(ids.get(index)).setTag(color);
            });
            chooseTheTargetTaw = true;
        }
        return false;
    }

//    public void deleteTaw(Player player, Position position) {
//        String placeName = "btn" + position.getY() + "_" + position.getX();
//        findViewById(ids.get(btnNames.indexOf(placeName))).setBackgroundColor(Color.parseColor("#ADAD85"));
//        findViewById(ids.get(btnNames.indexOf(placeName))).setTag("#ADAD85");
//        findViewById(ids.get(btnNames.indexOf(placeName))).setEnabled(false);
//        if (player.getId() == 1) {
//            playerFirst.getTawList().removeIf(taw1 ->
//                    taw1.getPlace().getY() == position.getY() && taw1.getPlace().getX() == position.getX()
//            );
//        } else {
//            playerSecond.getTawList().removeIf(taw1 ->
//                    taw1.getPlace().getY() == position.getY() && taw1.getPlace().getX() == position.getX()
//            );
//
//        }
//        places[position.getY()][position.getX()].setCurrentTaw(null);
//
//    }

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
        return Color.parseColor("#D6D628");
    }

    public int getBrightColors(int color) {

        if (Color.parseColor("#285C03") == color)
            return Color.parseColor("#65FA32");
        if (Color.parseColor("#5C0311") == color)
            return Color.parseColor("#FF0026");
        if (Color.parseColor("#023353") == color)
            return Color.parseColor("#4DB2F6");
        return Color.parseColor("#ADAD85");
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

    public void disableTheEmptyPlaces() {

        ids.forEach(id -> {
            Button button = findViewById(id);
            if (button.getTag().toString().contains("#ADAD85")) {
                button.setEnabled(false);
            }
        });

    }

    public void enableTheEmptyPlaces() {

        ids.forEach(id -> {
            Button button = findViewById(id);
            if (button.getTag().toString().contains("#ADAD85")) {
                button.setEnabled(true);
            }
        });
    }

    public void disableThePlayerTaw(Player player) {

        player.getTawList().forEach(taw -> {
            Position position = taw.getPlace();
            String placeName = "btn" + position.getY() + "_" + position.getX();
            findViewById(ids.get(btnNames.indexOf(placeName))).setEnabled(false);
        });

    }

    public void enableThePlayerTaw(Player player) {

        player.getTawList().forEach(taw -> {
            Position position = taw.getPlace();
            String placeName = "btn" + position.getY() + "_" + position.getX();
            findViewById(ids.get(btnNames.indexOf(placeName))).setEnabled(true);
        });

    }

    public TawPlace getTawByPosition(Position position) {

        return places[position.getY()][position.getX()];
    }

   /* public List<TawPlace> findNeighbors(TawPlace tawPlace) {
        List<TawPlace> placeList = new ArrayList<>();
        if (tawPlace.getLeft() != null) {
            if (getTawByPosition(tawPlace.getLeft()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlace.getLeft()));
        }
        if (tawPlace.getRight() != null) {
            if (getTawByPosition(tawPlace.getRight()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlace.getRight()));
        }
        if (tawPlace.getDown() != null) {
            if (getTawByPosition(tawPlace.getDown()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlace.getDown()));
        }
        if (tawPlace.getTop() != null) {
            if (getTawByPosition(tawPlace.getTop()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlace.getTop()));
        }
        if (tawPlace.getS_E() != null) {
            if (getTawByPosition(tawPlace.getS_E()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlace.getS_E()));
        }
        if (tawPlace.getS_W() != null) {
            if (getTawByPosition(tawPlace.getS_W()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlace.getS_W()));
        }
        if (tawPlace.getN_E() != null) {
            if (getTawByPosition(tawPlace.getN_E()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlace.getN_E()));
        }
        if (tawPlace.getN_W() != null) {
            if (getTawByPosition(tawPlace.getN_W()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlace.getN_W()));
        }
        return placeList;
    }*/



}