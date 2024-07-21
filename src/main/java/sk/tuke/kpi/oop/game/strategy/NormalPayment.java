package sk.tuke.kpi.oop.game.strategy;

public class NormalPayment implements PaymentStrategy{
    @Override
    public float givePayment(float value) {
        return value;
    }
}
