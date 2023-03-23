package ca.mcmaster.cas.se2aa4.a3.island;

import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a3.island.adt.*;


public class islandgen{
    public IslandMesh generateisland(IslandMesh aMesh){
        List<Tile> tilesWithColors = new ArrayList<>(aMesh.getTilesList());

        for (Tile t: tilesWithColors){
            if (t.getTiletype().equals("land")){
                for(Integer n: t.getNeighborsList()){
                    Tile neighbour = tilesWithColors.get(n);
                    if (neighbour.getTiletype().equals("lagoon") || neighbour.getTiletype().equals("ocean")){
                        int[] colorCode = {199,190,111};
                        neighbour.setColor(colorCode[0],colorCode[1],colorCode[2]);
                    }

                }
            }
        }

    IslandMesh Mesh = new IslandMesh(aMesh.getWidth(), aMesh.getHeight(), aMesh.getCornersList(), aMesh.getEdgesList(), aMesh.getTilesList());
    return Mesh;
}

}