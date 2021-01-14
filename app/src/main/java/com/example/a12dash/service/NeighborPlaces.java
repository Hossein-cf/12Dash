package com.example.a12dash.service;

import com.example.a12dash.Game_Page_Activity;
import com.example.a12dash.models.taw.Position;
import com.example.a12dash.models.taw.TawPlace;

import java.util.ArrayList;
import java.util.List;

public class NeighborPlaces {
    public List<TawPlace> findNeighbors(TawPlace tawPlace) {
        List<TawPlace> placeList = new ArrayList<>();
        if (tawPlace.getLeft() != null) {
            if (getTawByPosition(tawPlace.getLeft()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlace.getLeft()));
        }
        if (tawPlace.getRight() != null) {
            if (getTawByPosition(tawPlace.getRight()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlace.getRight()));
        }
        if (tawPlace.getDown() != null) {
            if (getTawByPosition(tawPlace.getDown()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlace.getDown()));
        }
        if (tawPlace.getTop() != null) {
            if (getTawByPosition(tawPlace.getTop()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlace.getTop()));
        }
        if (tawPlace.getS_E() != null) {
            if (getTawByPosition(tawPlace.getS_E()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlace.getS_E()));
        }
        if (tawPlace.getS_W() != null) {
            if (getTawByPosition(tawPlace.getS_W()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlace.getS_W()));
        }
        if (tawPlace.getN_E() != null) {
            if (getTawByPosition(tawPlace.getN_E()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlace.getN_E()));
        }
        if (tawPlace.getN_W() != null) {
            if (getTawByPosition(tawPlace.getN_W()).getCurrentTaw() == null)
                placeList.add(getTawByPosition(tawPlace.getN_W()));
        }
        return placeList;
    }
    public TawPlace getTawByPosition(Position position) {

        return Game_Page_Activity.places[position.getY()][position.getX()];
    }
}
