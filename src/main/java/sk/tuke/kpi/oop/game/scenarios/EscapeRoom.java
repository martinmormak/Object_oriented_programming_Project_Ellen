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
import sk.tuke.kpi.oop.game.behaviours.Observing;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.AlienMother;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;


public class EscapeRoom implements SceneListener {
    public static class Factory implements ActorFactory{
    @Override
    public @Nullable Actor create(@Nullable String type, @Nullable String name) {
        if("ellen".equals(name)){
            return new Ripley();
        } else if ("energy".equals(name)) {
            return new Energy();
        } else if (name.contains("door")) {
            if("vertical".equals(type)) {
                return new Door(name, Door.Orientation.VERTICAL);
            }else if ("horizontal".equals(type)) {
                return new Door(name, Door.Orientation.HORIZONTAL);
            }
        } else if ("access card".equals(name)) {
            return new AccessCard();
        } else if ("locker".equals(name)) {
            return new Locker();
        } else if ("ventilator".equals(name)) {
            return new Ventilator();
        } else if ("alien".equals(name)) {
            if("running".equals(type)){
                return new Alien(new RandomlyMoving());
            } else if ("waiting1".equals(type)){
                return new Alien(new Observing<>(Door.DOOR_OPENED,actor->actor.getName().equals("front door"),new RandomlyMoving()));
            } else if ("waiting2".equals(type)){
                return new Alien(new Observing<>(Door.DOOR_OPENED,actor->actor.getName().equals("back door"),new RandomlyMoving()));
            }
            return new Alien(null);
        } else if ("ammo".equals(name)) {
            return new Ammo();
        } else if ("alien mother".equals(name)) {
            if("running".equals(type)){
                return new AlienMother(new RandomlyMoving());
            } else if ("waiting1".equals(type)){
                return new AlienMother(new Observing<>(Door.DOOR_OPENED,actor->actor.getName().equals("front door"),new RandomlyMoving()));
            } else if ("waiting2".equals(type)){
                return new AlienMother(new Observing<>(Door.DOOR_OPENED,actor->actor.getName().equals("back door"),new RandomlyMoving()));
            }
            return new AlienMother(null);
        }
        return null;
    }
}

    @Override
    public void sceneCreated(@NotNull Scene scene) {
        SceneListener.super.sceneCreated(scene);
        //scene.getMessageBus().subscribe(World.ACTOR_ADDED_TOPIC,(actor)-> {if(actor instanceof Alien){((Alien) actor).randomMove();}});
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
        /*AtomicReference<Disposable> disposable = new AtomicReference<>();
        scene.getMessageBus().subscribeOnce(Door.DOOR_OPENED, door -> disposable.set(decrementEnergy(scene.getFirstActorByType(Ripley.class))));
        scene.getMessageBus().subscribeOnce(Ventilator.VENTILATOR_REPAIRED,ventilator -> disposable.get().dispose());*/
        //scene.getFirstActorByType(Ripley.class).getHealth().drain(50);

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
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        int windowWidth = scene.getGame().getWindowSetup().getHeight();
        int xTextPos = windowWidth - GameApplication.STATUS_LINE_OFFSET;
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED,ripley -> {
            scene.getActors().stream().forEach(actor-> scene.cancelActions(actor));
            new Loop<>(new Invoke<>(()->scene.getGame().getOverlay().drawText("GAME OVER!",xTextPos/2,yTextPos/2))).scheduleOn(scene);
            new ActionSequence<>(new Wait<>(5), new Invoke<>(()->scene.getGame().stop())).scheduleOn(scene);
        });
        scene.getMessageBus().subscribe(Door.DOOR_OPENED,door -> {
            if(door.getName().equals("exit door")){
                scene.getActors().stream().forEach(actor-> scene.cancelActions(actor));
                new Loop<>(new Invoke<>(()->scene.getGame().getOverlay().drawText("WELL DONE!",xTextPos/2,yTextPos/2))).scheduleOn(scene);
                new ActionSequence<>(new Wait<>(5), new Invoke<>(()->scene.getGame().stop())).scheduleOn(scene);
            }
        });
        SceneListener.super.sceneUpdating(scene);
    }
}
