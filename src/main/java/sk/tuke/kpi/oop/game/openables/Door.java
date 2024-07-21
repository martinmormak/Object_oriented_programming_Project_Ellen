package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Usable;

public class Door extends AbstractActor implements Openable, Usable<Actor> {
    private Animation openAnimation;
    private Animation closeAnimation;
    private boolean status;
    public static enum Orientation{
        HORIZONTAL,
        VERTICAL;
    }
    private Orientation orientation;

    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);
    public Door(Orientation orientation){
        String address;
        if(orientation== Orientation.VERTICAL) {
            address="sprites/vdoor.png";
            this.openAnimation = new Animation(address, 16, 32, 0.1f, Animation.PlayMode.ONCE);
            this.closeAnimation = new Animation(address, 16, 32, 0.1f, Animation.PlayMode.ONCE_REVERSED);
        }else if(orientation== Orientation.HORIZONTAL) {
            address="sprites/hdoor.png";
            this.openAnimation = new Animation(address, 32, 16, 0.1f, Animation.PlayMode.ONCE);
            this.closeAnimation = new Animation(address, 32, 16, 0.1f, Animation.PlayMode.ONCE_REVERSED);
        }
        this.orientation=orientation;
        close();
    }
    public Door(String name, Orientation orientation){
        super(name);
        String address;
        if(orientation== Orientation.VERTICAL) {
            address="sprites/vdoor.png";
            this.openAnimation = new Animation(address, 16, 32, 0.1f, Animation.PlayMode.ONCE);
            this.closeAnimation = new Animation(address, 16, 32, 0.1f, Animation.PlayMode.ONCE_REVERSED);
        }else if(orientation== Orientation.HORIZONTAL) {
            address="sprites/hdoor.png";
            this.openAnimation = new Animation(address, 32, 16, 0.1f, Animation.PlayMode.ONCE);
            this.closeAnimation = new Animation(address, 32, 16, 0.1f, Animation.PlayMode.ONCE_REVERSED);
        }
        this.orientation=orientation;
        close();
    }

    @Override
    public void useWith(Actor actor) {
        if(this.isOpen()){
            this.close();
        }
        else{
            this.open();
        }
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

    @Override
    public void open() {
        setAnimation(openAnimation);
        this.status=true;
        if(getScene()!=null) {
            getScene().getMessageBus().publish(DOOR_OPENED,this);
            if(this.orientation==Orientation.VERTICAL) {
                this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
                this.getScene().getMap().getTile(this.getPosX() / 16, 1 + this.getPosY() / 16).setType(MapTile.Type.CLEAR);
            }
            if(this.orientation==Orientation.HORIZONTAL) {
                this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
                this.getScene().getMap().getTile(1 + this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
            }
        }
    }

    @Override
    public void close() {
        setAnimation(closeAnimation);
        this.status=false;
        if(getScene()!=null) {
            getScene().getMessageBus().publish(DOOR_CLOSED,this);
            if(this.orientation==Orientation.VERTICAL) {
                this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
                this.getScene().getMap().getTile(this.getPosX() / 16, 1 + this.getPosY() / 16).setType(MapTile.Type.WALL);
            }
            if(this.orientation==Orientation.HORIZONTAL) {
                this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
                this.getScene().getMap().getTile(1 + this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
            }
            for(Actor actor: this.getScene().getActors()) {
                if(actor instanceof Ripley) {
                    while (this.getScene().getMap().intersectsWithWall(actor)) {
                        if (actor.getPosX()+actor.getWidth()/2 > this.getPosX()+this.getWidth()/2) {
                            actor.setPosition(actor.getPosX() + 1, actor.getPosY());
                        } else {
                            actor.setPosition(actor.getPosX() - 1, actor.getPosY());
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isOpen() {
        return this.status;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        this.close();
    }
}
