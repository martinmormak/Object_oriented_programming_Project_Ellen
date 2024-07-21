package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;
import sk.tuke.kpi.oop.game.items.Carrot;

public class Serf extends AbstractActor implements Villager {
    Behaviour<? super Villager> behaviour;
    public Serf(){
        Animation normalAnimation = new Animation("sprites/serf.png",22,17);
        setAnimation(normalAnimation);
    }

    @Override
    public void sellItem(Customer customer) {
        if(customer.getMoney()<10)return;
        ((Ripley)customer).getBackpack().add(new Carrot());
        customer.pay(10);
    }

    @Override
    public void setBehaviour(Behaviour<? super Villager> behaviour) {
        this.behaviour=behaviour;
        this.behaviour.setUp(this);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        Egg.getEgg().addSubscriber(this);
    }
}
