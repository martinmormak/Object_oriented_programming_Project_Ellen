package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Backpack implements ActorContainer<Collectible> {
    private String name;
    private int capacity;
    private int size;
    private Stack<Collectible> content;
    public Backpack(String name, int capacity){
        this.name=name;
        this.capacity=capacity;
        this.size=0;
        content=new Stack<>();
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public @NotNull List<Collectible> getContent() {
        return List.copyOf(this.content);
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public void add(@NotNull Collectible actor) {
        if(size<capacity){
            //this.content.set(size, (Collectible) actor);
            this.content.push(actor);
            size++;
        }else{
            throw new IllegalStateException(this.getName()+" is full");
        }
    }

    @Override
    public void remove(@NotNull Collectible actor) {
        if(size>0&&this.content.contains(actor)){//contains is not necessary because is in remove function
            this.content.remove(actor);
            size--;
        }
    }

    @NotNull
    @Override
    public Iterator<Collectible> iterator() {
        //return null;
        /*for (Collectible item : this) {
            return (Iterator<Collectible>) item;
        }*/
        //return (Iterator<Collectible>) this;
        /*return new  Iterator<Collectible>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Collectible next() {
                return null;
            }
        };*/
        return this.content.iterator();
    }

    @Override
    public @Nullable Collectible peek() {
        if(this.size>0) {
            return this.content.peek();
        }
        return null;
    }

    @Override
    public void shift() {
        if(this.getSize()>0) {
            Collectible last = (Collectible) this.peek();
            this.remove(this.peek());
            Backpack backpack = new Backpack("buffer", this.getCapacity());
            while (this.size > 0) {
                backpack.add(this.peek());
                this.remove(this.peek());
            }
            this.add(last);
            while (backpack.getSize() > 0) {
                this.add(backpack.peek());
                backpack.remove(backpack.peek());
            }
        }
    }
}
