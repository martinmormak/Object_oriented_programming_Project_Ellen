package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class ChainBomb extends TimeBomb {
    public ChainBomb(float time) {
        super(time);
    }
    /*public boolean isInRadius(Actor actor){
        double position=Math.hypot((this.getPosX()+this.getWidth()/2)-actor.getPosX(), (this.getPosY()+this.getHeight()/2)-actor.getPosY());
        position=Math.min(position, Math.hypot((this.getPosX()+this.getWidth()/2)-(actor.getPosX()+actor.getWidth()), (this.getPosY()+this.getHeight()/2)-actor.getPosY()));
        position=Math.min(position, Math.hypot((this.getPosX()+this.getWidth()/2)-actor.getPosX(), (this.getPosY()+this.getHeight()/2)-(actor.getPosY()+actor.getHeight())));
        position=Math.min(position, Math.hypot((this.getPosX()+this.getWidth()/2)-(actor.getPosX()+actor.getWidth()), (this.getPosY()+this.getHeight()/2)-(actor.getPosY()+actor.getHeight())));
        if(position<=50){
            return true;
        }
        return false;
    }*/

    @Override
    public void detonate() {
        super.detonate();
        Ellipse2D.Float ellipseFloat=new Ellipse2D.Float(this.getPosX()-50+this.getWidth()/2, this.getPosY()-50+this.getHeight()/2, 100,100);//asi je potrebne zvacsit o 1 aby to intersectovalo aj vzdialenost 50
        for(Actor actor:getScene().getActors()){
            if(actor instanceof ChainBomb&&actor!=this&&!((ChainBomb) actor).isActivated()) {
                Rectangle2D.Float rectangleFloat=new Rectangle2D.Float(actor.getPosX(),actor.getPosY(),actor.getWidth(),actor.getHeight());
                if(ellipseFloat.intersects(rectangleFloat)) {//contains
                    ((ChainBomb) actor).activate();
                }
            }
        }
        /*for(Actor actor:getScene().getActors()){
            if(actor instanceof ChainBomb&&actor!=this&&isInRadius(actor)&&!((ChainBomb) actor).isActivated()) {
                ((ChainBomb) actor).activate();
            }
        }*/
    }
}
