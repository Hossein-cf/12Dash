package com.example.a12dash.models.taw;

import lombok.Data;

@Data
public class TawPlace {
    private Taw currentTaw ;
    private TawPlaceCondition condition;
    private Position position;
    private Position top;
    private Position down;
    private Position right;
    private Position left;
    private Position S_E;
    private Position S_W;
    private Position N_W;
    private Position N_E;
    private double utilityForSystem;
    private double utilityForPlayer;
}
