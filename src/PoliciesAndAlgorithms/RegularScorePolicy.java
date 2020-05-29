package PoliciesAndAlgorithms;

import jdk.nashorn.internal.parser.JSONParser;
//import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

public class RegularScorePolicy extends ScoreTablePolicy {

    int pointsForWinner = 3;
    int pointForLoser = 0;
    int pointsForDraw = 1;



    @Override
    public int getPolicyWinnerPoints() {
        return pointsForWinner;
    }

    @Override
    public int getPolicyLoserPoints() {
        return pointForLoser;
    }

    @Override
    public int getPolicyDrawPoints() {
        return pointsForDraw;
    }
}






