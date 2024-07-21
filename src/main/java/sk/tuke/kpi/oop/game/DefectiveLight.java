package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class DefectiveLight extends Light implements Repairable{
    private boolean repaired;
    private Disposable disposable;

    public DefectiveLight(){
        super();
        repaired=false;
    }
    public void randRandomNumber()
    {
        int randomNumber;
        repaired=false;
        randomNumber=(int)(Math.random()*(21));
        if(randomNumber==1){
            if(isOn()) {
                turnOff();
            }else {
                turnOn();
            }
            //toggle();
        }
    }
    @Override
    public void addedToScene(@NotNull Scene scene){
        super.addedToScene(scene);
        disposable=new Loop<>(new Invoke<>(this::randRandomNumber)).scheduleFor(this);
    }

    @Override
    public boolean repair() {
        if(repaired||disposable==null) {
            return false;
        }
        repaired = true;
        disposable.dispose();
        //turnOn();
        disposable=new ActionSequence<>(new Wait<>(10),new Loop<>(new Invoke<>(this::randRandomNumber))).scheduleFor(this);
        return true;
    }
}
