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
    private List<Taw> tawList=new ArrayList<>();
    private double score;
    private int numberOfTawInGame=0;
    private int numberOfTawInHand =12;
    private int numberOfTawDeleted=0;
    public Player(int id){
         this.id = id;

        for (int i = 0; i < 12; i++) {
            Taw taw = new Taw();
            taw.setPlayerId(id);
            taw.setCondition(TawCondition.OUT_GAME);
            tawList.add(taw);
        }
    }

}
