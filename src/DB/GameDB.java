package DB;

import AssociationAssets.Field;
import AssociationAssets.Game;

import java.util.HashMap;

/**
 * this class is the GameDB class
 * Aouthors: Tair Cohen
 */
public class GameDB {
    HashMap<Integer, Game> allGames;

    public GameDB() {
        this.allGames = new HashMap<>();
    }
    public void addGame(Game newGame,int gid){
        allGames.put(gid,newGame);
    }
    public void removeGame(int gid){
        this.allGames.remove(gid);
    }

    public HashMap<Integer, Game> getAllGames() {
        return allGames;
    }
}
