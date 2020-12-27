package com.example.a12dash;

public enum GameState {
    ENTER_TAW(0),
    MOVE_TAW(1),
    DELETE_TAW(2);

    public int value;
    private GameState (int value){
        this.value=value;
    }
}
