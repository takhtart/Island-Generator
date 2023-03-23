package ca.mcmaster.cas.se2aa4.a3.island.modifiers;

import java.util.*;
import ca.mcmaster.cas.se2aa4.a3.island.adt.*;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;

public class Lake {

    private int lakes;

    public Lake(int lakes){
        this.lakes = lakes;
    }
    
    public void createLakes (List<Tile> tilesWithColors){
        
        List<Integer> lands = new ArrayList<>();
        int counter = 0;
        for (Tile t:tilesWithColors){
            if (t.getTiletype().equals("land")){
                lands.add(counter);
            } 
            counter++;
        }
        if (lakes > lands.size()){
            lakes = lands.size();
        }
        Random bag = new Random();
        List<Integer> random = new ArrayList<>();
        for (int i = 0; i<lakes; i++){
            int randInt = lands.get(bag.nextInt(lands.size()));
            for (int j = 0; j<random.size(); j++){
                if (random.get(j) == randInt){
                    randInt = lands.get(bag.nextInt(lands.size()));
                    j = -1;
                }
            }
            random.add(randInt);              
        }
        Collections.sort(random);
        //Beach
        int i = 0;
        int j = 0;
        for (Tile t: tilesWithColors){
            if (i == random.get(j)){
                String colorCode = 45 + "," + 105 + "," + 173;
                t.setColor(45, 105, 173);
                t.setTileType("ocean");
                tilesWithColors.set(i, t); 
                j++;
                if(j==random.size()){
                    break;
                }
            }
            

            i++;
        }
    }
}
