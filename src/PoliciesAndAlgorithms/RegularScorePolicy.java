package PoliciesAndAlgorithms;

//import org.json.JSONObject;


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






