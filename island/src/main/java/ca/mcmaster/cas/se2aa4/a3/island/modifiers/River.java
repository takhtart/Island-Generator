package ca.mcmaster.cas.se2aa4.a3.island.modifiers;

import java.util.*;
import ca.mcmaster.cas.se2aa4.a3.island.adt.*;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;

public class River {

    private int rivers;

    public River(int rivers){
        this.rivers = rivers;
    }
    
    public IslandMesh createRivers (IslandMesh aMesh){
        List<Tile> tilesWithColors = aMesh.getTilesList();
        List<Corner> cornersWithColors = aMesh.getCornersList();
        List<Edge> edgesWithColors = aMesh.getEdgesList();

        List<Integer> lands = new ArrayList<>();
        int counter = 0;
        for (Tile t:tilesWithColors){
            if (t.getTiletype().equals("land")){
                lands.add(counter);
            } 
            counter++;
        }
        if (rivers > lands.size()){
            rivers = lands.size();
        }
        Random bag = new Random();
        List<Integer> random = new ArrayList<>();
        
        for (int i = 0; i<rivers; i++){
            int randInt = lands.get(bag.nextInt(lands.size()));
            for (int j = 0; j<random.size(); j++){
                if (random.get(j) == randInt){
                    randInt = lands.get(bag.nextInt(lands.size()));
                    j = -1;
                }
                for (int a: tilesWithColors.get(randInt).getNeighborsList()){
                    if (a == random.get(j) || tilesWithColors.get(a).getTiletype() == "ocean" || tilesWithColors.get(a).getTiletype() == "lake" || tilesWithColors.get(a).getTiletype() == "lagoon"){
                        randInt = lands.get(bag.nextInt(lands.size()));
                        j = -1;
                        break;
                    }
                }
            }
            int randEdge = bag.nextInt(tilesWithColors.get(randInt).getSegmentsList().size());
            int count = 0;
            for (int c: tilesWithColors.get(randInt).getSegmentsList()){
                if (randEdge == count){
                    randInt = c;
                    break;
                }
                count++;
            }
            random.add(randInt);              
        }
        Collections.sort(random);
        //Beach
        int i = 0;
        int j = 0;
        List<Edge> river = new ArrayList<>();
        for (Edge e: edgesWithColors){
            if(rivers == 0){
                break;
            }
            if (i == random.get(j)){
                riverRecursion(edgesWithColors, cornersWithColors, tilesWithColors, e, 3);
                river.add(e);
                e.setColor(0, 0, 0);
                e.setThickness(4);
                edgesWithColors.set(i,e);
                j++;
                if(j==random.size()){
                    break;
                }
            }
            

            i++;
        }
        i = 0;
        for(Edge e: river){
            e.setUnmarked();
        }
        for (Edge e:edgesWithColors){
            if(e.isMarked()){
                e.setColor(45, 0, 173);
                e.setThickness(4);
                edgesWithColors.set(i,e);
            }
            else{
                e.setColor(0, 0, 0);
            }
            i++;
        }
        IslandMesh Mesh = new IslandMesh(aMesh.getWidth(), aMesh.getHeight(), cornersWithColors, edgesWithColors, aMesh.getTilesList());

        return Mesh;
    }

    public Edge riverRecursion(List<Edge> edgesWithColors, List<Corner> cornersWithColors, List<Tile> tilesWithColors, Edge e, int v){
        List<Edge> sharedEdges = new ArrayList<>();
        e.setMarked();
        for (Edge a: edgesWithColors){
            if ((a.getV1Idx() == e.getV2Idx() || a.getV2Idx() == e.getV2Idx()) && v >= 2){
                if (!(a.getV1Idx() == e.getV1Idx() && a.getV2Idx() == e.getV2Idx())){
                    sharedEdges.add(a);
                }
            }
            if ((a.getV1Idx() == e.getV1Idx() || a.getV2Idx() == e.getV1Idx()) && v%2 == 1){
                if (!(a.getV1Idx() == e.getV1Idx() && a.getV2Idx() == e.getV2Idx())){
                    sharedEdges.add(a);
                }
            }
        }

        for (Edge i: sharedEdges){
            String tileType = "land";
            for (Tile t: tilesWithColors){
                for(int b: t.getSegmentsList()){
                    if (edgesWithColors.get(b) == i){
                        tileType = t.getTiletype();
                    }
                }
            }
            if (tileType == "ocean" || tileType == "lake" || tileType == "lagoon"){
                return i;
            }
        } 
        List<Edge> neighbours = new ArrayList<>();
        
        for (Edge i: sharedEdges){
            double elevationI = (cornersWithColors.get(i.getV1Idx()).getElevation()+cornersWithColors.get(i.getV2Idx()).getElevation())/2;
            double elevationE = (cornersWithColors.get(e.getV1Idx()).getElevation()+cornersWithColors.get(e.getV2Idx()).getElevation())/2;
            if (!i.isMarked() && elevationI <= elevationE){
                if (i.getV1Idx() == e.getV1Idx() || i.getV1Idx() == e.getV2Idx()){
                    v = 2;
                }
                else{
                    v = 1;
                }
                e = riverRecursion(edgesWithColors, cornersWithColors, tilesWithColors, i, v);
                    String tileType = "land";
                    for (Tile t: tilesWithColors){
                        for(int b: t.getSegmentsList()){
                            if (edgesWithColors.get(b) == e){
                                tileType = t.getTiletype();
                            }
                        }
                    }
                    if (tileType == "ocean" || tileType == "lake" || tileType == "lagoon"){
                        break;
                    }
                    else{
                        e.setUnmarked();
                    }
                
            }
        }
        return e;
    }

















        
        /* List<Tile> river = new ArrayList<>();
        for (Tile t: tilesWithColors){
            if(rivers == 0){
                break;
            }
            if (i == random.get(j)){
                t.setColor(45, 0, 173);
                t.setTileType("river");
                tilesWithColors.set(i, t); 
                river.add(riverRecursion(tilesWithColors, t));
                j++;
                if(j==random.size()){
                    break;
                }
            }
            

            i++;
        }
        for (Tile t:tilesWithColors){
            if (t.isMarked()){
                t.setColor(45, 0, 173);
                t.setTileType("river");
                tilesWithColors.set(i, t); 
                t.setUnmarked();
            }
        }
    }
    public Tile riverRecursion (List<Tile> tilesWithColors, Tile tile){
        tile.setMarked();
        for (int i: tile.getNeighborsList()){
            if (tilesWithColors.get(i).getTiletype() == "ocean" || tilesWithColors.get(i).getTiletype() == "lake" || tilesWithColors.get(i).getTiletype() == "lagoon"){
                return tilesWithColors.get(i);
            }
        }   
        
        for (int i: tile.getNeighborsList()){
            int counter = 0;
            for (int j:tilesWithColors.get(i).getNeighborsList()){
                if (tilesWithColors.get(j).isMarked()){
                    counter++;
                }
            }
            if (!tilesWithColors.get(i).isMarked() && counter < 2 && tilesWithColors.get(i).getElevation()<=tile.getElevation()){
                tile = riverRecursion(tilesWithColors,tilesWithColors.get(i));
                break;
            }
        }
        return tile;
    } */
}
