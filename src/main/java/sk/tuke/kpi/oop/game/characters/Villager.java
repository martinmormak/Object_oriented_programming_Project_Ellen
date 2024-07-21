package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public interface Villager extends Actor, Movable {
    public void sellItem(Customer customer);
    public void setBehaviour(Behaviour<? super Villager> behaviour);

    @Override
    default int getSpeed(){return 1;}
}
