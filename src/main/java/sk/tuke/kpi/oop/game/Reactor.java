package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable{
    private int temperature;
    private int damage;
    private boolean status;
    private Animation offAnimation;
    private Animation normalAnimation;
    private Animation hotAnimation;
    private Animation brokenAnimation;
    private Animation extinguishedAnimation;
    private Set<EnergyConsumer> devices;

    public Reactor(){
        this.temperature=0;
        this.damage=0;
        this.status=false;
        this.offAnimation = new Animation("sprites/reactor.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.extinguishedAnimation = new Animation("sprites/reactor_extinguished.png");
        setAnimation(offAnimation);
        devices = new HashSet<>();
    }

    public int getDamage() {
        return damage;
    }

    public int getTemperature() {
        return temperature;
    }

    public void increaseTemperature(int increment){
        int inc=increment;
        int dmg;
        if(increment>0 && status==true) {
            if (damage > 66) {
                inc = 2 * increment;
            } else if (damage >= 33) {
                inc = (int) Math.ceil(increment + increment / 2);
            }
            /*temperature = temperature + inc;
            if (temperature < 0) {
                temperature = 0;
            }*/
            temperature=Math.max(0,temperature + inc);
            dmg = (int) Math.ceil((temperature - 2000) * 0.025);
            if (temperature > 2000&&inc > 0&&dmg>damage) {
                damage=Math.min(100,dmg);
            }
            if(damage==100){
                turnOff();
            }
            /*if (temperature > 2000&&inc > 0) {
                dmg = (int) Math.ceil((temperature - 2000) * 0.025);
                if (dmg > damage) {
                    damage=Math.min(100,dmg);
                }
                if (damage >= 100) {
                    damage = 100;
                    turnOff();
                }
            }*/
            updateAnimation();
        }
    }

    public void decreaseTemperature(int decrement){
        if(decrement > 0&&damage < 100&&status==true) {
            if (damage < 50) {
                temperature = temperature - decrement;
            } else {
                temperature = (int) Math.ceil(temperature - (decrement / 2));
            }
            if (temperature < 0) {
                temperature = 0;
            }
            updateAnimation();
        }
    }

    private void updateFrameDuration(){
        if(damage>=75) {
            getAnimation().setFrameDuration(0.025f);
        } else if(damage>=50){
            getAnimation().setFrameDuration(0.05f);
        } else if(damage>=25){
            getAnimation().setFrameDuration(0.075f);
        }else{
            getAnimation().setFrameDuration(0.1f);
        }
    }

    private void updateAnimation()
    {
        if(this.damage == 100){
            this.setAnimation(this.brokenAnimation);
        }else if (this.temperature > 4000) {
            this.setAnimation(this.hotAnimation);
        } else if (this.temperature < 4000) {
            this.setAnimation(this.normalAnimation);
        }
        if(this.status==false&&this.damage < 100) {
            this.setAnimation(this.offAnimation);
        }
        if(this.temperature <6000 && this.damage==100){
            this.setAnimation(this.extinguishedAnimation);
        }
        updateFrameDuration();
        /*if(damage>=75) {
            getAnimation().setFrameDuration(0.025f);
        } else if(damage>=50){
            getAnimation().setFrameDuration(0.05f);
        } else if(damage>=25){
            getAnimation().setFrameDuration(0.075f);
        }else{
            getAnimation().setFrameDuration(0.1f);
        }*/
    }

    public boolean repair()
    {
        if(this.damage == 100)
        {
            return false;
        }
        if(temperature>=2000) {
            damage = Math.max(0, damage - 50);
            temperature = (int) Math.max(2000, 2000 + damage * 40);
            updateAnimation();
            return true;
        }
        return false;
    }

    @Override
    public void turnOn(){
        if(damage<100) {
            status = true;
            updateAnimation();
            for (EnergyConsumer consumer : devices) {
                consumer.setPowered(true);
            }
            /*if(this.light!=null) {
                this.light.setPowered(true);
            }*/
        }
    }

    @Override
    public void turnOff(){
        this.status=false;
        updateAnimation();
        /*this.setAnimation(this.offAnimation);
        updateFrameDuration();*/
        for (EnergyConsumer consumer :devices)
        {
            consumer.setPowered(false);
        }
        /*if(this.light!=null) {
            this.light.setPowered(false);
        }*/
    }

    @Override
    public boolean isOn(){
        return status;
    }

    public void addDevice(EnergyConsumer energyConsumer){
        this.devices.add(energyConsumer);
        if(this.status==true&&this.getDamage()<100) {
            energyConsumer.setPowered(true);
        } else {
            energyConsumer.setPowered(false);
        }
    }
    /*public void addDevice(Light light){
        this.devices.add(light);
        if(this.status==true&&this.getDamage()<100) {
            light.setPowered(true);
        } else {
            light.setPowered(false);
        }
    }*/

    /*public void addDevice(Computer computer){
        this.devices.add(computer);
        if(this.status==true&&this.getDamage()<100) {
            computer.setPowered(true);
        } else {
            computer.setPowered(false);
        }
    }*/

    public void removeDevice(EnergyConsumer energyConsumer){
        energyConsumer.setPowered(false);
        this.devices.remove(energyConsumer);
    }

    /*public void removeDevice(Light light){
        light.setPowered(false);
        this.devices.remove(light);
    }*/

    /*public void removeDevice(Computer computer){
        computer.setPowered(false);
        this.devices.remove(computer);
    }*/

    public boolean extinguish(){
        if(this.damage==100){
            this.temperature=4000;
            updateAnimation();
            return true;
        }
        return false;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleFor(this);
    }
}
