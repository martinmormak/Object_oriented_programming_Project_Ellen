package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class PowerSwitch extends AbstractActor {
    //private Animation controllerAnimation;
    private Switchable device;

    public PowerSwitch(Switchable switchable) {
        Animation controllerAnimation;
        controllerAnimation = new Animation("sprites/switch.png",16,16);
        setAnimation(controllerAnimation);
        this.device=switchable;
    }

    /*public void toggle(){
        if(reactor.isOn()){
            reactor.turnOff();
        }
        else {
            reactor.turnOn();
        }
    }*/
    public Switchable getDevice()
    {
        return device;
    }
    public void switchOn()
    {
        if(device!=null) {
            device.turnOn();
        }
    }
    public void switchOff()
    {
        if(device!=null) {
            device.turnOff();
        }
    }
}
