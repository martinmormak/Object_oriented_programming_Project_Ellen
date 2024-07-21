package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.openables.Door;

public class Bullet extends AbstractActor implements Fireable {
    private int speed;
    private Animation normalAnimation=new Animation("sprites/bullet.png",16,16);
    public Bullet(){
        setAnimation(normalAnimation);
        this.speed=4;
    }
    public void controlHit(Scene scene){
        scene.getActors().stream().filter(actor -> actor instanceof Alive&&this.intersects(actor)).forEach(actor -> {((Alive)actor).getHealth().drain(10);scene.removeActor(this);
            /*if (((Alive) actor).getHealth().getValue() <= 0) {
                scene.removeActor(actor);
            }*/});
        scene.getActors().stream().filter(actor -> !(actor instanceof Alive)&&!(actor instanceof Door)&&actor!=this&&this.intersects(actor)).forEach(actor -> {scene.removeActor(this);});
    }
    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public void startedMoving(Direction direction) {
        Fireable.super.startedMoving(direction);
        normalAnimation.setRotation(direction.getAngle());
    }

    @Override
    public void collidedWithWall() {
        Fireable.super.collidedWithWall();
        getScene().removeActor(this);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(()->this.controlHit(scene))).scheduleFor(this);
    }
}
