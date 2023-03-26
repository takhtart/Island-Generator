package ca.mcmaster.cas.se2aa4.a3.island.adt;

import java.util.List;

public class Tile {
    private final int centroid;
    private final List<Integer> edge, neighbor, neighbortile, corners;
    private int red,green,blue = 255;
    private String tiletype;
    private int elevation = 1;
    private boolean aquifer = false;
    private int humidity = 1;
    private boolean marked;
    private String biome;

    public Tile(List<Integer> edge, List<Integer> neighbor, int centroid, List<Integer> neighbortile, List<Integer> corners) {
        this.edge = edge;
        this.neighbor = neighbor;
        this.centroid = centroid;
        this.neighbortile = neighbortile;
        this.corners = corners;
    }


    public void setColor(int r, int g, int b){
        if (r >= 0 && r <= 255 && g >= 0 && g <= 255 && b >= 0 && b <= 255){
            this.red = r;
            this.green = g;
            this.blue = b;
        }
    }

    public int getR(){
        return red;
    }
    public int getG(){
        return green;
    }
    public int getB(){
        return blue;
    }
    public void setMarked(){
        this.marked = true;
    }
    public void setUnmarked(){
        this.marked = false;
    }
    public void setBiome(String b){
        this.biome = b;
    }
    public boolean isMarked(){
        return marked;
    }
    public void setTileType(String type){
        this.tiletype = type;
    }

    public void setElevation(int level){
        this.elevation = level;
    }
    public int getElevation(){
        return elevation;
    }
    public String getStringColor(){
        return red + "," + green + "," + blue;
    }
    
    public int getCentroidIdx() {
        return centroid;
    }

    public String getBiome() {
        return biome;
    }

    public List<Integer> getNeighborsList() {
        return neighbor;
    }

    public List<Integer> getSegmentsList() {
        return edge;
    }

    public int getNeighborsCount(){
        return neighbor.size();
    }

    public int getSegmentsCount(){
        return edge.size();
    }

    public List<Integer> getNeighboringTiles(){
        return neighbortile;
    }

    public List<Integer> getCorners(){
        return corners;
    }

    public String getTiletype(){
        return tiletype;
    }

    public void setAquifer(){
        this.aquifer = true;
    }

    public boolean isAquifer(){
        return aquifer;
    }

    public int getHumidity(){
        return humidity;
    }
    public void setHumidity(int humidity){
        this.humidity = humidity;
    }
    public void addHumidity(int add){
        this.humidity = humidity + add;
    }

}