package com.example.a12dash.models.player;

import com.example.a12dash.models.taw.Taw;

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
}
