package com.example.a12dash.service;

import android.graphics.Color;
import android.view.View;

import com.example.a12dash.Game_Page_Activity;
import com.example.a12dash.models.player.Player;
import com.example.a12dash.models.taw.Position;

public class DeleteTaw {
    public void deleteTaw(View view, int playerId, Position position) {
        String placeName = "btn" + position.getY() + "_" + position.getX();
        view.findViewById(Game_Page_Activity.ids.get(Game_Page_Activity.btnNames.indexOf(placeName))).setBackgroundColor(Color.parseColor("#ADAD85"));
        view.findViewById(Game_Page_Activity.ids.get(Game_Page_Activity.btnNames.indexOf(placeName))).setTag("#ADAD85");
        view.findViewById(Game_Page_Activity.ids.get(Game_Page_Activity.btnNames.indexOf(placeName))).setEnabled(false);
        if (playerId == 1) {
            Game_Page_Activity.playerFirst.getTawList().removeIf(taw1 ->
                    taw1.getPlace().getY() == position.getY() && taw1.getPlace().getX() == position.getX()
            );
        } else {
            Game_Page_Activity.playerSecond.getTawList().removeIf(taw1 ->
                    taw1.getPlace().getY() == position.getY() && taw1.getPlace().getX() == position.getX()
            );

        }
        Game_Page_Activity.places[position.getY()][position.getX()].setCurrentTaw(null);

    }
}
