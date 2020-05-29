package Users;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
class PlayerTest {
    
    @Test
    void addFeedToMyPage() {
        //Creating player
        Player player = new Player("newPlayer", "yossi","cohen", new Date(),EPlayerRole.GoalKeeper);
        String feed = "this is my first post";
        player.addFeedToMyPage(feed);
        assertTrue(player.getMyPage().getMyFeed().size() == 1 && player.getMyPage().getMyFeed().get(0).equals(feed));
        player.addFeedToMyPage(null);
        assertTrue(player.getMyPage().getMyFeed().size() == 1 && player.getMyPage().getMyFeed().get(0).equals(feed));

        //Creating player with Fan
        Player playerFan = new Player(new Fan("newPlayer", "yossi","cohen"), new Date(),EPlayerRole.GoalKeeper);
        String feedFan = "this is my first post";
        playerFan.addFeedToMyPage(feedFan);
        assertTrue(playerFan.getMyPage().getMyFeed().size() == 1 && playerFan.getMyPage().getMyFeed().get(0).equals(feedFan));
        playerFan.addFeedToMyPage(null);
        assertTrue(playerFan.getMyPage().getMyFeed().size() == 1 && playerFan.getMyPage().getMyFeed().get(0).equals(feedFan));

        //Creating player with CanBeOwner
        Player playerOwner = new Player(new CanBeOwner("newPlayer", "yossi","cohen"), new Date(),EPlayerRole.GoalKeeper);
        String feedOwner = "this is my first post";
        playerOwner.addFeedToMyPage(feedOwner);
        assertTrue(playerOwner.getMyPage().getMyFeed().size() == 1 && playerOwner.getMyPage().getMyFeed().get(0).equals(feedOwner));
        playerOwner.addFeedToMyPage(null);
        assertTrue(playerOwner.getMyPage().getMyFeed().size() == 1 && playerOwner.getMyPage().getMyFeed().get(0).equals(feedOwner));

        //Check Observers
        player.getMyPage().register(new Fan("daniel","levi","levi"));
        player.addFeedToMyPage("new Feed");

    }

    @Test
    void removeFeedFromMyPage() {
        Player player = new Player("newPlayer", "yossi","cohen", new Date(),EPlayerRole.GoalKeeper);
        String feed = "this is my first post";
        player.addFeedToMyPage(feed);
        player.removeFeedFromMyPage(feed);
        assertEquals(0, player.getMyPage().getMyFeed().size());
        player.getMyPage().removeFeedFromMyPage(null);
        assertEquals(0, player.getMyPage().getMyFeed().size());

        //Check null case
        player.removeFeedFromMyPage(null);

    }

    @Test
    void setRole() {
        Player player = new Player("newPlayer", "yossi","cohen", new Date(),EPlayerRole.GoalKeeper);
        assertEquals(player.getRole(), EPlayerRole.GoalKeeper);
        player.setRole(EPlayerRole.Defender);
        assertEquals(player.getRole(), EPlayerRole.Defender);
        assertEquals(player.getMyPage().getRole(), EPlayerRole.Defender);
        player.setRole(null);
        assertEquals(player.getRole(), EPlayerRole.Defender);
        assertEquals(player.getMyPage().getRole(), EPlayerRole.Defender);
    }

    @Test
    void setMyPage() {
        Date bDay = new Date();
        Player player = new Player("newPlayer", "yossi","cohen", bDay,EPlayerRole.GoalKeeper);
        String feed = "this is my first post";
        player.addFeedToMyPage(feed);
        PlayerPageEditor newPage = new PlayerPageEditor("yossi","cohen",EPlayerRole.GoalKeeper,bDay);
        player.setMyPage(newPage);
        assertEquals(0, player.getMyPage().getMyFeed().size());
        player.setMyPage(null);
        assertEquals(0, player.getMyPage().getMyFeed().size());
    }

    @Test
    void logout() {
        Player player = new Player("newPlayer", "yossi","cohen", new Date(),EPlayerRole.GoalKeeper);
        assertEquals(player.getStatus(), EStatus.ONLINE);
        player.logout();
        assertEquals(player.getStatus(), EStatus.OFFLINE);
    }


    @Test
    void setfName() {
        Player player = new Player("newPlayer", "yossi","cohen", new Date(),EPlayerRole.GoalKeeper);
        assertEquals(player.getfName(),"yossi");
        player.setfName("yosi");
        assertEquals(player.getfName(), "yosi");
        assertEquals(player.getMyPage().getMyFirstName(), "yosi");

        player.setfName(null);
        assertEquals(player.getfName(), "yosi");
        assertEquals(player.getMyPage().getMyFirstName(), "yosi");


    }

    @Test
    void setlName() {
        Player player = new Player("newPlayer", "yossi","cohen", new Date(),EPlayerRole.GoalKeeper);
        assertEquals(player.getlName(),"cohen");
        player.setlName("levi");
        assertEquals(player.getlName(), "levi");
        assertEquals(player.getMyPage().getMyLastName(), "levi");
        player.setlName(null);
        assertEquals(player.getlName(), "levi");
        assertEquals(player.getMyPage().getMyLastName(), "levi");


    }

    @Test
    void setStatus() {
        Player player = new Player("newPlayer", "yossi","cohen", new Date(),EPlayerRole.GoalKeeper);
        assertEquals(player.getStatus(), EStatus.ONLINE);
        player.setStatus(EStatus.OFFLINE);
        assertEquals(player.getStatus(), EStatus.OFFLINE);
    }
}
