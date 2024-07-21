package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.map.ActorMapObject;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.*;
import sk.tuke.kpi.oop.game.items.*;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

import java.util.Collection;

public class MyScenar implements SceneListener {
    public static class Factory implements ActorFactory {
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            if("ellen".equals(name)){
                return new Ripley();
            } else if ("energy".equals(name)) {
                return new Energy();
            } else if (name.contains("door")) {
                if("vertical".equals(type)) {
                    return new LockedDoor(name, Door.Orientation.VERTICAL);
                }else if ("horizontal".equals(type)) {
                    return new LockedDoor(name, Door.Orientation.HORIZONTAL);
                }
            } else if ("access card".equals(name)) {
                return new AccessCard();
            } else if ("locker".equals(name)) {
                return new Locker();
            } else if ("ventilator".equals(name)) {
                return new Ventilator();
            } /*else if ("alien".equals(name)) {
                if("running".equals(type)){
                    return new Alien(new RandomlyMoving());
                } else if ("waiting1".equals(type)){
                    return new Alien(new Observing<>(Door.DOOR_OPENED, actor->actor.getName().equals("front door"),new RandomlyMoving()));
                } else if ("waiting2".equals(type)){
                    return new Alien(new Observing<>(Door.DOOR_OPENED,actor->actor.getName().equals("back door"),new RandomlyMoving()));
                }
                return new Alien(null);
            } */else if ("ammo".equals(name)) {
                return new Ammo();
            } /*else if ("alien mother".equals(name)) {
                if("running".equals(type)){
                    return new AlienMother(new RandomlyMoving());
                } else if ("waiting1".equals(type)){
                    return new AlienMother(new Observing<>(Door.DOOR_OPENED,actor->actor.getName().equals("front door"),new RandomlyMoving()));
                } else if ("waiting2".equals(type)){
                    return new AlienMother(new Observing<>(Door.DOOR_OPENED,actor->actor.getName().equals("back door"),new RandomlyMoving()));
                }
                return new AlienMother(null);
            }*//*else if (name.contains("golden bar")){
                return new GoldenBar(Integer.parseInt(type));
            }*/ else if ("fire".equals(name)) {
                return new FocalPoint();
            } else if ("serf".equals(name)) {
                return new Serf();
            } else if ("chief".equals(name)) {
                return new Chief();
            } else if ("egg".equals(name)) {
                return Egg.getEgg();
            }
            return null;
        }
    }
    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        SceneListener.super.sceneInitialized(scene);
        Collection<ActorMapObject> colection=scene.getMap().getActorObjects();
        GoldenBar goldenBar20 = new GoldenBar(20);
        colection.stream().filter(actorMapObject -> actorMapObject.getName().equals("golden bar 201")).forEach(actorMapObject -> scene.addActor(goldenBar20,actorMapObject.getPosX(),actorMapObject.getPosY()));
        colection.stream().filter(actorMapObject -> !actorMapObject.getName().equals("golden bar 201")&&actorMapObject.getName().contains("golden bar 20")).forEach(actorMapObject -> scene.addActor(goldenBar20.clone(),actorMapObject.getPosX(),actorMapObject.getPosY()));
        GoldenBar goldenBar10 = new GoldenBar(10);
        colection.stream().filter(actorMapObject -> actorMapObject.getName().equals("golden bar 101")).forEach(actorMapObject -> scene.addActor(goldenBar10,actorMapObject.getPosX(),actorMapObject.getPosY()));
        colection.stream().filter(actorMapObject -> !actorMapObject.getName().equals("golden bar 101")&&actorMapObject.getName().contains("golden bar 10")).forEach(actorMapObject -> scene.addActor(goldenBar10.clone(),actorMapObject.getPosX(),actorMapObject.getPosY()));
        GoldenBar goldenBar5 = new GoldenBar(5);
        colection.stream().filter(actorMapObject -> actorMapObject.getName().equals("golden bar 51")).forEach(actorMapObject -> scene.addActor(goldenBar5,actorMapObject.getPosX(),actorMapObject.getPosY()));
        colection.stream().filter(actorMapObject -> !actorMapObject.getName().equals("golden bar 51")&&actorMapObject.getName().contains("golden bar 5")).forEach(actorMapObject -> scene.addActor(goldenBar5.clone(),actorMapObject.getPosX(),actorMapObject.getPosY()));
        GoldenBar goldenBar2 = new GoldenBar(2);
        colection.stream().filter(actorMapObject -> actorMapObject.getName().equals("golden bar 21")).forEach(actorMapObject -> scene.addActor(goldenBar2,actorMapObject.getPosX(),actorMapObject.getPosY()));
        colection.stream().filter(actorMapObject -> !actorMapObject.getName().equals("golden bar 21")&&actorMapObject.getName().contains("golden bar 2")&&!actorMapObject.getName().contains("golden bar 20")).forEach(actorMapObject -> scene.addActor(goldenBar2.clone(),actorMapObject.getPosX(),actorMapObject.getPosY()));
        GoldenBar goldenBar1 = new GoldenBar(1);
        colection.stream().filter(actorMapObject -> actorMapObject.getName().equals("golden bar 11")).forEach(actorMapObject -> scene.addActor(goldenBar1,actorMapObject.getPosX(),actorMapObject.getPosY()));
        colection.stream().filter(actorMapObject -> !actorMapObject.getName().equals("golden bar 11")&&actorMapObject.getName().contains("golden bar 1")&&!actorMapObject.getName().contains("golden bar 10")).forEach(actorMapObject -> scene.addActor(goldenBar1.clone(),actorMapObject.getPosX(),actorMapObject.getPosY()));
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
            scene.getGame().getOverlay().drawText("GAME OVER!",xTextPos/2,yTextPos/2).showFor(5);
            new ActionSequence<>(new Wait<>(5), new Invoke<>(()->scene.getGame().stop())).scheduleOn(scene);
            scene.getActors().stream().forEach(actor-> scene.cancelActions(actor));
        });
        scene.getMessageBus().subscribe(Door.DOOR_OPENED,door -> {
            if(door.getName().equals("exit door")){
                scene.getGame().getOverlay().drawText("WELL DONE!",xTextPos/2,yTextPos/2).showFor(5);
                new ActionSequence<>(new Wait<>(5), new Invoke<>(()->scene.getGame().stop())).scheduleOn(scene);
                scene.getActors().stream().forEach(actor-> scene.cancelActions(actor));
            }
        });
        scene.getMessageBus().subscribe(Egg.EGG_OPENED,egg ->scene.getGame().getOverlay().drawText("Egg was opened!",xTextPos/2,yTextPos/2).showFor(2));
        scene.getMessageBus().subscribe(Egg.EGG_BREAKED,egg -> {scene.getGame().getOverlay().drawText("Egg was destroyed!",xTextPos/2,yTextPos/2).showFor(2);
            scene.getActors().stream().filter(actor -> actor instanceof LockedDoor).forEach(actor -> ((LockedDoor) actor).unlock());
        });
        scene.getMessageBus().subscribe(Alien.ALIEN_DIED,alien -> {
            scene.getGame().getOverlay().drawText("Alien was killed", xTextPos / 2, yTextPos / 2).showFor(2);
            scene.getActors().stream().filter(actor -> actor instanceof LockedDoor).forEach(actor -> ((LockedDoor) actor).unlock());
        });
        SceneListener.super.sceneUpdating(scene);
    }
}
