package ca.mcmaster.cas.se2aa4.a3.island.soil;

import java.util.*;
import ca.mcmaster.cas.se2aa4.a3.island.adt.*;

public class MoistSoil {
    public void getHumidity(HashSet<Integer> aquiferList, List<Tile> tilesWithColors, List<Edge> edgesWithColors){
        for (Integer i:aquiferList){
            Tile aquiferTile =  tilesWithColors.get(i);
            aquiferTile.setAquifer();
            aquiferTile.addHumidity(3);

            for (Integer n: aquiferTile.getNeighborsList()){
                if (tilesWithColors.get(n).getTiletype() == "land"){
                    tilesWithColors.get(n).addHumidity(2);
                }
                if (tilesWithColors.get(n).getHumidity()>3){
                    tilesWithColors.get(n).setHumidity(3);
                }
            }
            
        }
        List<Edge> river = new ArrayList<>();
        for (Edge e: edgesWithColors){
            if (e.getEdgeType() == "river"){
                river.add(e);
            }
        }
        for (Tile t: tilesWithColors){
            if (t.getTiletype().equals("land")){
                int counter = 0;
                for (int e: t.getSegmentsList()){
                    for (Edge edge:river){
                        if (edgesWithColors.get(e) == edge){
                            counter++;
                        }
                    }
                }
                t.addHumidity(counter);
            }
            if (t.getHumidity()>3){
                t.setHumidity(3);
            }
        }
        
        for (Tile t:tilesWithColors){
            if (t.getTiletype().equals("lake")){
                for (Integer n: t.getNeighborsList()){
                    if (tilesWithColors.get(n).getTiletype().equals("land")){
                        tilesWithColors.get(n).addHumidity(2);
                    }
                    
                    if (tilesWithColors.get(n).getHumidity()>3){
                        tilesWithColors.get(n).setHumidity(3);
                    }
                }
            }
        }
    }
}
