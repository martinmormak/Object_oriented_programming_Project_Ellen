package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class FocalPoint extends AbstractActor{
    public FocalPoint(){
        Animation normalAnimation=new Animation("sprites/fire.png",10,19,0.1f, Animation.PlayMode.LOOP);
        setAnimation(normalAnimation);
    }
}
