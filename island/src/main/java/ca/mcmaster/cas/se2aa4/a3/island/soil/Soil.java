package ca.mcmaster.cas.se2aa4.a3.island.soil;

import java.util.*;
import ca.mcmaster.cas.se2aa4.a3.island.adt.*;
public class Soil {
    private String soil;

    public Soil(String soil){
        this.soil = soil;
    }
    public void getHumidity(HashSet<Integer> aquiferList, List<Tile> tilesWithColors, List<Edge> edgesWithColors){
        for(Tile t:tilesWithColors){
            t.setHumidity(1);
        }
        if (soil.equals("arid")){
            AridSoil soil = new AridSoil();
            soil.getHumidity(aquiferList, tilesWithColors, edgesWithColors);
        }
        if (soil.equals("normal")){
            System.out.println("test");
            NormalSoil soil = new NormalSoil();
            soil.getHumidity(aquiferList, tilesWithColors, edgesWithColors);
        }
        if (soil.equals("moist")){
            MoistSoil soil = new MoistSoil();
            soil.getHumidity(aquiferList, tilesWithColors, edgesWithColors);
        }
    }
}
