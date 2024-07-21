package sk.tuke.kpi.oop.game.weapons;
public abstract class Firearm {
    private int maximumShots;
    private int actualShots;

    public Firearm(int actualShots, int maximumShots){
        this.actualShots=actualShots;
        this.maximumShots=maximumShots;
    }
    public Firearm(int maximumShots){
        this(maximumShots,maximumShots);
    }
    public int getAmmo(){
        return actualShots;
    }
    public void reload(int newAmmo){
        actualShots=Math.min(actualShots+newAmmo,maximumShots);
    }
    public Fireable fire(){
        if(this.getAmmo()>0){
            if(createBullet()!=null){
                actualShots--;
            }
            return createBullet();
        }
        return null;
    }
    protected abstract Fireable createBullet();
}
