package PoliciesAndAlgorithms;

public class ScoreTablePolicy2 extends ScoreTablePolicy {

    int pointsForWinner = 2;
    int pointForLoser = 0;
    int pointsForDraw = 0;



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
