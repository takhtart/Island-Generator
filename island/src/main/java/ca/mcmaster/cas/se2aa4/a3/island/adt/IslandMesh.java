package ca.mcmaster.cas.se2aa4.a3.island.adt;

import java.util.List;

public class IslandMesh {
    private final double width, height;
    private final List<Corner> corners;
    private final List<Edge> edges;
    private final List<Tile> tiles;

    public IslandMesh(double width, double height,List<Corner> corners, List<Edge> edges, List<Tile> tiles) {
        this.width = width;
        this.height = height;
        this.corners = corners;
        this.edges = edges;
        this.tiles = tiles;
    }

    public double getWidth(){
        return width;
    }

    public double getHeight(){
        return height;
    }

    public Tile getTile(int i){
        return tiles.get(i);
    }

    public List<Tile> getTilesList(){
        return tiles;
    }

    public int getTilesCount(){
        return tiles.size();
    }

    public Edge getEdge(int i){
        return edges.get(i);
    }

    public List<Edge> getEdgesList(){
        return edges;
    }

    public int getEdgesCount(){
        return edges.size();
    }

    public Corner getCorner(int i){
        return corners.get(i);
    }

    public List<Corner> getCornersList(){
        return corners;
    }

    public int getCornersCount(){
        return corners.size();
    }


    
}
