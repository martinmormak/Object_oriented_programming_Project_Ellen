package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.items.GoldenBar;
import sk.tuke.kpi.oop.game.strategy.HalfStrategy;

import java.util.ArrayList;
import java.util.List;

public class Egg extends AbstractActor {//singleton pattern, observer pattern
    private static Egg egg=null;
    private Animation openAnimation;
    private Animation breakAnimation;
    private Health health;
    private List<Actor> list;
    private Scene scene;
    public static final Topic<Egg> EGG_OPENED = Topic.create("egg opened", Egg.class);
    public static final Topic<Egg> EGG_BREAKED = Topic.create("egg breaked", Egg.class);

    private Egg(){
        Animation normalAnimation=new Animation("sprites/alien_egg.png",32,32,0.1f, Animation.PlayMode.ONCE_REVERSED);
        this.openAnimation=new Animation("sprites/alien_egg.png",32,32,0.1f, Animation.PlayMode.ONCE);
        this.breakAnimation=new Animation("sprites/alien_break_egg.png",32,32,0.1f, Animation.PlayMode.LOOP);
        setAnimation(normalAnimation);
        this.health=new Health(100);
        this.list = new ArrayList<>();
    }
    public static Egg getEgg(){
        if(egg==null){
            egg=new Egg();
        }
        return egg;
    }
    public void breakEgg(int damage){
        this.health.drain(damage);
        if(this.health.getValue()==0){
            setAnimation(breakAnimation);
            Scene scen=this.getScene();
            this.scene.getMessageBus().publish(EGG_BREAKED,this);
        }
    }

    public Health getHealth() {
        return this.health;
    }

    public void addSubscriber(Actor actor) {
        this.list.add(actor);
    }
    public void openEgg(Scene scene){
        if(getAnimation()==breakAnimation)return;
        setAnimation(openAnimation);
        Alien alien=new Alien(new RandomlyMoving());
        scene.addActor(alien,getPosX()+getWidth()/2-alien.getWidth()/2,getPosY()+getHeight()/2-alien.getHeight()/2);
        list.stream().filter(actor -> actor instanceof Villager).forEach(actor->((Villager) actor).setBehaviour(new RandomlyMoving()));
        list.stream().filter(actor -> actor instanceof GoldenBar).forEach(actor->((GoldenBar) actor).setPaymentStrategy(new HalfStrategy()));
        scene.getMessageBus().publish(EGG_OPENED,this);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        new ActionSequence<>(
            new Wait<>(30),
            new Invoke<>(()->openEgg(scene))
        ).scheduleOn(scene);
        this.scene=scene;
    }
}
