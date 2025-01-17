package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.DefectiveLight;

public class Wrench extends BreakableTool<DefectiveLight> implements Collectible {
    //private Animation wrenchAnimation;
    public Wrench() {
        super(2);
        Animation wrenchAnimation;
        wrenchAnimation=new Animation("sprites/wrench.png",16,16);//wrong width and height
        setAnimation(wrenchAnimation);
    }
    @Override
    public void useWith(DefectiveLight light){
        if(light==null)return;
        if(light.repair()){super.useWith(light);}
    }

    @Override
    public Class<DefectiveLight> getUsingActorClass() {
        return DefectiveLight.class;
    }
}
