package ca.mcmaster.cas.se2aa4.a3.island;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.*;

import ca.mcmaster.cas.se2aa4.a3.island.adt.*;
import ca.mcmaster.cas.se2aa4.a3.island.altimetricProfiles.Elevation;
import ca.mcmaster.cas.se2aa4.a3.island.biomes.Whittiker;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a3.island.modifiers.*;
import ca.mcmaster.cas.se2aa4.a3.island.seed.*;
import ca.mcmaster.cas.se2aa4.a3.island.soil.*;


public class islandgen{
    public IslandMesh generateisland(Configuration config, IslandMesh aMesh){
        List<String> seed = new ArrayList<>();
        List<Tile> tilesWithColors = new ArrayList<>(aMesh.getTilesList());
        Map<String, String> options = config.export();

        if (options.containsKey(Configuration.SEED)){
            Seed seedstring = new Seed();
            String[] splitseed = seedstring.Split(options.get(Configuration.SEED), 6);
            for (int i = 0; i < splitseed.length; i++) {
                seed.add(splitseed[i]);
            }
        }
        else{
            Seed seedstring = new Seed();
            String[] splitseed = seedstring.Split(seedstring.generate(), 6);
            for (int i = 0; i < splitseed.length; i++) {
                seed.add(splitseed[i]);
            }
        }
        
        Lake lake = new Lake(Integer.parseInt(options.getOrDefault(Configuration.LAKE,"0")));
        lake.createLakes(tilesWithColors,Integer.parseInt(seed.get(0)));
        Aquifers aquifers = new Aquifers(Integer.parseInt(options.getOrDefault(Configuration.AQUIFERS,"0")));    
        HashSet <Integer> aquiferlist = aquifers.createAquifers(tilesWithColors,Integer.parseInt(seed.get(1)));
        Elevation elevation = new Elevation(options.getOrDefault(Configuration.ALTITUDE,"volcano"));
        aMesh = elevation.create(aMesh);
        River river = new River(Integer.parseInt(options.getOrDefault(Configuration.RIVERS,"0")));
        aMesh = river.createRivers(aMesh,Integer.parseInt(seed.get(2)));
        Soil soil = new Soil(options.getOrDefault(Configuration.SOIL,"normal"));
        soil.getHumidity(aquiferlist, tilesWithColors, aMesh.getEdgesList());
        Whittiker biome = new Whittiker(options.getOrDefault(Configuration.BIOME,"tropical"));
        aMesh = biome.generatebiomes(aMesh);
        System.out.println("Seed: " + seed.get(0) + seed.get(1) + seed.get(2));
        


    IslandMesh Mesh = new IslandMesh(aMesh.getWidth(), aMesh.getHeight(), aMesh.getCornersList(), aMesh.getEdgesList(), aMesh.getTilesList());
    return Mesh;




}

}