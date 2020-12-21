package com.example.a12dash.models.taw;

import lombok.Data;

@Data
public class TawPlace {
    private TawPlaceCondition condition;
    private Position position;
    private TawPlace top;
    private TawPlace down;
    private TawPlace right;
    private TawPlace left;
    private TawPlace S_E;
    private TawPlace S_W;
    private TawPlace N_W;
    private TawPlace N_E;

}
