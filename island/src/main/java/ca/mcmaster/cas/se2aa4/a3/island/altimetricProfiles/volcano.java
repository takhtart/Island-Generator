package ca.mcmaster.cas.se2aa4.a3.island.altimetricProfiles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a3.island.adt.*;


public class volcano{
    public static IslandMesh setElevation(IslandMesh aMesh){
        List<Tile> tilesWithElevation = new ArrayList<>(aMesh.getTilesList());
        List<Corner> cornersWithElevation = new ArrayList<>(aMesh.getCornersList());

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
        
        double split = maxDistance / 5;
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
                for (int i = 5; i >= 1; i--){
                    if (minDistance <= (split*i)){
                        c.setElevation(i);
                    }
                }
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

                for (int i = 5; i >= 1; i--){
                    if (average == i){
                        t.setElevation(i);
                    }
                }

            }

        }


        IslandMesh Mesh = new IslandMesh(aMesh.getWidth(), aMesh.getHeight(), cornersWithElevation, aMesh.getEdgesList(), tilesWithElevation);

        return Mesh;
    
    }
    
}
