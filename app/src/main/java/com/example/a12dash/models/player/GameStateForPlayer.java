package com.example.a12dash.models.player;

public enum  GameStateForPlayer {
    ENTER_TAW(0),
    MOVE_TAW(1),
    DELETE_TAW(2);

    public int value;
    private GameStateForPlayer (int value){
        this.value=value;
    }
}
