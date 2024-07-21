package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.actions.Move;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovableController implements KeyboardListener {
    private Movable movable;
    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.RIGHT, Direction.EAST),
        Map.entry(Input.Key.LEFT, Direction.WEST));
    private Move<Movable> move;
    private Set<Direction> directions;
    public MovableController(Movable movable)
    {
        this.movable=movable;
        this.directions=new HashSet<>();
    }
    public Direction updateDirection(){
        Direction direction=Direction.NONE;
        for(Direction actualDirection:directions){
            direction=direction.combine(actualDirection);
            if(direction==Direction.NONE){
                return direction;
            }
        }
        return direction;
    }

    @Override
    public void keyPressed(Input.Key key) {//Input.@NotNull Key key
        KeyboardListener.super.keyPressed(key);
        if(this.movable==null)return;
        if(keyDirectionMap.containsKey(key)) {
            if(move!=null) {
                move.stop();
            }
            directions.add(keyDirectionMap.get(key));
            Direction direction=updateDirection();
            if(direction!=Direction.NONE) {
                this.move = new Move<Movable>(direction, 1000);
                move.scheduleFor(movable);
            }
        }
    }

    @Override
    public void keyReleased(Input.Key key) {//Input.@NotNull Key key
        KeyboardListener.super.keyReleased(key);
        if(this.movable==null)return;
        if(keyDirectionMap.containsKey(key)){
            directions.remove(keyDirectionMap.get(key));
            if(move==null)return;
            if(directions.isEmpty()==true) {
                move.reset();
                move.stop();
            }
            else{
                Direction direction=updateDirection();
                if(direction!=Direction.NONE) {
                    move.reset();
                    move.stop();
                    this.move = new Move<Movable>(direction, 1000);
                    move.scheduleFor(movable);
                }
            }
        }
    }
}
