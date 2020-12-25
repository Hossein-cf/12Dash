package com.example.a12dash.models.taw;

import lombok.Data;

@Data
public class PlayGround {

     private TawPlace[][] tawPlaces = new TawPlace[7][7];

    // place+row+_+clm
    // row = Y
    // clm = X
    // example placeY_X
    //قطر اصلی
    TawPlace place0_0 = new TawPlace();
    TawPlace place1_1 = new TawPlace();
    TawPlace place2_2 = new TawPlace();
    TawPlace place4_4 = new TawPlace();
    TawPlace place5_5 = new TawPlace();
    TawPlace place6_6 = new TawPlace();

    //قطر فرعی
    TawPlace place6_0 = new TawPlace();
    TawPlace place5_1 = new TawPlace();
    TawPlace place4_2 = new TawPlace();
    TawPlace place2_4 = new TawPlace();
    TawPlace place1_5 = new TawPlace();
    TawPlace place0_6 = new TawPlace();

    // افقی
    TawPlace place3_0 = new TawPlace();
    TawPlace place3_1 = new TawPlace();
    TawPlace place3_2 = new TawPlace();
    TawPlace place3_4 = new TawPlace();
    TawPlace place3_5 = new TawPlace();
    TawPlace place3_6 = new TawPlace();

    //عمودی
    TawPlace place0_3 = new TawPlace();
    TawPlace place1_3 = new TawPlace();
    TawPlace place2_3 = new TawPlace();
    TawPlace place4_3 = new TawPlace();
    TawPlace place5_3 = new TawPlace();
    TawPlace place6_3 = new TawPlace();

    public PlayGround() {

        setPosition();

        place0_0.setDown(place3_0);
        place0_0.setRight(place0_3);
        place0_0.setS_E(place1_1);
        tawPlaces[0][0] = place0_0;


        place0_3.setLeft(place0_0);
        place0_3.setRight(place0_6);
        place0_3.setDown(place1_3);
        tawPlaces[0][3] = place0_3;


        place0_6.setLeft(place0_3);
        place0_6.setDown(place3_6);
        place0_6.setS_W(place1_5);
        tawPlaces[0][6] = place0_6;

        place1_1.setN_W(place0_0);
        place1_1.setS_E(place2_2);
        place1_1.setDown(place3_1);
        place1_1.setRight(place1_3);
        tawPlaces[1][1] = place1_1;

        place1_3.setTop(place0_3);
        place1_3.setDown(place2_3);
        place1_3.setRight(place1_5);
        place1_3.setLeft(place1_1);
        tawPlaces[1][3] = place1_3;

        place1_5.setLeft(place1_3);
        place1_5.setDown(place3_5);
        place1_5.setN_E(place0_6);
        place1_5.setS_W(place2_4);
        tawPlaces[1][5] = place1_5;

        place2_2.setDown(place3_2);
        place2_2.setRight(place2_3);
        place2_2.setN_W(place1_1);
        tawPlaces[2][2] = place2_2;

        place2_3.setLeft(place2_2);
        place2_3.setRight(place2_4);
        place2_3.setTop(place1_3);
        tawPlaces[2][3] = place2_3;

        place2_4.setLeft(place2_3);
        place2_4.setDown(place3_4);
        place2_4.setN_E(place1_5);
        tawPlaces[2][4] = place2_4;


        place3_0.setTop(place0_0);
        place3_0.setDown(place6_0);
        place3_0.setRight(place3_1);
        tawPlaces[3][0] = place3_0;

        place3_1.setLeft(place3_0);
        place3_1.setRight(place3_2);
        place3_1.setTop(place1_1);
        place3_1.setDown(place5_1);
        tawPlaces[3][1] = place3_1;


        place3_2.setLeft(place3_1);
        place3_2.setTop(place2_2);
        place3_2.setDown(place4_2);
        tawPlaces[3][2] = place3_2;

        place3_4.setTop(place2_4);
        place3_4.setDown(place4_4);
        place3_4.setRight(place3_5);
        tawPlaces[3][4] = place3_4;

        place3_5.setLeft(place3_4);
        place3_5.setRight(place3_6);
        place3_5.setTop(place1_5);
        place3_5.setDown(place5_5);
        tawPlaces[3][5] = place3_5;

        place3_6.setLeft(place3_5);
        place3_6.setTop(place0_6);
        place3_6.setDown(place6_6);
        tawPlaces[3][6] = place3_6;


        place4_2.setTop(place3_2);
        place4_2.setRight(place4_3);
        place4_2.setS_E(place5_1);
        tawPlaces[4][2] = place4_2;


        place4_3.setLeft(place4_2);
        place4_3.setDown(place5_3);
        place4_3.setRight(place4_4);
        tawPlaces[4][3] = place4_3;

        place4_4.setTop(place3_4);
        place4_4.setLeft(place4_3);
        place4_4.setS_E(place5_5);
        tawPlaces[4][4] = place4_4;

        place5_1.setTop(place3_1);
        place5_1.setRight(place5_3);
        place5_1.setN_E(place4_2);
        place5_1.setS_W(place6_0);
        tawPlaces[5][1] = place5_1;

        place5_3.setLeft(place5_1);
        place5_3.setRight(place5_5);
        place5_3.setTop(place4_3);
        place5_3.setDown(place6_3);
        tawPlaces[5][3] = place5_3;

        place5_5.setLeft(place5_3);
        place5_5.setTop(place3_5);
        place5_5.setS_E(place6_6);
        place5_5.setN_W(place4_4);
        tawPlaces[5][5] = place5_5;

        place6_0.setTop(place3_0);
        place6_0.setN_E(place5_1);
        place6_0.setRight(place6_3);
        tawPlaces[6][0] = place6_0;

        place6_3.setLeft(place6_0);
        place6_3.setTop(place5_3);
        place6_3.setRight(place6_6);
        tawPlaces[3][6] = place6_3;

        place6_6.setLeft(place6_3);
        place6_6.setTop(place3_6);
        place6_6.setN_W(place5_5);
        tawPlaces[6][6] = place6_6;

    }

    public void setPosition() {
        Position position = new Position();
        position.setY(0);
        position.setX(0);
        place0_0.setPosition(position);

        position = new Position();
        position.setY(0);
        position.setX(3);
        place0_3.setPosition(position);


        position = new Position();
        position.setY(0);
        position.setX(6);
        place0_6.setPosition(position);


        position = new Position();
        position.setY(1);
        position.setX(1);
        place1_1.setPosition(position);

        position = new Position();
        position.setY(1);
        position.setX(3);
        place1_3.setPosition(position);

        position = new Position();
        position.setY(1);
        position.setX(5);
        place1_5.setPosition(position);

        position = new Position();
        position.setY(2);
        position.setX(2);
        place2_2.setPosition(position);

        position = new Position();
        position.setY(2);
        position.setX(3);
        place2_3.setPosition(position);

        position = new Position();
        position.setY(2);
        position.setX(4);
        place2_4.setPosition(position);

        position = new Position();
        position.setY(3);
        position.setX(0);
        place3_0.setPosition(position);

        position = new Position();
        position.setY(3);
        position.setX(1);
        place3_1.setPosition(position);

        position = new Position();
        position.setY(3);
        position.setX(2);
        place3_2.setPosition(position);

        position = new Position();
        position.setY(3);
        position.setX(4);
        place3_4.setPosition(position);

        position = new Position();
        position.setY(3);
        position.setX(5);
        place3_5.setPosition(position);

        position = new Position();
        position.setY(3);
        position.setX(6);
        place3_6.setPosition(position);

        position = new Position();
        position.setY(4);
        position.setX(2);
        place4_2.setPosition(position);

        position = new Position();
        position.setY(4);
        position.setX(3);
        place4_3.setPosition(position);

        position = new Position();
        position.setY(4);
        position.setX(4);
        place4_4.setPosition(position);

        position = new Position();
        position.setY(5);
        position.setX(1);
        place5_1.setPosition(position);

        position = new Position();
        position.setY(5);
        position.setX(3);
        place5_3.setPosition(position);

        position = new Position();
        position.setY(5);
        position.setX(5);
        place5_5.setPosition(position);

        position = new Position();
        position.setY(6);
        position.setX(0);
        place6_0.setPosition(position);

        position = new Position();
        position.setY(6);
        position.setX(3);
        place6_3.setPosition(position);

        position = new Position();
        position.setY(6);
        position.setX(6);
        place6_6.setPosition(position);

    }


}
