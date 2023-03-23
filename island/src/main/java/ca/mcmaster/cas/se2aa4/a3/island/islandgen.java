package ca.mcmaster.cas.se2aa4.a3.island;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcmaster.cas.se2aa4.a3.island.adt.*;
import ca.mcmaster.cas.se2aa4.a3.island.altimetricProfiles.volcano;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a3.island.modifiers.*;


public class islandgen{
    public IslandMesh generateisland(Configuration config, IslandMesh aMesh){
        List<Tile> tilesWithColors = new ArrayList<>(aMesh.getTilesList());
        Map<String, String> options = config.export();
        
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
        Lake lake = new Lake(Integer.parseInt(options.getOrDefault(Configuration.LAKE,"0")));
        lake.createLakes(tilesWithColors);
        volcano volcano = new volcano(Integer.parseInt(options.getOrDefault(Configuration.ELEVATIONLEVEL,"5")));
        aMesh = volcano.setElevation(aMesh);


    IslandMesh Mesh = new IslandMesh(aMesh.getWidth(), aMesh.getHeight(), aMesh.getCornersList(), aMesh.getEdgesList(), aMesh.getTilesList());
    return Mesh;




}

}