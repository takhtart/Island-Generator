package ca.mcmaster.cas.se2aa4.a3.island.adt;

public class Edge {

    private final int c1, c2;
    private int  red, green, blue = 0;

    public Edge(int c1, int c2) {
        this.c1 = c1;
        this.c2 = c2;
    }
    
    public int getV1Idx(){
        return c1;
    }

    public int getV2Idx(){
        return c2;
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
