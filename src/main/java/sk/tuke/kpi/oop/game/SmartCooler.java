package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class SmartCooler extends Cooler{
    private Reactor reactor;
    public SmartCooler(Reactor reactor) {
        super(reactor);
        this.reactor=reactor;
    }

    public void toogle(){
        if(reactor!=null) {
            if (this.reactor.getTemperature() > 2500) {
                this.turnOn();
            }

            if (this.reactor.getTemperature() < 1500) {
                this.turnOff();
            }
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene){
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::toogle)).scheduleFor(this);
    }
}
