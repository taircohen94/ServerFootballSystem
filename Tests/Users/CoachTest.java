package Users;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CoachTest {

    @Test
    void addFeedToMyPage() {
        //Creating coach
        Coach coach = new Coach("newCoach", "yossi","cohen",ETraining.CDiploma, ECoachRole.AssistantCoach);
        String feed =  "this is my first post";
        coach.addFeedToMyPage(feed);
        assertTrue(coach.getMyPage().getMyFeed().size() == 1 && coach.getMyPage().getMyFeed().get(0).equals(feed));
        coach.addFeedToMyPage(null);
        assertTrue(coach.getMyPage().getMyFeed().size() == 1 && coach.getMyPage().getMyFeed().get(0).equals(feed));

        //Creating coach with Fan
        Coach coachFan = new Coach(new Fan("newCoach", "yossi","cohen"),ETraining.CDiploma, ECoachRole.AssistantCoach);
        String feedFan =  "this is my first post";
        coachFan.addFeedToMyPage(feedFan);
        assertTrue(coachFan.getMyPage().getMyFeed().size() == 1 && coachFan.getMyPage().getMyFeed().get(0).equals(feedFan));
        coachFan.addFeedToMyPage(null);
        assertTrue(coachFan.getMyPage().getMyFeed().size() == 1 && coachFan.getMyPage().getMyFeed().get(0).equals(feedFan));


        //Creating coach with CanBeOwner
        Coach coachCanBeOwner = new Coach(new CanBeOwner("newCoach", "yossi","cohen"),ETraining.CDiploma, ECoachRole.AssistantCoach);
        String feedCanBeOwner =  "this is my first post";
        coachCanBeOwner.addFeedToMyPage(feedCanBeOwner);
        assertTrue(coachCanBeOwner.getMyPage().getMyFeed().size() == 1 && coachCanBeOwner.getMyPage().getMyFeed().get(0).equals(feedCanBeOwner));
        coachCanBeOwner.addFeedToMyPage(null);
        assertTrue(coachCanBeOwner.getMyPage().getMyFeed().size() == 1 && coachCanBeOwner.getMyPage().getMyFeed().get(0).equals(feedCanBeOwner));

        //Check Observers
        coach.getMyPage().register(new Player("newPlayer", "yossi","cohen", new Date(),EPlayerRole.GoalKeeper));
        coach.addFeedToMyPage("new Feed");

    }

    @Test
    void removeFeedFromMyPage() {
        Coach coach = new Coach("newCoach", "yossi","cohen",ETraining.CDiploma, ECoachRole.AssistantCoach);
        String feed = "this is my first post";
        coach.addFeedToMyPage(feed);
        coach.removeFeedFromMyPage(feed);
        assertEquals(0, coach.getMyPage().getMyFeed().size());
        // null case
        coach.getMyPage().removeFeedFromMyPage(null);
        assertEquals(0, coach.getMyPage().getMyFeed().size());
        // null case
        coach.removeFeedFromMyPage(null);
        assertEquals(0, coach.getMyPage().getMyFeed().size());
    }

    @Test
    void setRole() {
        Coach coach = new Coach("newCoach", "yossi","cohen",ETraining.CDiploma, ECoachRole.AssistantCoach);
        assertEquals(coach.getRole(), ECoachRole.AssistantCoach);
        coach.setRole(ECoachRole.GoalkeeperCoach);
        assertEquals(coach.getRole(), ECoachRole.GoalkeeperCoach);
        assertEquals(coach.getMyPage().getRole(), ECoachRole.GoalkeeperCoach);
        coach.setRole(null);
        assertEquals(coach.getRole(), ECoachRole.GoalkeeperCoach);
        assertEquals(coach.getMyPage().getRole(), ECoachRole.GoalkeeperCoach);

    }

    @Test
    void setTraining() {
        Coach coach = new Coach("newCoach", "yossi","cohen",ETraining.CDiploma, ECoachRole.AssistantCoach);
        assertEquals(coach.getTraining(), ETraining.CDiploma);
        coach.setTraining(ETraining.UEFAA);
        assertEquals(coach.getTraining(), ETraining.UEFAA);
        assertEquals(((CoachPageEditor)coach.getMyPage()).getTraining(), ETraining.UEFAA);

        coach.setTraining(null);
        assertEquals(coach.getTraining(), ETraining.UEFAA);
        assertEquals(((CoachPageEditor)coach.getMyPage()).getTraining(), ETraining.UEFAA);
    }


    @Test
    void setMyPage() {
        Coach coach = new Coach("newCoach", "yossi","cohen",ETraining.CDiploma, ECoachRole.AssistantCoach);
        String feed = "this is my first post";
        coach.addFeedToMyPage(feed);
        CoachPageEditor newPage = new CoachPageEditor("yossi","cohen",ECoachRole.AssistantCoach,ETraining.CDiploma);
        coach.setMyPage(newPage);
        assertEquals(0, coach.getMyPage().getMyFeed().size());
        coach.setMyPage(null);
        assertEquals(0, coach.getMyPage().getMyFeed().size());

    }

    @Test
    void logout() {
        Coach coach = new Coach("newCoach", "yossi","cohen",ETraining.CDiploma, ECoachRole.AssistantCoach);
        assertEquals(coach.getStatus(), EStatus.ONLINE);
        coach.logout();
        assertEquals(coach.getStatus(), EStatus.OFFLINE);
    }


    @Test
    void setfName() {
        Coach coach = new Coach("newCoach", "yossi","cohen",ETraining.CDiploma, ECoachRole.AssistantCoach);
        assertEquals(coach.getfName(),"yossi");
        assertEquals(coach.getMyPage().getMyFirstName(),"yossi");
        coach.setfName("yosi");
        assertEquals(coach.getfName(), "yosi");
        assertEquals(coach.getMyPage().getMyFirstName(),"yosi");
        coach.setfName(null);
        assertEquals(coach.getfName(), "yosi");
        assertEquals(coach.getMyPage().getMyFirstName(),"yosi");


    }

    @Test
    void setlName() {
        Coach coach = new Coach("newCoach", "yossi","cohen",ETraining.CDiploma, ECoachRole.AssistantCoach);
        assertEquals(coach.getlName(),"cohen");
        assertEquals(coach.getMyPage().getMyLastName(), "cohen");
        coach.setlName("levi");
        assertEquals(coach.getlName(), "levi");
        assertEquals(coach.getMyPage().getMyLastName(), "levi");
        coach.setlName(null);
        assertEquals(coach.getlName(), "levi");
        assertEquals(coach.getMyPage().getMyLastName(), "levi");

    }

    @Test
    void setStatus() {
        Coach coach = new Coach("newCoach", "yossi","cohen",ETraining.CDiploma, ECoachRole.AssistantCoach);
        assertEquals(coach.getStatus(), EStatus.ONLINE);
        coach.setStatus(EStatus.OFFLINE);
        assertEquals(coach.getStatus(), EStatus.OFFLINE);
    }
}
