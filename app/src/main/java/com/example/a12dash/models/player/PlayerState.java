package com.example.a12dash.models.player;

public enum PlayerState {
    MY_TURN(0),
    RIVAL_TURN(1),
    ENTER_TAW(2),
    DELETE_RIVAL_TAW(3);
    public int value;
    private PlayerState (int value){
        this.value=value;
    }

}
