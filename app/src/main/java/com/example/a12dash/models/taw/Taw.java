package com.example.a12dash.models.taw;

import com.example.a12dash.models.player.Player;

import lombok.Data;

@Data
public class Taw {
    private int id;
    private Position place;
    private TawCondition condition = TawCondition.OUT_GAME;
    private int playerId;
}
