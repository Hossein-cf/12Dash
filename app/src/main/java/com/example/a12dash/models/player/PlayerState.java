package com.example.a12dash.models.player;

public enum PlayerState {
    MY_TURN(0),
    WAITING(1),

    DELETE_RIVAL_TAW(2);
    public int value;
    private PlayerState (int value){
        this.value=value;
    }

}
