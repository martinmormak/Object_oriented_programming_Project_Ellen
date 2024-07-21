package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.items.Usable;

public class Locker extends AbstractActor implements Usable<Ripley> {
    private boolean usedBefore;
    public Locker(){
        Animation normalAnimation=new Animation("sprites/locker.png",16,16);
        setAnimation(normalAnimation);
        usedBefore=false;
    }

    @Override
    public void useWith(Ripley ripley) {
        if(usedBefore==false){
            Hammer hammer=new Hammer();
            ripley.getBackpack().add(hammer);
            usedBefore=true;
        }
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}
