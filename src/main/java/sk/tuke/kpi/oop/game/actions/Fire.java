package sk.tuke.kpi.oop.game.actions;

//import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;

public class Fire<A extends Armed> extends AbstractAction<A> {
    public Fire(){
    }

    @Override
    public void execute(float deltaTime) {
        if(getActor()!=null&&getActor().getFirearm().getAmmo()>0) {
            Fireable fireable = getActor().getFirearm().fire();
            if (fireable == null) return;
            int posX = 0;
            int posY = 0;
            //done=true;
            switch (Direction.fromAngle(getActor().getAnimation().getRotation())) {
                case NORTH: {
                    posX = getActor().getPosX() + getActor().getWidth() / 2 - fireable.getWidth() / 2;
                    posY = getActor().getPosY() + getActor().getHeight();
                    break;
                }
                case SOUTH: {
                    posX = getActor().getPosX() + getActor().getWidth() / 2 - fireable.getWidth() / 2;
                    posY = getActor().getPosY() - fireable.getHeight();
                    break;
                }
                case WEST: {
                    posX = getActor().getPosX() - fireable.getWidth();
                    posY = getActor().getPosY() + getActor().getHeight() / 2 - fireable.getHeight() / 2;
                    break;
                }
                case EAST: {
                    posX = getActor().getPosX() + getActor().getWidth();
                    posY = getActor().getPosY() + getActor().getHeight() / 2 - fireable.getHeight() / 2;
                    break;
                }
            /*case NORTHEAST:{
                posX=getActor().getPosX()+getActor().getWidth();
                posY=getActor().getPosY()+getActor().getHeight();
                break;
            }
            case NORTHWEST:{
                posX=getActor().getPosX()-fireable.getWidth();
                posY=getActor().getPosY()+getActor().getHeight();
                break;
            }
            case SOUTHEAST:{
                posX=getActor().getPosX()+getActor().getWidth();
                posY=getActor().getPosY();
                break;
            }
            case SOUTHWEST:{
                posX=getActor().getPosX()-fireable.getWidth();
                posY=getActor().getPosY();
                break;
            }*/
                default: {
                    break;
                }
            }
            getActor().getScene().addActor((Actor) fireable, posX, posY);
            new Move<Movable>(Direction.fromAngle(getActor().getAnimation().getRotation()), 1000).scheduleFor(fireable);
        }
        setDone(true);
    }
}
