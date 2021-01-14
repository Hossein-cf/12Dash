package com.example.a12dash.service;


import com.example.a12dash.models.taw.Position;
import com.example.a12dash.models.taw.TawPlace;

public class EvaluateTheGoal {
    private TawPlace[][] places;

    public EvaluateTheGoal(TawPlace[][] places) {
        this.places = places;
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
    public TawPlace getTawByPosition(Position position) {

        return places[position.getY()][position.getX()];
    }
}
