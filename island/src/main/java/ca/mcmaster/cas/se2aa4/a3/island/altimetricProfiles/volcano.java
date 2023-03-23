package ca.mcmaster.cas.se2aa4.a3.island.altimetricProfiles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a3.island.adt.*;

public class volcano {
    private int levels;

    public volcano(int levels){
        this.levels = levels;
    }


    public IslandMesh setElevation(IslandMesh aMesh){
        List<Tile> tilesWithElevation = new ArrayList<>(aMesh.getTilesList());
        List<Corner> cornersWithElevation = new ArrayList<>(aMesh.getCornersList());

        //Property altitude = Property.newBuilder().setKey("altitude").setValue("0").build();


        HashSet<Corner> coasts = new HashSet<>();

        for (Tile t: tilesWithElevation){
            if (t.getTiletype().equals("land")){
                for (Integer c:t.getCorners()){
                    cornersWithElevation.get(c).setLandCorner();
                }

                for (Integer n: t.getNeighborsList()){
                    Tile neighbour = tilesWithElevation.get(n);
                    if (neighbour.getTiletype().equals("ocean")){
                        for (Integer c:t.getCorners()){
                            for (Integer d:neighbour.getCorners()){
                                if (c.equals(d)){
                                    coasts.add(aMesh.getCorner(d));
                                }                               
                            }
                        }
                    }
                }
            }
        }

        //Get Max Distance
        double maxDistance = 0;
        for (Corner c: cornersWithElevation){
            if (c.isLandCorner()){
                double minDistance = aMesh.getHeight();
                double x = c.getX();
                double y = c.getY();
                for (Corner o: coasts){
                    double coastX = o.getX();
                    double coastY = o.getY();
        
                    double distance = Math.sqrt(Math.pow(coastX - x,2) + Math.pow(coastY- y,2));
                    if (distance < minDistance){
                        minDistance = distance;
                    }
                }

                if (minDistance > maxDistance){
                    maxDistance = minDistance;
                }
            }
        }
        
        double split = maxDistance / levels;
        for (Corner c: cornersWithElevation){
            if (c.isLandCorner()){
                double minDistance = aMesh.getHeight();
                double x = c.getX();
                double y = c.getY();
                for (Corner o: coasts){
                    double coastX = o.getX();
                    double coastY = o.getY();
        
                    double distance = Math.sqrt(Math.pow(coastX - x,2) + Math.pow(coastY- y,2));
                    if (distance < minDistance){
                        minDistance = distance;
                    }

                }
                for (int i = levels; i >= 1; i--){
                    if (minDistance <= (split*i)){
                        c.setElevation(i);
                    }
                }
                /* if (minDistance <= split){
                    c.setElevation(1);
                }
                else if (minDistance <= (split*2)){
                    c.setElevation(2);
                }
                else if (minDistance <= (split*3)){
                    c.setElevation(3);
                }
                else if (minDistance <= (split*4)){
                    c.setElevation(4);
                }
                else if (minDistance <= (maxDistance)){
                    c.setElevation(5);
                } */
            }

        }
        //Tile Elevation 
        for (Tile t: tilesWithElevation){
            if (t.getTiletype().equals("land")){
                int elevationSum = 0; 
                int average = 0;
                for (Integer c:t.getCorners()){
                        elevationSum += cornersWithElevation.get(c).getElevation(); 
                }
                average = Math.round(elevationSum / t.getCorners().size());

                for (int i = levels; i >= 1; i--){
                    if (average == i){
                        t.setElevation(1);
                        t.setColor(t.getR()+(150/levels)*i,t.getG(),t.getB());
                    }
                }



                /* if (average == 1){
                    t.setElevation(1);
                    t.setColor(t.getR()+30,t.getG(),t.getB());
                }
                else if (average == 2){
                    t.setElevation(2);
                    t.setColor(t.getR()+60,t.getG(),t.getB());
                }
                else if (average == 3){
                    t.setElevation(3);
                    t.setColor(t.getR()+90,t.getG(),t.getB());
                }
                else if (average == 4){
                    t.setElevation(4);
                    t.setColor(t.getR()+120,t.getG(),t.getB());
                }
                else if (average == 5){
                    t.setElevation(5);
                    t.setColor(t.getR()+150,t.getG(),t.getB());
                } */
            }

        }


        IslandMesh Mesh = new IslandMesh(aMesh.getWidth(), aMesh.getHeight(), cornersWithElevation, aMesh.getEdgesList(), tilesWithElevation);

        return Mesh;
    
    }
    
}
