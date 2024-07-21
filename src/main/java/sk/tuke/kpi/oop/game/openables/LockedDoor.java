package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Actor;

public class LockedDoor extends Door{
    private boolean status;
    public LockedDoor(String name, Orientation orientation){
        super(name,orientation);
        status=true;
    }
    public LockedDoor(Orientation orientation){
        super(orientation);
        status=true;
    }

    @Override
    public void useWith(Actor actor) {
        if(isLocked()==false) {
            super.useWith(actor);
        }
    }

    public void lock(){
        status=true;
    }
    public void unlock(){
        status=false;
    }
    public boolean isLocked(){
        return status;
    }
}
