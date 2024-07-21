package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.characters.Customer;
import sk.tuke.kpi.oop.game.characters.Egg;

public class Break <C extends Customer> extends AbstractAction<C> {
    @Override
    public void execute(float deltaTime) {
        if(getActor()!=null&&/*getActor().getBackpack().getSize()>0*/getActor().getSaw()!=null) {
            getActor().getScene().getActors().stream().filter(actor->actor.intersects(getActor())&&actor instanceof Egg).forEach(actor->getActor().getSaw().destroyEgg(actor));
        }
        setDone(true);
    }
}
