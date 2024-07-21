package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.actions.Break;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.characters.Customer;
import sk.tuke.kpi.oop.game.characters.Egg;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.ArrayList;
import java.util.List;

public class CustomerController implements KeyboardListener {
    private Customer customer;
    private List<Input.Key> action=new ArrayList<>(){{
        add(Input.Key.T);
        add(Input.Key.E);
        add(Input.Key.Q);
        add(Input.Key.Z);
        add(Input.Key.TAB);
    };};
    public CustomerController(Customer customer){
        this.customer=customer;
    }
    @Override
    public void keyPressed(Input.Key key) {
        KeyboardListener.super.keyPressed(key);
        if(this.customer==null)return;
        if(action.contains(key)){
            switch (key){
                case T:{
                    customer.buy();
                    break;
                }
                case E:{
                    customer.consume();
                    break;
                }
                case Q:{
                    customer.takeFormBackback();
                    break;
                }
                case Z:{
                    customer.putIntoBackback();
                    break;
                }
                case TAB:{
                    new Break<>().scheduleFor(customer);
                    break;
                }
                default:{
                    break;                }
            }
        }
    }
}
