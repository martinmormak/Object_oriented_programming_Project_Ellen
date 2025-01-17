package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer {
    private Animation normalAnimation;
    private boolean powered;

    public Computer(){
        this.normalAnimation= new Animation("sprites/computer.png", 80, 48, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(normalAnimation);
        powered =false;
        normalAnimation.pause();
    }

    public int add(int a, int b) {
        if(powered ==true) {
            return a + b;
        }
        return 0;
    }

    public float add(float a, float b) {
        if(powered ==true) {
            return a + b;
        }
        return 0;
    }

    public int sub(int a, int b) {
        if(powered ==true) {
            return a - b;
        }
        return 0;
    }

    public float sub(float a, float b) {
        if(powered ==true) {
            return a - b;
        }
        return 0;
    }

    @Override
    public void setPowered(boolean powered) {
        this.powered = powered;
        if(this.powered ==false){
            normalAnimation.pause();
        } else {
            normalAnimation.play();
        }
    }
}
