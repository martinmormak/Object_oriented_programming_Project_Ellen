package sk.tuke.kpi.oop.game.proxy;

import sk.tuke.kpi.oop.game.strategy.NormalPayment;
import sk.tuke.kpi.oop.game.strategy.PaymentStrategy;

public class ProxyStrategy implements PaymentStrategy {//proxy pattern (virtual proxy)
    PaymentStrategy paymentStrategy;
    public ProxyStrategy(){
        paymentStrategy=null;
    }
    @Override
    public float givePayment(float value) {
        if(paymentStrategy==null)paymentStrategy=new NormalPayment();
        return paymentStrategy.givePayment(value);
    }
}
