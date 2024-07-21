package sk.tuke.kpi.oop.game.weapons;

public class Gun extends Firearm{
    public Gun(int initialShots, int maximumShots) {
        super(initialShots, maximumShots);
    }

    public Gun(int maximumShots) {
        super(maximumShots);
    }

    @Override
    protected Fireable createBullet() {
        return new Bullet();
    }
}
