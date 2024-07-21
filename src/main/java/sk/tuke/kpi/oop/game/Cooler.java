package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Cooler extends AbstractActor implements Switchable {
    private Reactor reactor;
    private Animation normalAnimation;
    private boolean status;

    public Cooler (Reactor reactor) {
        this.normalAnimation = new Animation("sprites/fan.png", 32, 32, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(normalAnimation);
        normalAnimation.pause();
        this.reactor=reactor;
        this.status=false;
    }

    @Override
    public void turnOn() {
        normalAnimation.play();
        status=true;
    }
    @Override
    public void turnOff() {
        normalAnimation.pause();
        status=false;
    }
    @Override
    public boolean isOn(){
        return status;
    }
    private void coolReactor(){
        if(this.status&&reactor!=null) {
            reactor.decreaseTemperature(1);
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene){
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }
}
