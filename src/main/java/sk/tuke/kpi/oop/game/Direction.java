package sk.tuke.kpi.oop.game;

public enum Direction {
    NONE(0,0),
    NORTH(0,1),
    EAST(1,0),
    SOUTH(0,-1),
    WEST(-1,0),
    NORTHEAST(1,1),
    NORTHWEST(-1,1),
    SOUTHEAST(1,-1),
    SOUTHWEST(-1,-1);
    private int dx;
    private int dy;
    Direction(int dx, int dy){
        this.dx=dx;
        this.dy=dy;
    }
    public float getAngle(){
        //return (float) (Math.toDegrees(Math.atan2(getDy(), getDx()))-90);
        if (this == NORTH) {
            return 0;
        } else if (this == SOUTH) {
            return 180;
        } else if (this == WEST) {
            return 90;
        } else if (this == EAST) {
            return 270;
        } else if (this == NORTHEAST) {
            return 315;
        } else if (this == NORTHWEST) {
            return 45;
        } else if (this == SOUTHEAST) {
            return 225;
        } else if (this == SOUTHWEST) {
            return 135;
        } else {
            return 0;
        }
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public Direction combine(Direction other){
        if (this==other)return this;
        int combineY=this.getDy()+other.getDy();
        int combineX=this.getDx()+other.getDx();
        if(combineY>1){
            combineY=1;
        }else if (combineY<-1){
            combineY=-1;
        }
        if(combineX>1){
            combineX=1;
        }else if (combineX<-1){
            combineX=-1;
        }
        for(Direction direction:Direction.values()){
            if(direction.getDy()==combineY&&direction.getDx()==combineX){
                return direction;
            }
        }
        return NONE;
    }
    public static Direction fromAngle(float angle){
        /*while (angle>360){
            angle=angle-360;
        }
        while (angle<0){
            angle=angle+360;
        }*/
        if (angle == 0.0) {
            return NORTH;
        } else if (angle == 180.0) {
            return SOUTH;
        } else if (angle == 90.0) {
            return WEST;
        } else if (angle == 270.0) {
            return EAST;
        } else if (angle == 315.0) {
            return NORTHEAST;
        } else if (angle == 45.0) {
            return NORTHWEST;
        } else if (angle == 225.0) {
            return SOUTHEAST;
        } else if (angle == 135.0) {
            return SOUTHWEST;
        } else {
            return NONE;
        }
    }
}
