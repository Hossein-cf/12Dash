package com.example.a12dash.models.player;

public enum PlayerType {
    SYSTEM(0),
    PERSON(1);
    public int value;

    private PlayerType(int value) {
        this.value = value;
    }
}
