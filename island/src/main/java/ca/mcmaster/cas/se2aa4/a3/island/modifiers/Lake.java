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
        if (lakes > Math.sqrt(lands.size())){
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
                    continue;
                }
                for (int a: tilesWithColors.get(randInt).getNeighborsList()){
                    if (a == random.get(j) || tilesWithColors.get(a).getTiletype().equals("ocean") || tilesWithColors.get(a).getTiletype().equals("lake") || tilesWithColors.get(a).getTiletype().equals("lagoon")){
                        randInt = lands.get(bag.nextInt(lands.size()));
                        j = -1;
                        break;
                    }
                }
            }
            random.add(randInt);              
        }
        Collections.sort(random);
        
        int i = 0;
        int j = 0;
        for (Tile t: tilesWithColors){
            if(lakes == 0){
                break;
            }
            if (i == random.get(j)){
                t.setColor(45, 105, 173);
                t.setTileType("lake");
                t.setMarked();
                tilesWithColors.set(i, t); 

                j++;
                if(j==random.size()){
                    break;
                }
            }
            

            i++;
        }
        for (int n = 0; n<random.size();n++){
            int rand = bag.nextInt(10);
            if(rand == 0){
                continue;
            }
            for (int a: tilesWithColors.get(random.get(n)).getNeighborsList()){
                Tile tile = tilesWithColors.get(a);
                if (tile.getTiletype().equals("land") && tile.getElevation() == tilesWithColors.get(random.get(n)).getElevation()){
                    boolean overlaps = false;
                    if (tile.getTiletype().equals("lagoon") || tile.getTiletype().equals("ocean") ||(tile.getTiletype().equals("lake")&& tile.isMarked())){
                        break;
                    }
                     
                        for (int b: tilesWithColors.get(a).getNeighborsList()){
                            boolean overlap = false;
                            Tile c = tilesWithColors.get(b);
                            for (int d: tilesWithColors.get(b).getNeighborsList()){
                                Tile e = tilesWithColors.get(d);
                                if (e.getTiletype().equals("lagoon") || e.getTiletype().equals("ocean") || (e.getTiletype().equals("lake")&& e.isMarked())){
                                    overlap = true;
                                    break;
                                }
                            }
                            if (c.getTiletype().equals("lagoon") || c.getTiletype().equals("ocean") || (c.getTiletype().equals("lake")&& c.isMarked() && c!=tilesWithColors.get(random.get(n)))){
                                overlaps = true;
                                break;
                            }
                            if (c.getTiletype().equals("land") && c.getElevation() == tilesWithColors.get(random.get(n)).getElevation() && !overlap && rand > 4){
                                c.setColor(45, 105, 173);
                                c.setTileType("lake");
                                tilesWithColors.set(a,c);
                            }
                        }
                    if (!overlaps){
                        tile.setColor(45, 105, 173);
                        tile.setTileType("lake");
                        tilesWithColors.set(a,tile);
                    }
                }
            }
            for(Tile t: tilesWithColors){
                if (t.getTiletype().equals("lake")){
                    t.setMarked();
                }
            }
        }
        
    }
}
