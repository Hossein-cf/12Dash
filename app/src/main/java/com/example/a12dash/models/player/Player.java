package com.example.a12dash.models.player;

import com.example.a12dash.models.taw.Taw;
import com.example.a12dash.models.taw.TawCondition;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Player {
    private int id;
    private String name;
    private PlayerType type;
    private List<Taw> tawList=new ArrayList<>(12);
    private double score;

    public Player(){
        for (int i = 0; i < 12; i++) {
            Taw taw = new Taw();
            taw.setPlayer(this);
            taw.setCondition(TawCondition.OUT_GAME);
            tawList.add(taw);
        }
    }

}
