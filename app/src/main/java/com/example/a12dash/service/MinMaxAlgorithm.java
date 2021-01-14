

package com.example.a12dash.service;

import android.view.View;

import com.example.a12dash.models.player.GameStateForPlayer;
import com.example.a12dash.models.taw.Position;
import com.example.a12dash.models.taw.Taw;
import com.example.a12dash.models.taw.TawPlace;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MinMaxAlgorithm {
    private final TawPlace[][] ground;


    public MinMaxAlgorithm(TawPlace[][] ground) {
        this.ground = ground;
    }



    public TawPlace getBestWay(TawPlace[][] ground , GameStateForPlayer state , View view) {
        int bestScore = -20;
        TawPlace bestPlace = new TawPlace();
        TawPlace source = new TawPlace();
        if (state.value == GameStateForPlayer.MOVE_TAW.value){
//            List<TawPlace> placeList = new ArrayList<>();
            for (int y = 0; y < 7; y++) {
                for (int x = 0; x < 7; x++) {
                    if (ground[y][x] != null && ground[y][x].getCurrentTaw() != null) {
                        if (ground[y][x].getCurrentTaw().getPlayerId()==1){
                            List<TawPlace>placeList = new NeighborPlaces().findNeighbors(ground[y][x]);
                            Taw taw = ground[y][x].getCurrentTaw();
                            ground[y][x].setCurrentTaw(null);
                            for (TawPlace place:placeList) {
                                int score =  evaluate(place,1);
                                if (bestScore<score){
                                    bestScore = score;
                                    bestPlace = place;
                                    ground[y][x].setCurrentTaw(taw);
                                    source = ground[y][x];
                                }
                            }
                            ground[y][x].setCurrentTaw(taw);
//                            placeList.add(ground[y][x]);
                        }
                    }
                }
            }
           boolean flag= new Move().move(view,source,bestPlace,true);
            if (flag){
                delete(view);
            }
        }
        if (state.value == GameStateForPlayer.ENTER_TAW.value){
            for (int y = 0; y < 7; y++) {
                for (int x = 0; x < 7; x++) {
                    if (ground[y][x] != null && ground[y][x].getCurrentTaw() == null) {
                        int score = evaluate( ground[y][x],1);
                        if (score > bestScore) {
                            bestScore = score;
                            bestPlace = ground[y][x];
                        }
                    }
                }
            }

        }




        return bestPlace;
    }
    private void delete (View view){
        List<TawPlace> placeList = new ArrayList<>();
        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 7; x++) {
                if (ground[y][x] != null && ground[y][x].getCurrentTaw() != null) {
                    if (ground[y][x].getCurrentTaw().getPlayerId()==2){
                        placeList.add(ground[y][x]);
                    }
                }
            }
        }
        int randomIndexForDelete = new Random().nextInt(placeList.size());
        TawPlace deletedPlace= placeList.get(randomIndexForDelete);
        new DeleteTaw().deleteTaw(view,1,deletedPlace.getPosition());
    }




    public TawPlace getTawByPosition(Position position) {

        return ground[position.getY()][position.getX()];
    }

    public int evaluate(TawPlace place, int playerId) {

        int score = 0;

        //place.getCurrentTaw().getPlayer();
        if (place.getPosition().getX() == 0 && place.getPosition().getY() == 0) {
            // check down right and s-e
            score += evaluateScoreTo0_0Position(place, playerId);
            //done
        } else if (place.getPosition().getX() == 3 && place.getPosition().getY() == 0) {
            //check down_left_right
            score += down_left_right(place, playerId);
            //done

        } else if (place.getPosition().getX() == 3 && place.getPosition().getY() == 4) {
            //check down_left_right

            score += down_left_right(place, playerId);
            //done

        } else if (place.getPosition().getX() == 6 && place.getPosition().getY() == 0) {
            // check down left and s-w
            score += evaluateScoreTo0_6Position(place, playerId);
            // done
        } else if (place.getPosition().getX() == 0 && place.getPosition().getY() == 6) {
            //check top right and n-e
            score += evaluateScoreTo6_0Position(place, playerId);
            //done

        } else if (place.getPosition().getX() == 3 && place.getPosition().getY() == 6) {
            // check top right and left
            score += top_left_right(place, playerId);
            //done

        } else if (place.getPosition().getX() == 3 && place.getPosition().getY() == 2) {
            // check top right and left
            score += top_left_right(place, playerId);
            //done

        } else if (place.getPosition().getX() == 6 && place.getPosition().getY() == 6) {
            // check top left and n-w
            score += evaluateScoreTo6_6Position(place, playerId);
            //done
        } else if (place.getPosition().getY() == 3 && place.getPosition().getX() == 6) {
            // check left top down
            score += top_down_left(place, playerId);
            // done

        } else if (place.getPosition().getY() == 3 && place.getPosition().getX() == 2) {
            // check left top down(Done)
            score += top_down_left(place, playerId);

            //done
        } else if (place.getPosition().getY() == 3 && place.getPosition().getX() == 0) {
            // check right top down(Done)
            score += top_down_right(place, playerId);

            //done
        } else if (place.getPosition().getY() == 3 && place.getPosition().getX() == 4) {
            // check right top down(Done)
            score += top_down_right(place,playerId);

            //done

        } else if (place.getPosition().getY() == 2 && place.getPosition().getX() == 2) {

            // check left down n-w
            score += evaluateScoreTo2_2Position(place, playerId);
            // done
        } else if (place.getPosition().getY() == 2 && place.getPosition().getX() == 4) {

            // check down left n-e
            score += evaluateScoreTo2_4Position(place, playerId);
            //done
        } else if (place.getPosition().getY() == 4 && place.getPosition().getX() == 2) {
            // check top Right s-w
            score += evaluateScoreTo4_2Position(place, playerId);

            //ended
        } else if (place.getPosition().getY() == 4 && place.getPosition().getX() == 4) {
            // check top left s-e
            score += evaluateScoreTo4_4Position(place, playerId);
            //ended
        } else if (place.getPosition().getY() == 1 && place.getPosition().getX() == 1) {
            // check down right n-W and s-e
            score += evaluateScoreTo1_1Position(place, playerId);
            // done
        } else if (place.getPosition().getY() == 1 && place.getPosition().getX() == 3) {
            // check top left right down
            score += top_down_left_right(place,playerId);

            //done
        } else if (place.getPosition().getY() == 1 && place.getPosition().getX() == 5) {
            // check down left s-w n_e
            score += evaluateScoreTo1_5Position(place, playerId);
            // done
        } else if (place.getPosition().getY() == 3 && place.getPosition().getX() == 1) {
            // check top left right dow
            score += top_down_left_right(place, playerId);

            //done
        } else if (place.getPosition().getY() == 3 && place.getPosition().getX() == 5) {
            // check top left right down
            score += top_down_left_right(place, playerId);
            //done

        } else if (place.getPosition().getY() == 5 && place.getPosition().getX() == 1) {
            // check top left  n-e s-w
            score += evaluateScoreTo5_1Position(place, playerId);
            //done
        } else if (place.getPosition().getY() == 5 && place.getPosition().getX() == 3) {
            // check top left right down
            score += top_down_left_right(place, playerId);
            //done

        } else if (place.getPosition().getY() == 5 && place.getPosition().getX() == 5) {
            // check top  right n-w s-e
            score += evaluateScoreTo5_5Position(place, playerId);
            //done
        }

        return score;
    }

    public int down_left_right(TawPlace place, int playerId) {
        int score = 0;
        if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() == playerId) {
            //check down
            if (getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() == playerId) {
            // check right
            if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() != playerId) {
            //check down
            if (getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }
        if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() != playerId) {
            // check right
            if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }
        return score;
    }

    public int top_down_left_right(TawPlace place, int playerId) {
        int score = 0;
        if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() == playerId) {
            // check top
            if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() == playerId) {
            // check right
            if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() != playerId) {
            // check top
            if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }
        if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() != playerId) {
            // check right
            if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }

        return score;
    }

    public int top_left_right(TawPlace place, int playerId) {
        int score = 0;
        if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() == playerId) {
            //check down
            if (getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() == playerId) {
            //check right
            if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() != playerId) {
            //check down
            if (getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }
        if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() != playerId) {
            //check right
            if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }


        return score;
    }

    public int top_down_right(TawPlace place, int playerId) {
        int score = 0;

        if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() == playerId) {
            //check right
            if (getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() == playerId) {
            //check down
            if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() == playerId)

                ++score;
        }
        if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() != playerId) {
            //check right
            if (getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }
        if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() != playerId) {
            //check down
            if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() != playerId)

                ++score;
        }

        return score;
    }

    public int top_down_left(TawPlace place, int playerId) {
        int score = 0;
        if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() == playerId) {
            //check left
            if (getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() == playerId) {
            //check down
            if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() != playerId) {
            //check left
            if (getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }
        if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() != playerId) {
            //check down
            if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }

        return score;
    }

    private int evaluateScoreTo0_0Position(TawPlace place, int playerId) {
        int score = 0;
        boolean flag;
        if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw().getPlayerId() == playerId) {
                ++score;
                flag = true;
            }

        } else if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw().getPlayerId() == playerId) {
                flag = true;
                ++score;
            }
        } else if (getTawByPosition(place.getS_E()).getCurrentTaw() != null && getTawByPosition(place.getS_E()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getS_E()).getS_E()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getS_E()).getS_E()).getCurrentTaw().getPlayerId() == playerId) {
                flag = true;
                ++score;
            }
        }
        if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw().getPlayerId() != playerId) {
                flag = true;

                ++score;

            }

        } else if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw().getPlayerId() != playerId) {
                ++score;
                flag = true;
            }

        } else if (getTawByPosition(place.getS_E()).getCurrentTaw() != null && getTawByPosition(place.getS_E()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getS_E()).getS_E()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getS_E()).getS_E()).getCurrentTaw().getPlayerId() != playerId) {
                ++score;
                flag = true;
            }
        }
        //todo create twins
        return score;
    }

    private int evaluateScoreTo0_6Position(TawPlace place, int playerId) {
        int score = 0;
        if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }

        if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getS_W()).getCurrentTaw() != null && getTawByPosition(place.getS_W()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getS_W()).getS_W()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getS_W()).getS_W()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }

        if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }
        if (getTawByPosition(place.getS_W()).getCurrentTaw() != null && getTawByPosition(place.getS_W()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getS_W()).getS_W()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getS_W()).getS_W()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }

        return score;
    }

    private int evaluateScoreTo6_0Position(TawPlace place, int playerId) {
        int score = 0;
        if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }

        if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getN_E()).getCurrentTaw() != null && getTawByPosition(place.getN_E()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getN_E()).getN_E()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getN_E()).getN_E()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }

        if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }
        if (getTawByPosition(place.getN_E()).getCurrentTaw() != null && getTawByPosition(place.getN_E()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getN_E()).getN_E()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getN_E()).getN_E()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }
        return score;
    }

    private int evaluateScoreTo6_6Position(TawPlace place, int playerId) {
        int score = 0;
        if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }

        if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getN_W()).getCurrentTaw() != null && getTawByPosition(place.getN_W()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getN_W()).getN_W()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getN_W()).getN_W()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }

        if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }
        if (getTawByPosition(place.getN_W()).getCurrentTaw() != null && getTawByPosition(place.getN_W()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getN_W()).getN_W()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getN_W()).getN_W()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }
        return score;
    }

    private int evaluateScoreTo2_2Position(TawPlace place, int playerId) {
        int score = 0;
        if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        } else if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        } else if (getTawByPosition(place.getN_W()).getCurrentTaw() != null && getTawByPosition(place.getN_W()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getN_W()).getN_W()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getN_W()).getN_W()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        } else if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        } else if (getTawByPosition(place.getN_W()).getCurrentTaw() != null && getTawByPosition(place.getN_W()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getN_W()).getN_W()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getN_W()).getN_W()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }
        return score;
    }

    private int evaluateScoreTo2_4Position(TawPlace place, int playerId) {
        int score = 0;

        if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }

        if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getN_E()).getCurrentTaw() != null && getTawByPosition(place.getN_E()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getN_E()).getN_E()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getN_E()).getN_E()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }

        if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }
        if (getTawByPosition(place.getN_E()).getCurrentTaw() != null && getTawByPosition(place.getN_E()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getN_E()).getN_E()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getN_E()).getN_E()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }
        return score;
    }

    private int evaluateScoreTo4_2Position(TawPlace place, int playerId) {
        int score = 0;
        if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        } else if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw().getPlayerId() == playerId)
                ++score;

        } else if (getTawByPosition(place.getS_W()).getCurrentTaw() != null && getTawByPosition(place.getS_W()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getS_W()).getS_W()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getS_W()).getS_W()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        } else if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw().getPlayerId() != playerId)
                ++score;

        } else if (getTawByPosition(place.getS_W()).getCurrentTaw() != null && getTawByPosition(place.getS_W()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getS_W()).getS_W()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getS_W()).getS_W()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }
        return score;
    }

    private int evaluateScoreTo4_4Position(TawPlace place, int playerId) {
        int score = 0;
        if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        } else if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        } else if (getTawByPosition(place.getS_E()).getCurrentTaw() != null && getTawByPosition(place.getS_E()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getS_E()).getS_E()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getS_E()).getS_E()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        } else if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        } else if (getTawByPosition(place.getS_E()).getCurrentTaw() != null && getTawByPosition(place.getS_E()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getS_E()).getS_E()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getS_E()).getS_E()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }


        return score;
    }

    private int evaluateScoreTo1_1Position(TawPlace place, int playerId) {
        int score = 0;
        if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }

        if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getS_E()).getCurrentTaw() != null && getTawByPosition(place.getS_E()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(place.getN_W()).getCurrentTaw() != null && getTawByPosition(place.getN_W()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }

        if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }
        if (getTawByPosition(place.getS_E()).getCurrentTaw() != null && getTawByPosition(place.getS_E()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(place.getN_W()).getCurrentTaw() != null && getTawByPosition(place.getN_W()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }
        return score;
    }

    private int evaluateScoreTo1_5Position(TawPlace place, int playerId) {
        int score = 0;
        if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        } else if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        } else if (getTawByPosition(place.getN_E()).getCurrentTaw() != null && getTawByPosition(place.getN_E()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(place.getS_W()).getCurrentTaw() != null && getTawByPosition(place.getS_W()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getDown()).getCurrentTaw() != null && getTawByPosition(place.getDown()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getDown()).getDown()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        } else if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        } else if (getTawByPosition(place.getN_E()).getCurrentTaw() != null && getTawByPosition(place.getN_E()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(place.getS_W()).getCurrentTaw() != null && getTawByPosition(place.getS_W()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }
        return score;
    }

    private int evaluateScoreTo5_5Position(TawPlace place, int playerId) {
        int score = 0;
        if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        } else if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        } else if (getTawByPosition(place.getN_W()).getCurrentTaw() != null && getTawByPosition(place.getN_W()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(place.getS_E()).getCurrentTaw() != null && getTawByPosition(place.getS_E()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }
        if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        } else if (getTawByPosition(place.getLeft()).getCurrentTaw() != null && getTawByPosition(place.getLeft()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getLeft()).getLeft()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        } else if (getTawByPosition(place.getN_W()).getCurrentTaw() != null && getTawByPosition(place.getN_W()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(place.getS_E()).getCurrentTaw() != null && getTawByPosition(place.getS_E()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }
        return score;
    }

    private int evaluateScoreTo5_1Position(TawPlace place, int playerId) {
        int score = 0;
        if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        } else if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        } else if (getTawByPosition(place.getN_E()).getCurrentTaw() != null && getTawByPosition(place.getN_E()).getCurrentTaw().getPlayerId() == playerId) {
            if (getTawByPosition(place.getS_W()).getCurrentTaw() != null && getTawByPosition(place.getS_W()).getCurrentTaw().getPlayerId() == playerId)
                ++score;
        }  if (getTawByPosition(place.getTop()).getCurrentTaw() != null && getTawByPosition(place.getTop()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getTop()).getTop()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        } else if (getTawByPosition(place.getRight()).getCurrentTaw() != null && getTawByPosition(place.getRight()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw() != null && getTawByPosition(getTawByPosition(place.getRight()).getRight()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        } else if (getTawByPosition(place.getN_E()).getCurrentTaw() != null && getTawByPosition(place.getN_E()).getCurrentTaw().getPlayerId() != playerId) {
            if (getTawByPosition(place.getS_W()).getCurrentTaw() != null && getTawByPosition(place.getS_W()).getCurrentTaw().getPlayerId() != playerId)
                ++score;
        }
        return score;
    }
}

//
//enum Utility {
//    GOAL(1),
//    DEFENSE(1),
//    TWINS_1(0.2),
//    TWINS_2(0.5),
//    TWINS_3(0.7),
//    NONE(0);
//
//    public double value;
//
//    private Utility(double value) {
//        this.value = value;
//    }
//}