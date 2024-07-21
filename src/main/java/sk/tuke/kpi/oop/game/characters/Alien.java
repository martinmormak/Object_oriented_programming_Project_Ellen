package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class Alien extends AbstractActor implements Movable,Alive,Enemy {
    private int speed;
    private Health health;
    private Behaviour<? super Alien> behaviour;
    private Animation normalAnimation=new Animation("sprites/alien.png",32,32,0.1f, Animation.PlayMode.LOOP);

    public static final Topic<Alien> ALIEN_DIED = Topic.create("Alien died", Alien.class);
    public Alien(int health){
        setAnimation(normalAnimation);
        this.speed=5;
        this.health=new Health(health);
        this.behaviour=null;
    }
    public Alien(int health, Behaviour<? super Alien> behaviour){
        setAnimation(normalAnimation);
        this.speed=1;
        this.health=new Health(health);
        this.behaviour= behaviour;
    }
    public Alien(Behaviour<? super Alien> behaviour){
        this(100,behaviour);
    }
    public Alien(){
        this(100);
    }
    /*public void randomMove(){
        new Loop<>(new Invoke<>(this::loopRandomMove)).scheduleFor(this);
    }
    public void loopRandomMove(){
        Direction direction;
        if(move==null){
            do {
                direction=Direction.values()[(int) (Math.random()*9)];
            }while (direction==Direction.NONE);
            this.move=new Move<>(direction, (float) (Math.random()*5));
            move.scheduleFor(this);
        }
        if(move.isDone()){
            do {
                direction=Direction.values()[(int) (Math.random()*9)];
            }while (direction==Direction.NONE);
            this.move=new Move<>(Direction.values()[(int) (Math.random()*9)], (float) (Math.random()*5));
            move.scheduleFor(this);
        }
    }*/
    public void controlHealth(Scene scene){
        if(this.health.getValue()<=0){
            scene.removeActor(this);
            scene.getMessageBus().publish(ALIEN_DIED,this);
        }
    }
    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public void startedMoving(Direction direction) {
        Movable.super.startedMoving(direction);
        this.getAnimation().setRotation(direction.getAngle());
        this.getAnimation().play();
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        /*new  Loop<>(new Invoke<>(actor -> {
            if(actor instanceof Alien&&actor instanceof Enemy==false&&this.intersects(actor)){
                ((Alien)actor).getHealth().drain(50);
            }
        })).scheduleFor(this);*/
        //new Loop<>(new Invoke<>().scheduleFor(this));

        //new Loop<>(new Invoke<>(()->scene.getActors().stream().filter(actor -> actor instanceof Ripley&&this.intersects(actor)).forEach(actor -> ((Alive)actor).getHealth().drain(1)))).scheduleFor(this);
        //new Loop<>(new Invoke<>(()->scene.getActors().stream().filter(actor -> actor instanceof Alive&&!(actor instanceof Enemy)&&this.intersects(actor)).forEach(actor -> ((Alive)actor).getHealth().drain(1)))).scheduleFor(this);
        contolIntersecting(scene);
        new Loop<>(new Invoke<>(()->this.controlHealth(scene))).scheduleFor(this);
        if(behaviour!=null){behaviour.setUp(this);}
    }

    public void contolIntersecting(Scene scene){
        scene.getActors().stream().filter(actor -> actor instanceof Alive&&!(actor instanceof Enemy)&&this.intersects(actor)).forEach(actor -> ((Alive)actor).getHealth().drain(1));
        new ActionSequence<>(
            new Wait<>(0.25f),
            new Invoke<>(()->contolIntersecting(scene))
        ).scheduleOn(scene);
    }
}
