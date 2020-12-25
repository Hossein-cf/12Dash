package com.example.a12dash.models.player;

public enum PlayerState {
    MY_TURN(0),
    RIVAL_TURN(1),

    DELETE_RIVAL_TAW(2);
    public int value;
    private PlayerState (int value){
        this.value=value;
    }

}
