package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Take <K extends Keeper> extends AbstractAction<K> {
    @Override
    public void execute(float deltaTime) {
        if(getActor()!=null) {
            for(Actor actor:getActor().getScene().getActors()){
                if(actor instanceof Collectible&& getActor().intersects(actor)){
                    try {
                        getActor().getBackpack().add((Collectible) actor);
                        getActor().getScene().removeActor(actor);
                    } catch (Exception ex) {
                        getActor().getScene().getOverlay().drawText(ex.getMessage(), 0, 0).showFor(2);
                    }
                }
            }
        }
        setDone(true);
    }
}
