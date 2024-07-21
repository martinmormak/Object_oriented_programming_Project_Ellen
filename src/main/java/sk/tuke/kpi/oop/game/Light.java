package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable,EnergyConsumer {
    private Animation onLightAnimation;
    private Animation offLightAnimation;
    private boolean sw;
    private boolean powered;

    public Light() {
        this.onLightAnimation = new Animation("sprites/light_on.png",16,16);
        this.offLightAnimation = new Animation("sprites/light_off.png",16,16);
        setAnimation(offLightAnimation);
        sw=false;
        powered =false;
    }

    /*public void setElectricityFlow(boolean powered){
        this.powered = powered;
        if(this.powered ==false){
            setAnimation(offLightAnimation);
        } else if (sw==true) {
            setAnimation(onLightAnimation);
        }
    }*/

    public void toggle(){
        if(isOn()==true)
        {
            turnOff();
        } else if (isOn()==false)
        {
            turnOn();
        }
    }

    @Override
    public void turnOn() {
        sw=true;
        if(sw==true&& powered ==true) {
            setAnimation(onLightAnimation);
        }else {
            setAnimation(offLightAnimation);
        }
    }
    @Override
    public void turnOff() {
        sw=false;
        if(sw==true&& powered ==true) {
            setAnimation(onLightAnimation);
        }else {
            setAnimation(offLightAnimation);
        }
    }

    @Override
    public boolean isOn() {
        return sw;
    }

    @Override
    public void setPowered(boolean powered) {
        this.powered = powered;
        if(this.powered ==false){
            setAnimation(offLightAnimation);
        } else if (sw==true) {
            setAnimation(onLightAnimation);
        }
    }
}
