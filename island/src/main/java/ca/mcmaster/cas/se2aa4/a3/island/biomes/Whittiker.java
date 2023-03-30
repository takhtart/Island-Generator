package ca.mcmaster.cas.se2aa4.a3.island.biomes;

import java.util.List;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a3.island.adt.IslandMesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.Tile;

public class Whittiker {
    private final String diagram;

    public Whittiker(String diagram){
        if (diagram.equals("tropical") || diagram.equals("arctic") || diagram.equals("grassland")){
            this.diagram = diagram;
        }
        else{
            this.diagram = "tropical";
            System.out.println("Invalid Diagram Selected, switching to default: \"tropical\"");
        }
        
    }
    
    public IslandMesh generatebiomes(IslandMesh aMesh){
        List<Tile> tilesWithColors = aMesh.getTilesList();
        if (diagram.equals("tropical")){
            for (Tile t : tilesWithColors) {
                if (t.getTiletype().equals("land")){
                    int elevation = t.getElevation();
                    int humidity = t.getHumidity();

                    if (elevation == 1 && humidity == 1){
                        t.setBiome(tropicalbiome.Tropical_Desert.toString());
                        t.setColor(255, 221, 102);

                    }
                    else if (elevation == 2 && humidity == 2){
                        t.setBiome(tropicalbiome.Savanna.toString());
                        t.setColor(161,95,62);

                    }
                    else if (elevation == 3 && (humidity == 2 | humidity == 3)){
                        t.setBiome(tropicalbiome.Seasonal_Forest.toString());
                        t.setColor(157, 200, 121);

                    }
                    else if (elevation == 4 && (humidity == 2 | humidity == 3)){
                        t.setBiome(tropicalbiome.Montane_Forest.toString());
                        t.setColor(109, 156, 90);

                    }
                    else if (elevation == 5 && humidity == 3){
                        t.setBiome(tropicalbiome.Tropical_Rainforest.toString());
                        t.setColor(40, 85, 44);

                    }
                    else{

                        Tile neigh = tilesWithColors.get(0);
                        for(Integer n: t.getNeighborsList()){
                            if (tilesWithColors.get(n).getTiletype().equals("land")){
                                if (!tilesWithColors.get(n).getBiome().equals("")){
                                    neigh = tilesWithColors.get(n);
                                    t.setBiome(neigh.getBiome());
                                    t.setColor(neigh.getR(),neigh.getG(),neigh.getB());
                                    break;
                                }
                                else{
                                    t.setBiome(tropicalbiome.Seasonal_Forest.toString());
                                    t.setColor(157, 200, 121);
                                }
                            }
                            
                        }
                    }


                    /* System.out.println(t.getBiome());
                    System.out.println(t.getElevation() + " " + t.getHumidity());
                    System.out.println(t.getStringColor()); */
                    
                    }

                }
                
            }
            
        
        else if (diagram.equals("arctic")){
            for (Tile t : tilesWithColors) {
                if (t.getTiletype().equals("land")){
                    int elevation = t.getElevation();
                    int humidity = t.getHumidity();

                    if (elevation == 1 && humidity == 1){
                        t.setBiome(arcticbiome.Temperate_Rainforest.toString());
                        t.setColor(23, 87, 77);

                    }
                    else if (elevation == 2 && (humidity == 2 | humidity == 3)){
                        t.setBiome(arcticbiome.Boreal_Forest.toString());
                        t.setColor(70, 112, 90);

                    }
                    else if (elevation == 3 && humidity == 2){
                        t.setBiome(arcticbiome.Taiga.toString());
                        t.setColor(83, 145, 118);

                    }
                    else if (elevation == 4 && (humidity == 1 | humidity == 2)){
                        t.setBiome(arcticbiome.Alpine_Tundra.toString());
                        t.setColor(190, 190, 190);

                    }
                    else if (elevation == 5 && humidity == 1){
                        t.setBiome(arcticbiome.Arctic_Tundra.toString());
                        t.setColor(238, 238, 238);

                    }
                    else{

                        Tile neigh = tilesWithColors.get(0);
                        for(Integer n: t.getNeighborsList()){
                            if (tilesWithColors.get(n).getTiletype().equals("land")){
                                if (!tilesWithColors.get(n).getBiome().equals("")){
                                    neigh = tilesWithColors.get(n);
                                    t.setBiome(neigh.getBiome());
                                    t.setColor(neigh.getR(),neigh.getG(),neigh.getB());
                                    break;
                                }
                                else{
                                    t.setBiome(arcticbiome.Alpine_Tundra.toString());
                                    t.setColor(190, 190, 190);
                                }
                            }
                            
                        }
                       
                    }

                    /* System.out.println(t.getBiome());
                    System.out.println(t.getElevation() + " " + t.getHumidity());
                    System.out.println(t.getStringColor()); */
                }
                
            }
            
        }
        else if (diagram.equals("grassland")){
            for (Tile t : tilesWithColors) {
                if (t.getTiletype().equals("land")){
                    int elevation = t.getElevation();
                    int humidity = t.getHumidity();

                    if (elevation == 1 && (humidity == 1 | humidity == 2)){
                        t.setBiome(grasslandbiome.Desert_Scrub.toString());
                        t.setColor(201, 134, 10);

                    }
                    else if (elevation == 2 && humidity == 2){
                        t.setBiome(grasslandbiome.Savanna.toString());
                        t.setColor(161,95,62);

                    }
                    else if (elevation == 3 && (humidity == 2 | humidity == 3)){
                        t.setBiome(grasslandbiome.Temperate_Grassland.toString());
                        t.setColor(101, 122, 56);

                    }
                    else if (elevation == 4 && (humidity == 2 | humidity == 3)){
                        t.setBiome(grasslandbiome.Tropical_Grassland.toString());
                        t.setColor(83, 102, 42);

                    }
                    else if (elevation == 5 && humidity == 3){
                        t.setBiome(grasslandbiome.Temperate_Forest.toString());
                        t.setColor(50, 64, 19);

                    }
                    else{

                        Tile neigh = tilesWithColors.get(0);
                        for(Integer n: t.getNeighborsList()){
                            if (tilesWithColors.get(n).getTiletype().equals("land")){
                                if (!tilesWithColors.get(n).getBiome().equals("")){
                                    neigh = tilesWithColors.get(n);
                                    t.setBiome(neigh.getBiome());
                                    t.setColor(neigh.getR(),neigh.getG(),neigh.getB());
                                    break;
                                }
                                else{
                                    t.setBiome(grasslandbiome.Temperate_Grassland.toString());
                                    t.setColor(101, 122, 56);
                                }
                            }
                            
                        }
                    }

                    /* System.out.println(t.getBiome());
                    System.out.println(t.getElevation() + " " + t.getHumidity());
                    System.out.println(t.getStringColor()); */
                }
                
            }

        }

        IslandMesh Mesh = new IslandMesh(aMesh.getWidth(), aMesh.getHeight(), aMesh.getCornersList(), aMesh.getEdgesList(), tilesWithColors);

        return Mesh;

    }
   
}
