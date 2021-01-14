    package com.example.a12dash.service;

import android.graphics.Color;
import android.view.View;

import com.example.a12dash.Game_Page_Activity;
import com.example.a12dash.models.taw.Position;
import com.example.a12dash.models.taw.TawPlace;

public class Move {
    public boolean move(View view,TawPlace source, TawPlace target , boolean flag) {
        //change the taw from source to target place
        int color;
        if (flag) {
            color = Game_Page_Activity.firstPlayerColor;
            Game_Page_Activity.playerFirst.getTawList().forEach(taw -> {
                if (taw.getPlace().getX() == source.getPosition().getX() && taw.getPlace().getY() == source.getPosition().getY()) {
                    Game_Page_Activity.playerFirst.getTawList().get(Game_Page_Activity.playerFirst.getTawList().indexOf(taw)).setPlace(target.getPosition());
                }
            });
        } else {
            color = Game_Page_Activity.secondPlayerColor;
            Game_Page_Activity.playerSecond.getTawList().forEach(taw -> {
                if (taw.getPlace().getX() == source.getPosition().getX() && taw.getPlace().getY() == source.getPosition().getY()) {
                    Game_Page_Activity.playerSecond.getTawList().get(Game_Page_Activity.playerSecond.getTawList().indexOf(taw)).setPlace(target.getPosition());

                }
            });
        }

        Game_Page_Activity.places[target.getPosition().getY()][target.getPosition().getX()].setCurrentTaw(source.getCurrentTaw());
        Game_Page_Activity.places[source.getPosition().getY()][source.getPosition().getX()].setCurrentTaw(null);

//        target.setCurrentTaw(source.getCurrentTaw());
//        source.setCurrentTaw(null);
        String nameTarget = "btn" + target.getPosition().getY() + "_" + target.getPosition().getX();
        int idTarget = Game_Page_Activity.ids.get(Game_Page_Activity.btnNames.indexOf(nameTarget));
        view.findViewById(idTarget).setBackgroundColor(color);
        view.findViewById(idTarget).setTag(color);

        String nameSource = "btn" + source.getPosition().getY() + "_" + source.getPosition().getX();
        int idSource = Game_Page_Activity.ids.get(Game_Page_Activity.btnNames.indexOf(nameSource));
        view.findViewById(idSource).setBackgroundColor(Color.parseColor("#ADAD85"));
        view.findViewById(idSource).setTag("#ADAD85");
        return new EvaluateTheGoal(Game_Page_Activity.places).checkForGoal(Game_Page_Activity.places[target.getPosition().getY()][target.getPosition().getX()].getPosition());
    }


}
