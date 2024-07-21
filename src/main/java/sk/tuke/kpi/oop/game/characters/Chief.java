package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;
import sk.tuke.kpi.oop.game.items.ChainSaw;
import sk.tuke.kpi.oop.game.items.HandSaw;

public class Chief extends AbstractActor implements Villager{
    Behaviour<? super Villager> behaviour;

    public Chief() {
        Animation normalAnimation = new Animation("sprites/chief.png",22,17);
        setAnimation(normalAnimation);
    }

    @Override
    public void sellItem(Customer customer) {
        if(customer.getMoney()>50){
            ((Ripley) customer).getBackpack().add(new ChainSaw());
            customer.pay(50);
        }else if (customer.getMoney()>25) {
            ((Ripley) customer).getBackpack().add(new HandSaw());
            customer.pay(25);
        }
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
