package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

public class RandomlyMoving implements Behaviour<Movable>{
    private Move<Movable> move;
    public void loopRandomMove(Movable actor){
        Direction direction;
        if(move==null){
            do {
                direction=Direction.values()[(int) (Math.random()*9)];
            }while (direction==Direction.NONE);
            this.move=new Move<>(direction, (float) (Math.random()*5));
            move.scheduleFor(actor);
        }
        if(move.isDone()){
            do {
                direction=Direction.values()[(int) (Math.random()*9)];
            }while (direction==Direction.NONE);
            this.move=new Move<>(Direction.values()[(int) (Math.random()*9)], (float) (Math.random()*5));
            move.scheduleFor(actor);
        }
    }
    @Override
    public void setUp(Movable actor) {
        if(actor==null)return;
        new Loop<>(new Invoke<>(this::loopRandomMove)).scheduleFor(actor);
    }
}
