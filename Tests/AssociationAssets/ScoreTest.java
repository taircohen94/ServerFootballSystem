package AssociationAssets;

import AssociationAssets.Score;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ScoreTest {

    Score score;

    @Before
    public void setUp() throws Exception {

        score = new Score();

    }

    @Test
    public void setGoalsHost() {

        score.setGoalsHost(1);
        assertEquals(score.getGoalsHost(),1);

    }



    @Test
    public void toStringTest() {
        score.setGoalsHost(3);
        score.setGoalsGuest(2);
        assertEquals(score.toString(),"Score= {Host=3, Guest=2}");

    }
}