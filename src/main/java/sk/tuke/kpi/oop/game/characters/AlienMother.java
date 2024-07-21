package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class AlienMother extends Alien {
    private Animation normalAnimation=new Animation("sprites/mother.png",112,162,0.2f, Animation.PlayMode.LOOP);
    public AlienMother(){
        super(200);
        //Animation normalAnimation=new Animation("sprites/mother.png",112,162,0.2f, Animation.PlayMode.LOOP);
        setAnimation(normalAnimation);
    }
    public AlienMother(Behaviour<? super Alien> behaviour){
        super(200,behaviour);
        //Animation normalAnimation=new Animation("sprites/mother.png",112,162,0.2f, Animation.PlayMode.LOOP);
        setAnimation(normalAnimation);
    }
    public AlienMother(int health){
        super(health);
        //Animation normalAnimation=new Animation("sprites/mother.png",112,162,0.2f, Animation.PlayMode.LOOP);
        setAnimation(normalAnimation);
    }
    public AlienMother(int health, Behaviour<? super Alien> behaviour){
        super(health,behaviour);
        //Animation normalAnimation=new Animation("sprites/mother.png",112,162,0.2f, Animation.PlayMode.LOOP);
        setAnimation(normalAnimation);
    }
}
