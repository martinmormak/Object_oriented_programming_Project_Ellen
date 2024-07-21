package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.oop.game.items.Saw;

public interface Customer extends Actor {
    public void buy();
    public void consume();
    public Saw getSaw();
    public void takeFormBackback();
    public void putIntoBackback();
    public void getPaid(float newMoney);
    public void pay(float giveMoney);
    public float getMoney();
}
