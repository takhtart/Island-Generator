package ca.mcmaster.cas.se2aa4.a3.island;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.*;

import ca.mcmaster.cas.se2aa4.a3.island.adt.*;
import ca.mcmaster.cas.se2aa4.a3.island.altimetricProfiles.ElevationBuildable;
import ca.mcmaster.cas.se2aa4.a3.island.altimetricProfiles.volcano;
import ca.mcmaster.cas.se2aa4.a3.island.biomes.Whittiker;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a3.island.modifiers.*;
import ca.mcmaster.cas.se2aa4.a3.island.soil.*;


public class islandgen{
    public IslandMesh generateisland(Configuration config, IslandMesh aMesh){
        List<Tile> tilesWithColors = new ArrayList<>(aMesh.getTilesList());
        Map<String, String> options = config.export();
        
       /*  for (Tile t: tilesWithColors){
            if (t.getTiletype().equals("land")){
                for(Integer n: t.getNeighborsList()){
                    Tile neighbour = tilesWithColors.get(n);
                    if (neighbour.getTiletype().equals("lagoon") || neighbour.getTiletype().equals("ocean")){
                        int[] colorCode = {199,190,111};
                        neighbour.setColor(colorCode[0],colorCode[1],colorCode[2]);
                    }

                }
            }
        } */
        Lake lake = new Lake(Integer.parseInt(options.getOrDefault(Configuration.LAKE,"0")));
        lake.createLakes(tilesWithColors);
        Aquifers aquifers = new Aquifers(Integer.parseInt(options.getOrDefault(Configuration.AQUIFERS,"0")));

        HashSet <Integer> aquiferlist = aquifers.createAquifers(tilesWithColors);
        volcano volcano = new volcano(Integer.parseInt(options.getOrDefault(Configuration.ELEVATIONLEVEL,"5")));
        aMesh = volcano.setElevation(aMesh);
        River river = new River(Integer.parseInt(options.getOrDefault(Configuration.RIVERS,"5")));
        aMesh = river.createRivers(aMesh);
        NormalSoil soil = new NormalSoil();
        soil.getHumidity(aquiferlist, tilesWithColors, aMesh.getEdgesList());
        Whittiker biome = new Whittiker(options.getOrDefault(Configuration.BIOME,"tropical"));
        aMesh = biome.generatebiomes(aMesh);

        //Aquifers Test
        /* for (Tile t:aMesh.getTilesList()){
            if (t.getHumidity() == 1){
                int[] colorCode = {137, 207, 240};
                t.setColor(colorCode[0],colorCode[1],colorCode[2]);
            }
            else if (t.getHumidity() > 1){
                int[] colorCode = {137+30, 207, 240};
                t.setColor(colorCode[0],colorCode[1],colorCode[2]);
            }
            if (t.isAquifer()){
                int[] colorCode = {0,255,255};
                t.setColor(colorCode[0],colorCode[1],colorCode[2]);
            }
        } */


    IslandMesh Mesh = new IslandMesh(aMesh.getWidth(), aMesh.getHeight(), aMesh.getCornersList(), aMesh.getEdgesList(), aMesh.getTilesList());
    return Mesh;




}

}