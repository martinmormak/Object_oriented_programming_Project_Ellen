package sk.tuke.kpi.oop.game.characters;

import java.util.ArrayList;
import java.util.List;

public class Health {
    @FunctionalInterface
    public interface ExhaustionEffect {
        void apply();
    }
    private int actualHealth;
    private int maximalHealth;
    private List<ExhaustionEffect> list;
    public Health(int actualHealth, int maximalHealth){
        this.actualHealth=actualHealth;
        this.maximalHealth=maximalHealth;
        this.list = new ArrayList<>();
    }
    public Health(int maximalHealth){
        this(maximalHealth,maximalHealth);
    }
    public int getValue(){
        return this.actualHealth;
    }
    public void refill(int amount){
        this.actualHealth=Math.min(this.actualHealth+amount,this.maximalHealth);
    }
    public void restore(){
        this.refill(this.maximalHealth);
    }
    public void drain(int amount){
        if(actualHealth<=0)return;
        this.actualHealth=Math.max(this.actualHealth-amount,0);
        if(this.actualHealth==0){
            list.stream().forEach(actor->actor.apply());
        }
    }
    public void exhaust(){
        this.drain(this.maximalHealth);
    }

    public void onExhaustion(ExhaustionEffect effect) {
        this.list.add(effect);
    }
}
