package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;

public class Drop <K extends Keeper> extends AbstractAction<K> {
    @Override
    public void execute(float deltaTime) {
        if(getActor()!=null&&/*getActor().getBackpack().getSize()>0*/getActor().getBackpack().peek()!=null) {
            getActor().getScene().addActor(getActor().getBackpack().peek(), getActor().getPosX() + getActor().getWidth() / 2 - getActor().getBackpack().peek().getWidth() / 2, getActor().getPosY() + getActor().getHeight() / 2 - getActor().getBackpack().peek().getHeight() / 2);
            getActor().getBackpack().remove(getActor().getBackpack().peek());
        }
        setDone(true);
    }
}
