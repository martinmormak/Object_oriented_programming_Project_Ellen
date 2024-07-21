package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;

public interface Saw extends Actor,Collectible {
    public void destroyEgg(Actor actor);
}
