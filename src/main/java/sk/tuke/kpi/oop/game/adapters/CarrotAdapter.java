package sk.tuke.kpi.oop.game.adapters;

import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Carrot;

public class CarrotAdapter extends Carrot {//adapter pattern
    private Carrot carrot;
    public CarrotAdapter(Carrot carrot){
        this.carrot=carrot;
    }

    public void useWith(Ripley consumer) {
        super.eat(consumer);
    }
}
