package com.example.a12dash;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a12dash.models.player.Player;
import com.example.a12dash.models.taw.PlayGround;
import com.example.a12dash.models.taw.Position;
import com.example.a12dash.models.taw.TawCondition;
import com.example.a12dash.models.taw.TawPlace;

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
        int playerId = place.getCurrentTaw().getPlayer().getId();
        //place.getCurrentTaw().getPlayer().getId();
        if (place.getTop().getPosition().getX() == 0 && place.getTop().getPosition().getY() == 0) {
            // check down right and s-e
            if (place.getRight().getCurrentTaw() != null && place.getRight().getCurrentTaw().getPlayer().getId() == playerId) {
                if (place.getRight().getRight().getCurrentTaw() != null && place.getRight().getRight().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;

            } else if (place.getDown().getCurrentTaw() != null && place.getDown().getCurrentTaw().getPlayer().getId() == playerId) {
                if (place.getDown().getDown().getCurrentTaw() != null && place.getDown().getDown().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;

            } else if (place.getS_E().getCurrentTaw() != null && place.getS_E().getCurrentTaw().getPlayer().getId() == playerId) {
                if (place.getS_E().getS_E().getCurrentTaw() != null && place.getS_E().getS_E().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }

            // done
        } else if (place.getTop().getPosition().getX() == 3 && place.getTop().getPosition().getY() == 0) {
            //check down_left_right
            return down_left_right(place, place.getCurrentTaw().getPlayer().getId());
            //done

        } else if (place.getTop().getPosition().getX() == 3 && place.getTop().getPosition().getY() == 4) {
            //check down_left_right

            return down_left_right(place, place.getCurrentTaw().getPlayer().getId());
            //done

        } else if (place.getTop().getPosition().getX() == 6 && place.getTop().getPosition().getY() == 0) {
            // check down left and s-w
            if (place.getDown().getCurrentTaw() != null && place.getDown().getCurrentTaw().getPlayer().getId() == playerId) {
                if (place.getDown().getDown().getCurrentTaw() != null && place.getDown().getDown().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }

            if (place.getLeft().getCurrentTaw() != null && place.getLeft().getCurrentTaw().getPlayer().getId() == playerId) {
                if (place.getLeft().getLeft().getCurrentTaw() != null && place.getLeft().getLeft().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }
            if (place.getS_W().getCurrentTaw() != null && place.getS_W().getCurrentTaw().getPlayer().getId() == playerId) {
                if (place.getS_W().getS_W().getCurrentTaw() != null && place.getS_W().getS_W().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }
            // done
        } else if (place.getTop().getPosition().getX() == 0 && place.getTop().getPosition().getY() == 6) {
            //check top right and n-e
            if (place.getTop().getCurrentTaw() != null && place.getTop().getCurrentTaw().getPlayer().getId() == playerId) {
                if (place.getTop().getTop().getCurrentTaw() != null && place.getTop().getTop().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }

            if (place.getRight().getCurrentTaw() != null && place.getRight().getCurrentTaw().getPlayer().getId() == playerId) {
                if (place.getRight().getRight().getCurrentTaw() != null && place.getRight().getRight().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }
            if (place.getN_E().getCurrentTaw() != null && place.getN_E().getCurrentTaw().getPlayer().getId() == playerId) {
                if (place.getN_E().getN_E().getCurrentTaw() != null && place.getN_E().getN_E().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }
            //done

        } else if (place.getTop().getPosition().getX() == 3 && place.getTop().getPosition().getY() == 6) {
            // check top right and left
            return top_left_right(place, place.getCurrentTaw().getPlayer().getId());
            //done

        } else if (place.getTop().getPosition().getX() == 3 && place.getTop().getPosition().getY() == 2) {
            // check top right and left
            return top_left_right(place, place.getCurrentTaw().getPlayer().getId());
            //done

        } else if (place.getTop().getPosition().getX() == 6 && place.getTop().getPosition().getY() == 6) {
            // check top left and n-w
            if (place.getTop().getCurrentTaw() != null && place.getTop().getCurrentTaw().getPlayer().getId() == playerId) {
                if (place.getTop().getTop().getCurrentTaw() != null && place.getTop().getTop().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }

            if (place.getLeft().getCurrentTaw() != null && place.getLeft().getCurrentTaw().getPlayer().getId() == playerId) {
                if (place.getLeft().getLeft().getCurrentTaw() != null && place.getLeft().getLeft().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }
            if (place.getN_W().getCurrentTaw() != null && place.getN_W().getCurrentTaw().getPlayer().getId() == playerId) {
                if (place.getN_W().getN_W().getCurrentTaw() != null && place.getN_W().getN_W().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }
            //done
        } else if (place.getTop().getPosition().getY() == 3 && place.getTop().getPosition().getX() == 6) {
            // check left top down
            return top_down_left(place, place.getCurrentTaw().getPlayer().getId());
            // done

        } else if (place.getTop().getPosition().getY() == 3 && place.getTop().getPosition().getX() == 2) {
            // check left top down(Done)
           return top_down_left(place, place.getCurrentTaw().getPlayer().getId());

            //done
        } else if (place.getTop().getPosition().getY() == 3 && place.getTop().getPosition().getX() == 0) {
            // check right top down(Done)
            return top_down_right(place, place.getCurrentTaw().getPlayer().getId());

            //done
        } else if (place.getTop().getPosition().getY() == 3 && place.getTop().getPosition().getX() == 4) {
            // check right top down(Done)
            return top_down_right(place, place.getCurrentTaw().getPlayer().getId());

            //done

        } else if (place.getTop().getPosition().getY() == 2 && place.getTop().getPosition().getX() == 2) {

            // check left down n-w
            if (place.getDown().getCurrentTaw() != null && place.getDown().getCurrentTaw().getPlayer().getId() == playerId) {
                if (place.getDown().getDown().getCurrentTaw() != null && place.getDown().getDown().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }

            if (place.getRight().getCurrentTaw() != null &&place.getRight().getCurrentTaw().getPlayer().getId() ==playerId ) {
                if (place.getRight().getRight().getCurrentTaw() != null && place.getRight().getRight().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }
            if (place.getN_W().getCurrentTaw() != null &&place.getN_W().getCurrentTaw().getPlayer().getId() ==playerId ) {
                if (place.getN_W().getN_W().getCurrentTaw() != null && place.getN_W().getN_W().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }
            // done
        } else if (place.getTop().getPosition().getY() == 2 && place.getTop().getPosition().getX() == 4) {

            // check down left n-e
            if (place.getDown().getCurrentTaw() != null && place.getDown().getCurrentTaw().getPlayer().getId() == playerId) {
                if (place.getDown().getDown().getCurrentTaw() != null && place.getDown().getDown().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }

            if (place.getLeft().getCurrentTaw() != null &&place.getLeft().getCurrentTaw().getPlayer().getId() ==playerId ) {
                if (place.getLeft().getLeft().getCurrentTaw() != null && place.getLeft().getLeft().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }
            if (place.getN_E().getCurrentTaw() != null &&place.getN_E().getCurrentTaw().getPlayer().getId() ==playerId ) {
                if (place.getN_E().getN_E().getCurrentTaw() != null && place.getN_E().getN_E().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }
            //done
        } else if (place.getTop().getPosition().getY() == 4 && place.getTop().getPosition().getX() == 2) {
            // check top Right s-w
            if (place.getTop().getCurrentTaw() != null && place.getTop().getCurrentTaw().getPlayer().getId() == playerId) {
                if (place.getTop().getTop().getCurrentTaw() != null && place.getTop().getTop().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }

            if (place.getRight().getCurrentTaw() != null &&place.getRight().getCurrentTaw().getPlayer().getId() ==playerId ) {
                if (place.getRight().getRight().getCurrentTaw() != null && place.getRight().getRight().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }
            if (place.getS_W().getCurrentTaw() != null &&place.getS_W().getCurrentTaw().getPlayer().getId() ==playerId ) {
                if (place.getS_W().getS_W().getCurrentTaw() != null && place.getS_W().getS_W().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }
            //ended
        } else if (place.getTop().getPosition().getY() == 4 && place.getTop().getPosition().getX() == 4) {
            // check top left s-e
            if (place.getTop().getCurrentTaw() != null && place.getTop().getCurrentTaw().getPlayer().getId() == playerId) {
                if (place.getTop().getTop().getCurrentTaw() != null && place.getTop().getTop().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }

            if (place.getLeft().getCurrentTaw() != null &&place.getLeft().getCurrentTaw().getPlayer().getId() ==playerId ) {
                if (place.getLeft().getLeft().getCurrentTaw() != null && place.getLeft().getLeft().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }
            if (place.getS_E().getCurrentTaw() != null &&place.getS_E().getCurrentTaw().getPlayer().getId() ==playerId ) {
                if (place.getS_E().getS_E().getCurrentTaw() != null && place.getS_E().getS_E().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }
            //ended
        } else if (place.getTop().getPosition().getY() == 1 && place.getTop().getPosition().getX() == 1) {
            // check down right n-W and s-e
            if (place.getDown().getCurrentTaw() != null && place.getDown().getCurrentTaw().getPlayer().getId() == playerId) {
                if (place.getDown().getDown().getCurrentTaw() != null && place.getDown().getDown().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }

            if (place.getRight().getCurrentTaw() != null &&place.getRight().getCurrentTaw().getPlayer().getId() ==playerId ) {
                if (place.getRight().getRight().getCurrentTaw() != null && place.getRight().getRight().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }
            if (place.getS_E().getCurrentTaw() != null &&place.getS_E().getCurrentTaw().getPlayer().getId() ==playerId ) {
                if (place.getN_W().getCurrentTaw() != null && place.getN_W().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }
            // done
        } else if (place.getTop().getPosition().getY() == 1 && place.getTop().getPosition().getX() == 3) {
            // check top left right down
            return top_down_left_right(place, place.getCurrentTaw().getPlayer().getId());

            //done
        } else if (place.getTop().getPosition().getY() == 1 && place.getTop().getPosition().getX() == 5) {
            // check down left s-w n_e
            if (place.getDown().getCurrentTaw() != null && place.getDown().getCurrentTaw().getPlayer().getId() == playerId) {
                if (place.getDown().getDown().getCurrentTaw() != null && place.getDown().getDown().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }

            if (place.getLeft().getCurrentTaw() != null &&place.getLeft().getCurrentTaw().getPlayer().getId() ==playerId ) {
                if (place.getLeft().getLeft().getCurrentTaw() != null && place.getLeft().getLeft().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }
            if (place.getN_E().getCurrentTaw() != null &&place.getN_E().getCurrentTaw().getPlayer().getId() ==playerId ) {
                if (place.getS_W().getCurrentTaw() != null && place.getS_W().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }
            // done
        } else if (place.getTop().getPosition().getY() == 3 && place.getTop().getPosition().getX() == 1) {
            // check top left right dow
            return top_down_left_right(place, place.getCurrentTaw().getPlayer().getId());

            //done
        } else if (place.getTop().getPosition().getY() == 3 && place.getTop().getPosition().getX() == 5) {
            // check top left right down
            return top_down_left_right(place, place.getCurrentTaw().getPlayer().getId());
            //done

        } else if (place.getTop().getPosition().getY() == 5 && place.getTop().getPosition().getX() == 1) {
            // check top left  n-e s-w
            if (place.getTop().getCurrentTaw() != null && place.getTop().getCurrentTaw().getPlayer().getId() == playerId) {
                if (place.getTop().getTop().getCurrentTaw() != null && place.getTop().getTop().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }

            if (place.getLeft().getCurrentTaw() != null &&place.getLeft().getCurrentTaw().getPlayer().getId() ==playerId ) {
                if (place.getLeft().getLeft().getCurrentTaw() != null && place.getLeft().getLeft().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }
            if (place.getN_E().getCurrentTaw() != null &&place.getN_E().getCurrentTaw().getPlayer().getId() ==playerId ) {
                if (place.getS_W().getCurrentTaw() != null && place.getS_W().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }
            //done
        } else if (place.getTop().getPosition().getY() == 5 && place.getTop().getPosition().getX() == 3) {
            // check top left right down
           return top_down_left_right(place, place.getCurrentTaw().getPlayer().getId());
           //done

        } else if (place.getTop().getPosition().getY() == 5 && place.getTop().getPosition().getX() == 5) {
            // check top  right n-w s-e
            if (place.getTop().getCurrentTaw() != null && place.getTop().getCurrentTaw().getPlayer().getId() == playerId) {
                if (place.getTop().getTop().getCurrentTaw() != null && place.getTop().getTop().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }

            if (place.getRight().getCurrentTaw() != null &&place.getRight().getCurrentTaw().getPlayer().getId() ==playerId ) {
                if (place.getRight().getRight().getCurrentTaw() != null && place.getRight().getRight().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }
            if (place.getN_W().getCurrentTaw() != null &&place.getN_W().getCurrentTaw().getPlayer().getId() ==playerId ) {
                if (place.getS_E().getCurrentTaw() != null && place.getS_E().getCurrentTaw().getPlayer().getId() == playerId)
                    return true;
            }
            //done
        }

        return false;
    }

    public boolean down_left_right(TawPlace place, int playerId) {
        if (place.getDown().getCurrentTaw() != null && place.getDown().getCurrentTaw().getPlayer().getId() == playerId) {
            //check down
            if (place.getDown().getDown().getCurrentTaw() != null && place.getDown().getDown().getCurrentTaw().getPlayer().getId() == playerId)
                return true;
        }
        if (place.getLeft().getCurrentTaw() != null && place.getLeft().getCurrentTaw().getPlayer().getId() == playerId) {
            // check right
            if (place.getRight().getCurrentTaw() != null && place.getRight().getCurrentTaw().getPlayer().getId() == playerId)
                return true;
        }
        return false;
    }

    public boolean top_down_left_right(TawPlace place, int playerId) {
        if (place.getDown().getCurrentTaw() != null && place.getDown().getCurrentTaw().getPlayer().getId() == playerId) {
            // check top
            if (place.getTop().getCurrentTaw() != null && place.getTop().getCurrentTaw().getPlayer().getId() == playerId)
                return true;
        }
        if (place.getLeft().getCurrentTaw() != null && place.getLeft().getCurrentTaw().getPlayer().getId() == playerId) {
            // check right
            if (place.getRight().getCurrentTaw() != null && place.getRight().getCurrentTaw().getPlayer().getId() == playerId)
                return true;
        }

        return false;
    }


    public boolean top_left_right(TawPlace place, int playerId) {
        if (place.getTop().getCurrentTaw() != null && place.getTop().getCurrentTaw().getPlayer().getId() == playerId) {
            //check down
            if (place.getTop().getTop().getCurrentTaw() != null && place.getTop().getTop().getCurrentTaw().getPlayer().getId() == playerId)
                return true;
        }
        if (place.getLeft().getCurrentTaw() != null && place.getLeft().getCurrentTaw().getPlayer().getId() == playerId) {
            //check right
            if (place.getRight().getCurrentTaw() != null && place.getRight().getCurrentTaw().getPlayer().getId() == playerId)
                return true;
        }


        return false;
    }

    public boolean top_down_right(TawPlace place, int playerId) {
        if (place.getRight().getCurrentTaw() != null && place.getRight().getCurrentTaw().getPlayer().getId() == playerId) {
            //check right
            if (place.getRight().getRight().getCurrentTaw() != null && place.getRight().getRight().getCurrentTaw().getPlayer().getId() == playerId)
                return true;
        }
        if (place.getTop().getCurrentTaw() != null && place.getTop().getCurrentTaw().getPlayer().getId() == playerId) {
            //check down
            if (place.getDown().getCurrentTaw() != null && place.getDown().getCurrentTaw().getPlayer().getId() == playerId)
                return true;
        }

        return false;
    }

    public boolean top_down_left(TawPlace place, int playerId) {
        if (place.getLeft().getCurrentTaw() != null && place.getLeft().getCurrentTaw().getPlayer().getId() == playerId) {
            //check left
            if (place.getLeft().getLeft().getCurrentTaw() != null && place.getLeft().getLeft().getCurrentTaw().getPlayer().getId() == playerId)
                return true;
        }
        if (place.getTop().getCurrentTaw() != null && place.getTop().getCurrentTaw().getPlayer().getId() == playerId) {
            //check down
            if (place.getDown().getCurrentTaw() != null && place.getDown().getCurrentTaw().getPlayer().getId() == playerId)
                return true;
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