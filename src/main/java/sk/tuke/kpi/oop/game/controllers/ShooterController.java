package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.actions.Fire;
import sk.tuke.kpi.oop.game.characters.Armed;

import java.util.ArrayList;
import java.util.List;

public class ShooterController implements KeyboardListener {
    private Armed armed;
    private List<Input.Key> action=new ArrayList<>(){{
        add(Input.Key.SPACE);
    };};
    public ShooterController(Armed armed){
        this.armed=armed;
    }
    @Override
    public void keyPressed(Input.Key key) {
        KeyboardListener.super.keyPressed(key);
        if(this.armed==null)return;
        if(action.contains(key)&&key==Input.Key.SPACE){
            new Fire<>().scheduleFor(armed);
        }
    }
}
