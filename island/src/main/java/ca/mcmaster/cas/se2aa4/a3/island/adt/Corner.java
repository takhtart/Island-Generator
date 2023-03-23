package ca.mcmaster.cas.se2aa4.a3.island.adt;

public class Corner {
    private final double x, y;
    private int  red, green, blue = 0;
    private int elevation;
    private boolean landCorner = false;

    public Corner(double x, double y) {
        this.x = x;
        this.y = y;

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setElevation(int value){
        this.elevation = value;
    }
    
    public int getElevation(){
        return elevation;
    }

    public void setLandCorner(){
        this.landCorner = true;
    }

    public boolean isLandCorner(){
        return landCorner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Corner corner = (Corner) o;
        return x == corner.x && y == corner.y;
    }

    public void setColor(int r, int g, int b){
        if (r >= 0 && r <= 255 && g >= 0 && g <= 255 && b >= 0 && b <= 255){
            this.red = r;
            this.green = g;
            this.blue = b;
        }
    }

    public String getStringColor(){
        return red + "," + green + "," + blue;
    }

}


