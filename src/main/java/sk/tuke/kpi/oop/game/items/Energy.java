package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Energy extends AbstractActor implements Usable<Alive>{
    public Energy(){
        Animation normalAnimation;
        normalAnimation=new Animation("sprites/energy.png",16,16);
        setAnimation(normalAnimation);
    }

    public void controlEnergy() {
        for(Actor actor:getScene().getActors()){
            if(actor instanceof Ripley &&super.intersects(actor)) {
                new Use<>(this).scheduleFor((Ripley) actor);
                //this.useWith((Ripley) actor);
            }
        }
    }
    @Override
    public void useWith(Alive alive) {
        if(alive==null)return;
        if(alive.getHealth().getValue()<100) {
            alive.getHealth().restore();
            getScene().removeActor(this);
        }
    }

    @Override
    public Class<Alive> getUsingActorClass() {
        return Alive.class;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::controlEnergy)).scheduleFor(this);
    }
}
