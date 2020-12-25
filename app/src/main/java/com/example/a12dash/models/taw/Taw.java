package com.example.a12dash.models.taw;

import com.example.a12dash.models.player.Player;

import lombok.Data;

@Data
public class Taw {
    private int id;
    private TawPlace place;
    private TawCondition condition = TawCondition.OUT_GAME;
    private Player player;
}
