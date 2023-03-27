package ca.mcmaster.cas.se2aa4.a3.island.adt;

public class Edge {

    private final int c1, c2;
    private int  red, green, blue = 0;
    private boolean marked;
    private String edgeType;
    private int thickness;

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

    public void setEdgeType(String type){
        this.edgeType = type;
    }

    public String getEdgeType(){
        return edgeType;
    }

    public void setMarked(){
        this.marked = true;
    }
    public void setUnmarked(){
        this.marked = false;
    }
    public boolean isMarked(){
        return marked;
    }

    public void setColor(int r, int g, int b){
        if (r >= 0 && r <= 255 && g >= 0 && g <= 255 && b >= 0 && b <= 255){
            this.red = r;
            this.green = g;
            this.blue = b;
        }
    }
    public void setThickness(int thickness){
        this.thickness = thickness;
    }
    public int getThickness(){
        return thickness;
    }
    public String getStringColor(){
        return red + "," + green + "," + blue;
    }

}
