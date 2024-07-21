package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;

import java.util.concurrent.atomic.AtomicReference;

public class MissionImpossible implements SceneListener {
    public static class Factory implements ActorFactory{
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            if("ellen".equals(name)){
                return new Ripley();
            } else if ("energy".equals(name)) {
                return new Energy();
            /*} else if ("door".equals(name)) {
                return new Door();*/
            } else if ("access card".equals(name)) {
                return new AccessCard();
            } else if ("locker".equals(name)) {
                return new Locker();
            } else if ("ventilator".equals(name)) {
            return new Ventilator();
            }
            return null;
        }
    }
    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        SceneListener.super.sceneInitialized(scene);
        /*for(Actor actor:scene.getActors())
        {
            if(actor instanceof Ripley){
                scene.getMessageBus().subscribe(DOOR_OPENED,decrementEnergy(scene.getFirstActorByType(Ripley)));
            }
        }*/
        AtomicReference<Disposable> disposable = new AtomicReference<>();
        scene.getMessageBus().subscribeOnce(Door.DOOR_OPENED,door -> disposable.set(decrementEnergy(scene.getFirstActorByType(Ripley.class))));
        scene.getMessageBus().subscribeOnce(Ventilator.VENTILATOR_REPAIRED,ventilator -> disposable.get().dispose());
    }
    public Disposable decrementEnergy(Ripley ripley){
        //return new Loop<>(new ActionSequence<>(new Wait<>(1),new Invoke<Actor>(rilpey1->ripley.setEnergy(ripley.getEnergy()-1)))).scheduleFor(ripley);
        return new Loop<>(new ActionSequence<>(new Wait<>(1),new Invoke<Actor>(rilpey1->ripley.getHealth().drain(1)))).scheduleFor(ripley);
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        for(Actor actor:scene.getActors()) {
            if (actor instanceof Ripley) {
                ((Ripley) actor).showRipleyState();
            }
        }
        SceneListener.super.sceneUpdating(scene);
    }
}
