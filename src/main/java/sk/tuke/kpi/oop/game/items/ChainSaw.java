package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Egg;

public class ChainSaw extends AbstractActor implements Saw {
    public ChainSaw(){
        Animation normalAnimation=new Animation("sprites/chain_saw.png",32,14);
        setAnimation(normalAnimation);
    }

    @Override
    public void destroyEgg(Actor actor) {
        ((Egg)actor).breakEgg(20);
    }
}
