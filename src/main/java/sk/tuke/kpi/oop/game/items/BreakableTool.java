package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.oop.game.characters.Ripley;

public abstract class BreakableTool <A extends Actor> extends AbstractActor implements Usable<A> {
    private int remainingUses;

    protected  BreakableTool(int remainingUses){
        this.remainingUses=remainingUses;
    }

    public int getRemainingUses() {//getNumUses
        return this.remainingUses;
    }

    @Override
    public void useWith(A actor) {
        if(remainingUses<=0||actor==null)
        {
            return;
        }
        this.remainingUses--;
        if(getRemainingUses()==0) {
            if(getScene()!=null) {
                getScene().removeActor(this);
                return;
            }
            for(Actor a:actor.getScene().getActors()){
                if(a instanceof Ripley&&((Ripley) a).getBackpack().peek()==this){
                    ((Ripley) a).getBackpack().remove((Collectible) this);
                }
            }
        }
    }

    /*public void use(){
        if(remainingUses<=0)
        {
            return;
        }
        this.remainingUses--;
        if(getNumUses()==0) {
            getScene().removeActor(this);
        }
    }*/
}
