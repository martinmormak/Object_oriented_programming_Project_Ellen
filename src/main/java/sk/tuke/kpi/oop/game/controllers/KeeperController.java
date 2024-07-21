package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.ArrayList;
import java.util.List;

public class KeeperController implements KeyboardListener {
    private Keeper keeper;
    private List<Input.Key> action=new ArrayList<>(){{
        add(Input.Key.ENTER);
        add(Input.Key.BACKSPACE);
        add(Input.Key.S);
        add(Input.Key.U);
        add(Input.Key.B);
    };};

    public KeeperController(Keeper keeper) {
        this.keeper=keeper;
    }

    @Override
    public void keyPressed(Input.Key key){//Input.@NotNull Key key
        KeyboardListener.super.keyPressed(key);
        if(this.keeper==null)return;
        if(action.contains(key)){
            switch (key){
                case ENTER:{
                    //Take<Keeper> take=new Take<>();
                    /*take.setActor(keeper);
                    take.execute(0);*/
                    //take.scheduleFor(keeper);
                    new Take<>().scheduleFor(keeper);
                    break;
                }
                case BACKSPACE:{
                    //Drop<Keeper> drop=new Drop<>();
                    /*drop.setActor(keeper);
                    drop.execute(0);*/
                    //drop.scheduleFor(keeper);
                    new Drop<>().scheduleFor(keeper);
                    break;
                }
                case S:{
                    //Shift<Keeper> shift=new Shift<>().scheduleFor(keeper);
                    /*shift.setActor(keeper);
                    shift.execute(0);*/
                    //shift.scheduleFor(keeper);
                    new Shift<>().scheduleFor(keeper);
                    break;
                }
                case U:{
                    /*for(Actor actor: keeper.getScene().getActors()){
                        if(actor instanceof Usable&&actor.intersects(keeper)) {
                            new Use<>((Usable<?>)actor).scheduleForIntersectingWith(keeper);
                            break;
                        }
                    }*/
                    keeper.getScene().getActors().stream().filter(actor->actor instanceof Usable&&actor.intersects(keeper)).forEach(actor->new Use<>((Usable<?>)actor).scheduleForIntersectingWith(keeper));
                    break;
                }
                case B:{
                    /*for (Actor actor : keeper.getScene().getActors()) {
                        if ((actor instanceof LockedDoor||actor instanceof Ventilator)&& actor.intersects(keeper)&&keeper.getBackpack().peek() instanceof Usable) {
                            new Use<>((Usable<?>)keeper.getBackpack().peek()).scheduleForIntersectingWith(actor);
                            break;
                        }
                    }*/
                    keeper.getScene().getActors().stream().filter(actor->actor.intersects(keeper)&&keeper.getBackpack().peek() instanceof Usable).forEach(actor->new Use<>((Usable<?>)keeper.getBackpack().peek()).scheduleForIntersectingWith(keeper));
                    break;
                }
                default:{
                    break;
                }
            }
        }
    }
}
