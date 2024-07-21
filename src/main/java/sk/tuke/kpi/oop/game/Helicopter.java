package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Helicopter extends AbstractActor {
    //private Animation normalAnimation;
    private Player player;
    private boolean status;

    public Helicopter(){
        Animation normalAnimation;
        normalAnimation = new Animation("sprites/heli.png", 64, 64, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(normalAnimation);
        player=null;
    }
    public void searchAndDestroy(){
        if(status==false) {
            player = this.getScene().getFirstActorByType(Player.class);
            status = true;
        }else {
            status = false;
        }
        new Loop<>(new Invoke<>(this::move)).scheduleFor(this);
    }
    public void move(){
        if(player!=null&&status) {
            if (this.getPosX() > player.getPosX()) {
                this.setPosition(getPosX() - 1, getPosY());
            } else if (this.getPosX() < player.getPosX()) {
                this.setPosition(getPosX() + 1, getPosY());
            }
            if (this.getPosY() > player.getPosY()) {
                this.setPosition(getPosX(), getPosY() - 1);
            } else if (this.getPosY() < player.getPosY()) {
                this.setPosition(getPosX(), getPosY() + 1);
            }
            if (player.intersects(this)) {
                player.setEnergy(player.getEnergy()-1);
            }
        }
    }
    /*@Override
    public void addedToScene(@NotNull Scene scene){
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::move)).scheduleFor(this);
    }*/
}
