package System;

import Users.*;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {

    @Test
    void exportReport() {
    }

    @Test
    void addActionToLogger() {
        Coach coach = new Coach("newCoach", "yossi","cohen",ETraining.CDiploma, ECoachRole.AssistantCoach);
        assertEquals(Logger.getInstance().getActionLog().size(),1);
        Player player = new Player("newPlayer", "yossi","cohen", new Date(),EPlayerRole.GoalKeeper);
        assertEquals(Logger.getInstance().getActionLog().size(),2);
        Referee main = new Referee("1", "main", "main",EReferee.MAIN);
        assertEquals(Logger.getInstance().getActionLog().size(),3);

    }
}