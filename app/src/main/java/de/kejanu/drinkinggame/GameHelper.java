package de.kejanu.drinkinggame;

import java.util.ArrayList;
import java.util.Iterator;

public class GameHelper {
    public void removeCardGames(ArrayList<Game> gameList) {
        for (Iterator<Game> it = gameList.iterator(); it.hasNext();) {
            if (it.next().isNeedsCards())
                it.remove();
        }
    }
}
