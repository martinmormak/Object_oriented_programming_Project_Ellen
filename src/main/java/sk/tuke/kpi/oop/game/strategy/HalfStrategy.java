package sk.tuke.kpi.oop.game.strategy;

public class HalfStrategy implements PaymentStrategy{
    @Override
    public float givePayment(float value) {
        return (float) (value*0.5);
    }
}
