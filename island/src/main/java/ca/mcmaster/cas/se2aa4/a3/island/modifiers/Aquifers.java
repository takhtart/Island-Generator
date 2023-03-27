package ca.mcmaster.cas.se2aa4.a3.island.modifiers;

import java.util.*;
import ca.mcmaster.cas.se2aa4.a3.island.adt.*;

public class Aquifers {
    private int aquifers;

    public Aquifers(int aquifers){
        this.aquifers = aquifers;
    }

    public HashSet<Integer> createAquifers(List<Tile> tilesWithColors){
        List<Integer> landTiles = new ArrayList<>();
        HashSet<Integer> aquiferList = new HashSet<>();

        int counter = 0;
        for (Tile t: tilesWithColors){
            if (t.getTiletype().equals("land")){
                landTiles.add(counter);
            }
            counter++;
        }
        if (aquifers > landTiles.size()){
            aquifers = landTiles.size();
        }

        Random bag = new Random();

        while (aquiferList.size() < aquifers){
            int random = bag.nextInt(landTiles.size());
            aquiferList.add(landTiles.get(random));
        }


    
        return aquiferList;
    }





}
