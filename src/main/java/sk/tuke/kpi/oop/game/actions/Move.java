package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

public class Move/*<A extends Actor>*/<M extends Movable> implements Action<Movable> {
    private Direction direction;
    private Actor actor;
    private float duration;
    private float beforeDuretion;
    private boolean done;
    private boolean wasCallerdBefore;
    public Move(Direction direction, float duration) {
        // implementacia konstruktora akcie
        this.direction=direction;
        this.duration=duration;
        this.beforeDuretion=0;
        actor=null;
        done=false;
        wasCallerdBefore=false;
    }

    public Move(Direction direction) {
        // implementacia konstruktora akcie
        this.direction=direction;
        this.duration=0;
        this.beforeDuretion=0;
        actor=null;
        done=false;
        wasCallerdBefore=false;
    }

    public void stop(){
        if(getActor()==null)return;
        getActor().stoppedMoving();
        done=true;
    }

    @Override
    public @Nullable Movable getActor() {
        return (Movable) actor;
    }

    @Override
    public void setActor(@Nullable Movable actor) {
        this.actor=actor;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void reset() {
        done=false;
        wasCallerdBefore=false;
        beforeDuretion=0;
    }

    @Override
    public void execute(float deltaTime) {
        if(getActor()==null||this.done==true)return;
        if(!wasCallerdBefore){
            getActor().startedMoving(this.direction);
            wasCallerdBefore=true;
        }
        getActor().setPosition(getActor().getPosX()+getActor().getSpeed()*this.direction.getDx(), actor.getPosY()+getActor().getSpeed()*this.direction.getDy());
        if(getActor().getScene().getMap().intersectsWithWall(getActor())){
            getActor().setPosition(getActor().getPosX()- getActor().getSpeed()*direction.getDx(), getActor().getPosY()- getActor().getSpeed()*direction.getDy());
            getActor().collidedWithWall();
            done=true;
        }
        this.beforeDuretion=this.beforeDuretion+deltaTime;
        if(this.duration-this.beforeDuretion<1e-5)
        {
            //this.done=true;
            this.stop();
        }



        /*if(getActor()==null||this.done==true)return;
        if(!wasCallerdBefore){
            getActor().startedMoving(this.direction);
            wasCallerdBefore=true;
        }
        do {
            new ActionSequence<>(new Wait<>(1), new Invoke<>(()-> moveActor(direction)));
            this.beforeDuretion=this.beforeDuretion+deltaTime;
        }while(this.duration-this.beforeDuretion>1e-5);
        this.stop();*/
    }

    /*public void moveActor(Direction direction)
    {
        getActor().setPosition(getActor().getPosX()+getActor().getSpeed()*this.direction.getDx(), actor.getPosY()+getActor().getSpeed()*this.direction.getDy());
    }*/
}
