package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.*;

public class FirstSteps implements SceneListener {

    private Ripley ripley=new Ripley();
    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        SceneListener.super.sceneInitialized(scene);
        scene.addActor(ripley,100,300);
        /*Move move=new Move(Direction.SOUTH,2);
        move.setActor(ripley);
        move.execute(1);*/
        MovableController movableController=new MovableController(ripley);
        scene.getInput().registerListener(movableController);
        KeeperController keeperController =new KeeperController(ripley);
        scene.getInput().registerListener(keeperController);
        Energy energy=new Energy();
        scene.addActor(energy,100,100);
        ripley.getHealth().drain(50);
        ripley.getFirearm().reload(480);
        Hammer hammer=new Hammer();
        Wrench wrench=new Wrench();
        FireExtinguisher fireExtinguisher=new FireExtinguisher();
        /*ripley.getBackpack().add(hammer);
        ripley.getBackpack().add(wrench);
        ripley.getBackpack().add(fireExtinguisher);*/
        //ripley.getBackpack().remove(hammer);
        //ripley.getBackpack().peek();
        //ripley.getBackpack().shift();
        scene.addActor(hammer,-100,0);
        scene.addActor(wrench,0,-100);
        scene.addActor(fireExtinguisher,-100,-100);
        Take<Keeper> take=new Take<>();
        take.setActor(ripley);
        //take.execute(0);
        Drop<Keeper> drop=new Drop<>();
        drop.setActor(ripley);
        //drop.execute(0);
        Shift<Keeper> shift=new Shift<>();
        shift.setActor(ripley);
        //shift.execute(0);
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        /*int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        scene.getGame().getOverlay().drawText(String.valueOf(ripley.getEnergy()+" || "+ ripley.getAmmo()),windowHeight,yTextPos);
        scene.getGame().pushActorContainer(ripley.getBackpack());*/
        ripley.showRipleyState();
        SceneListener.super.sceneUpdating(scene);
    }
}
