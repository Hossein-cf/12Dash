package com.example.a12dash.models.taw;

public enum TawCondition {
    DELETED(0),
    IN_GAME(1),
    OUT_GAME(2);
    public int value;
    private TawCondition(int value){
        this.value=value;
    }
}
