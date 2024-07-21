package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Teleport extends AbstractActor {
    //private Animation normalAnimation;
    private Teleport destination;
    private List<Player> teleportedPlayers;

    public Teleport(){
        Animation normalAnimation;
        normalAnimation=new Animation("sprites/lift.png",48,48);
        setAnimation(normalAnimation);
        destination=null;
        this.teleportedPlayers =new ArrayList<>();
    }

    public Teleport(Teleport destination){
        Animation normalAnimation;
        normalAnimation=new Animation("sprites/lift.png",48,48);
        setAnimation(normalAnimation);
        this.destination=destination;
        this.teleportedPlayers =new ArrayList<>();
    }

    public Teleport getDestination() {
        return destination;
    }

    public void setDestination(Teleport destination) {

        if(destination!=this){this.destination = destination;}
    }
    public boolean isInRadius(Player player){
        if((player.getPosX()+player.getWidth()/2)>this.getPosX()&&(player.getPosY()+player.getHeight()/2)>this.getPosY()&&
            (player.getPosX()+player.getWidth()/2)<this.getPosX()+this.getWidth()&&(player.getPosY()+player.getHeight()/2)<this.getPosY()+this.getHeight()){
            return true;
        }
        return false;
    }
    public void controlTeleport() {
        for(Actor actor:getScene().getActors()){
            if(actor instanceof Player&&isInRadius((Player) actor)&&this.getDestination()!=null&&!isInList((Player) actor)) {
                this.getDestination().teleportPlayer((Player) actor);
            }
        }
    }
    public void controlEnteredPlayers(){
        /*List<Player> copy =new ArrayList<>();
        copy.removeAll(copy);
        copy.addAll(teleportedPlayers);
        for(Player player:copy) {
            if(!isInRadius(player)){
                teleportedPlayers.remove(player);
            }
        }*/
        this.teleportedPlayers = this.teleportedPlayers.stream().filter(p -> this.isInRadius(p)).collect(Collectors.toList());
    }
    public boolean isInList(Player player){
        for(Player pla: teleportedPlayers) {
            if(pla==player){
                return true;
            }
        }
        return false;
    }

    public void teleportPlayer(Player player) {
        /*if(isInRadius(player)&&this.destination!=null&&!isInList(player))
        {
            destination.teleportedPlayers.add(player);
            player.setPosition((this.destination.getPosX()+this.destination.getWidth()/2)-player.getWidth()/2,(this.destination.getPosY()+this.destination.getHeight()/2)-player.getHeight()/2);
        }*/
        this.teleportedPlayers.add(player);
        player.setPosition((this.getPosX()+this.getWidth()/2)-player.getWidth()/2,(this.getPosY()+this.getHeight()/2)-player.getHeight()/2);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::controlTeleport)).scheduleFor(this);
        new Loop<>(new Invoke<>(this::controlEnteredPlayers)).scheduleFor(this);
    }
}
