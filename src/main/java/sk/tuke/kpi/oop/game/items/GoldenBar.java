package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Customer;
import sk.tuke.kpi.oop.game.characters.Egg;
import sk.tuke.kpi.oop.game.proxy.ProxyStrategy;
import sk.tuke.kpi.oop.game.strategy.NormalPayment;
import sk.tuke.kpi.oop.game.strategy.PaymentStrategy;

public class GoldenBar extends AbstractActor implements Usable<Customer> {//prototype pattern
    private float nominalValue;
    private PaymentStrategy paymentStrategy;
    public GoldenBar(float nominalValue){
        Animation normalAnimation = new Animation("sprites/golden_bar.png",18,5);
        setAnimation(normalAnimation);
        this.nominalValue= nominalValue;
        paymentStrategy=new NormalPayment();
    }
    public void setPaymentStrategy(PaymentStrategy paymentStrategy){
        this.paymentStrategy=paymentStrategy;
    }

    public GoldenBar(GoldenBar goldenBar){
        setAnimation(goldenBar.getAnimation());
        nominalValue= goldenBar.getNominalValue();
        paymentStrategy=new ProxyStrategy();
    }
    public GoldenBar clone(){
        return new GoldenBar(this);
    }

    public void controlGoldenBar() {
        for(Actor actor:getScene().getActors()){
            if(actor instanceof Customer &&super.intersects(actor)) {
                //new Use<>(this).scheduleFor((Customer) actor);
                this.useWith((Customer) actor);
            }
        }
    }

    public float getNominalValue() {
        return nominalValue;
    }

    @Override
    public void useWith(Customer customer) {
        if(customer==null)return;
        customer.getPaid(paymentStrategy.givePayment(getNominalValue()));
        getScene().removeActor(this);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::controlGoldenBar)).scheduleFor(this);
        Egg.getEgg().addSubscriber(this);
    }

    @Override
    public Class<Customer> getUsingActorClass() {
        return null;
    }
}
