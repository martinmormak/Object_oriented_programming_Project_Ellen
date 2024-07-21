package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.function.Consumer;

public class Carrot extends AbstractActor implements Collectible {
    public Carrot(){
        Animation normalAnimation=new Animation("sprites/carrot.png",24,24);
        setAnimation(normalAnimation);
    }
    public void eat(Ripley consumer){
        if(consumer==null)return;
        if(((Alive)consumer).getHealth().getValue()<100) {
            ((Alive)consumer).getHealth().refill(10);
            (consumer).getBackpack().remove((consumer).getBackpack().peek());
        }
    }
}
