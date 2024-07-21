package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class TimeBomb extends AbstractActor {
    private float time;
    private boolean activated;
    //private Animation normalAnimation;
    private Animation activatedAnimation;
    private Animation explosionAnimation;
    public TimeBomb(float time){
        Animation normalAnimation;
        this.time=time;
        normalAnimation=new Animation("sprites/bomb.png",16,16);
        this.activatedAnimation=new Animation("sprites/bomb_activated.png",16,16,0.5f,Animation.PlayMode.LOOP);
        this.explosionAnimation=new Animation("sprites/small_explosion.png",16,16,0.5f, Animation.PlayMode.ONCE);
        setAnimation(normalAnimation);
        activated=false;
    }
    public void removeFromScreen()
    {
        getScene().removeActor(this);
    }
    public void detonate()
    {
        setAnimation(explosionAnimation);
        new ActionSequence<>(new Wait<>(explosionAnimation.getFrameCount()*explosionAnimation.getFrameDuration()), new Invoke<>(this::removeFromScreen)).scheduleFor(this);
    }
    public void activate(){
        setAnimation(activatedAnimation);
        activated=true;
        new ActionSequence<>(new Wait<>(time), new Invoke<>(this::detonate)).scheduleFor(this);
    }

    public boolean isActivated() {
        return activated;
    }
}
