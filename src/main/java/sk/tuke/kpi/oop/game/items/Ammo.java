package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Ammo extends AbstractActor implements Usable<Armed>{
    public Ammo(){
        Animation normalAnimation;
        normalAnimation=new Animation("sprites/ammo.png",16,16);
        setAnimation(normalAnimation);
    }

    public void controlAmmo() {
        //getScene().getActors().stream().filter(actor->actor instanceof Ripley &&this.intersects(actor)).forEach(actor->new Use<>(this).scheduleFor((Ripley) actor));
        getScene().getActors().stream().filter(actor->actor instanceof Ripley &&super.intersects(actor)).forEach(actor->this.useWith((Ripley) actor));
    }

    @Override
    public void useWith(Armed armed) {
        if(armed==null)return;
        if(armed.getFirearm().getAmmo()<500) {
            armed.getFirearm().reload(50);
            getScene().removeActor(this);
        }
    }

    @Override
    public Class<Armed> getUsingActorClass() {
        return Armed.class;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::controlAmmo)).scheduleFor(this);
    }
}
