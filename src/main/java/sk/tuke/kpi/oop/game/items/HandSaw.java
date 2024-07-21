package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Egg;

public class HandSaw extends AbstractActor implements Saw  {
    public HandSaw(){
        Animation normalAnimation=new Animation("sprites/saw.png",21,8);
        setAnimation(normalAnimation);
    }

    @Override
    public void destroyEgg(Actor actor) {
        ((Egg)actor).breakEgg(10);
    }
}
