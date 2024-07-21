package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.GameApplication;
//import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.adapters.CarrotAdapter;
import sk.tuke.kpi.oop.game.controllers.CustomerController;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.items.Carrot;
import sk.tuke.kpi.oop.game.items.Saw;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

import java.util.PrimitiveIterator;


public class Ripley extends AbstractActor implements Movable, Keeper, Alive, Armed, Customer {
    private Animation normalAnimation;
    private Animation diedAnimation;
    private int speed;
    //private int energy;
    //private int ammo;
    private Health health;
    private Backpack backpack;
    private Disposable movableDisposable;
    private Disposable keeperDisposable;
    private Disposable shooterDisposable;
    private Disposable customerDisposable;
    private Firearm firearm;
    private Saw saw;
    private float money;
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("Ripley died", Ripley.class);
    public Ripley(){
        super("Ellen");
        this.normalAnimation=new Animation("sprites/player.png",32,32,0.1f,Animation.PlayMode.LOOP_PINGPONG);
        this.diedAnimation=new Animation("sprites/player_die.png",32,32,0.1f,Animation.PlayMode.ONCE);
        setAnimation(normalAnimation);
        this.normalAnimation.stop();
        this.speed=2;
        //this.energy=100;
        //this.ammo=0;
        this.money=0;
        health=new Health(100);
        this.backpack=new Backpack("Ripley's backpack",10);
        firearm=new Gun(0,500);
    }

    /*public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        if(this.energy>0) {
            this.energy = Math.max(0,energy);
            if (this.energy == 0 && getScene() != null) {
                setAnimation(diedAnimation);
                getScene().getMessageBus().publish(RIPLEY_DIED, this);
                movableDisposable.dispose();
                keeperDisposable.dispose();
            }
        }
    }*/

    /*public int getAmmo() {
        return this.ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }*/

    public void showRipleyState(){
        int windowHeight = this.getScene().getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        this.getScene().getGame().getOverlay().drawText(String.valueOf("Energy: "+this.getHealth().getValue()),15,yTextPos-15);
        this.getScene().getGame().getOverlay().drawText(String.valueOf("Ammo: "+ this.getFirearm().getAmmo()),15,yTextPos-30);
        this.getScene().getGame().getOverlay().drawText(String.valueOf("Money: "+ this.getMoney()),15,yTextPos-45);
        //this.getScene().getGame().getOverlay().drawText(String.valueOf("Alien: "+ this.getScene().getFirstActorByType(Alien.class).getHealth().getValue()),15,yTextPos-45);
        this.getScene().getGame().pushActorContainer(this.getBackpack());
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public void startedMoving(Direction direction) {
        //Movable.super.startedMoving(direction);
        this.normalAnimation.setRotation(direction.getAngle());
        this.normalAnimation.play();
    }

    @Override
    public void stoppedMoving() {
        //Movable.super.stoppedMoving();
        this.normalAnimation.stop();
    }

    @Override
    public Backpack getBackpack() {
        return this.backpack;
    }

    @Override
    public Health getHealth() {
        return this.health;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        MovableController movableController=new MovableController(this);
        movableDisposable=scene.getInput().registerListener(movableController);
        KeeperController keeperController=new KeeperController(this);
        keeperDisposable=scene.getInput().registerListener(keeperController);
        ShooterController shooterController=new ShooterController(this);
        shooterDisposable=scene.getInput().registerListener(shooterController);
        CustomerController customerController=new CustomerController(this);
        customerDisposable =scene.getInput().registerListener(customerController);
        scene.follow(this);
        this.health.onExhaustion(()->{
            getScene().getMessageBus().publish(RIPLEY_DIED,this);
            setAnimation(diedAnimation);
            movableDisposable.dispose();
            keeperDisposable.dispose();
            shooterDisposable.dispose();
            customerDisposable.dispose();
            scene.cancelActions(this);});
    }

    @Override
    public Firearm getFirearm() {
        return firearm;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        firearm=weapon;
    }

    @Override
    public void buy() {
        getScene().getActors().stream().filter(actor -> actor instanceof Villager &&actor.intersects(this)).forEach(actor -> ((Villager) actor).sellItem(this));
    }

    @Override
    public void consume() {
        if(getBackpack().peek() instanceof Carrot){
            new CarrotAdapter((Carrot) getBackpack().peek()).useWith(this);

        }
    }

    @Override
    public Saw getSaw() {
        return saw;
    }

    @Override
    public void takeFormBackback() {
        if(getBackpack().peek()instanceof Saw&&this.saw==null){
            saw= (Saw) getBackpack().peek();
            getBackpack().remove(getBackpack().peek());
        }
    }

    @Override
    public void putIntoBackback() {
        if(saw!=null){
            getBackpack().add(saw);
            this.saw=null;
        }
    }

    @Override
    public void getPaid(float newMoney) {
        this.money=this.money+newMoney;
    }

    @Override
    public void pay(float giveMoney) {
        this.money=this.money-giveMoney;
    }

    @Override
    public float getMoney() {
        return this.money;
    }
}
