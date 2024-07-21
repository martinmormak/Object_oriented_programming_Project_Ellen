package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;

public class Ventilator extends AbstractActor implements Repairable {
    private Animation normalAnimation;
    private boolean repaired;

    public static final Topic<Ventilator> VENTILATOR_REPAIRED = Topic.create("ventilator repaired", Ventilator.class);

    public Ventilator(){
        normalAnimation=new Animation("sprites/ventilator.png",32,32);
        setAnimation(normalAnimation);
        repaired=false;
        normalAnimation.pause();
    }

    @Override
    public boolean repair() {
        if(repaired==false){
            repaired=true;
            normalAnimation.play();
            this.getScene().getMessageBus().publish(VENTILATOR_REPAIRED,this);
            return true;
        }
        return false;
    }
}
