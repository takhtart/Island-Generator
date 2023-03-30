package ca.mcmaster.cas.se2aa4.a3.island.modifiers;

import java.util.*;
import ca.mcmaster.cas.se2aa4.a3.island.adt.*;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;

public class River {

    private int rivers;

    public River(int rivers){
        this.rivers = rivers;
    }
    
    public IslandMesh createRivers (IslandMesh aMesh, int seed){
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
        if(seed == 0){ 
            seed = bag.nextInt(1000);
            bag.setSeed(seed);
            System.out.println(seed);
        }
        else{
            bag.setSeed(seed);
        }
        List<Integer> random = new ArrayList<>();
        
        for (int i = 0; i<rivers; i++){
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
        List<Edge> start = new ArrayList<>();
        List<List<Edge>> meshRivers = new ArrayList<>();
        List<Edge> unmarkedEdges = new ArrayList<>();
        for(Edge edge: edgesWithColors){
            unmarkedEdges.add(edge);
        }
        for (Edge e: edgesWithColors){
            List<Edge> river = new ArrayList<>();
            if(rivers == 0){
                break;
            }
            if (i == random.get(j)){
                riverRecursion(edgesWithColors, cornersWithColors, tilesWithColors, e, meshRivers, 3);
                for (Edge edge: edgesWithColors){
                    if (edge.isMarked() && unmarkedEdges.indexOf(edge) != -1){
                        river.add(edge);
                        unmarkedEdges.remove(edge);
                    }
                }
                
                start.add(e);
                if(river.size() != 1){
                    meshRivers.add(river);
                    e.setColor(45, 0, 113);
                    e.setThickness(4);
                    edgesWithColors.set(i,e);
                }
                j++;
                if(j==random.size()){
                    break;
                }
            }
            

            i++;
        }

        
        /* for(List<Edge> list:meshRivers){
            for(Edge riverEdge: list){
                for(List<Edge> l:meshRivers){
                    for(Edge e: l){
                        if((riverEdge.getV1Idx() == e.getV2Idx() || riverEdge.getV2Idx() == e.getV2Idx() || riverEdge.getV1Idx() == e.getV1Idx() || riverEdge.getV2Idx() == e.getV1Idx()) && !list.contains(e)){
                            e.setThickness(2);
                        }
                    }
                }
            }
        } */

        i = 0;
        for(Edge e: start){
            e.setUnmarked();
        }
        for (Edge e:edgesWithColors){
            if(e.isMarked()){
                e.setEdgeType("river");
                e.setColor(45, 0, 173);
                e.setThickness(e.getThickness() + 4);
                edgesWithColors.set(i,e);
            }
            i++;
        }
        /* for(List<Edge> list:meshRivers){
            for(Edge edge: list){
                System.out.print(edge.getV1Idx()+", ");
            }
            System.out.println();
        } */
        
        
        IslandMesh Mesh = new IslandMesh(aMesh.getWidth(), aMesh.getHeight(), cornersWithColors, edgesWithColors, aMesh.getTilesList());

        return Mesh;
    }

    public Edge riverRecursion(List<Edge> edgesWithColors, List<Corner> cornersWithColors, List<Tile> tilesWithColors, Edge e, List<List<Edge>> meshRivers, int v){
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
                e = riverRecursion(edgesWithColors, cornersWithColors, tilesWithColors, i, meshRivers, v);
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
                        i.setUnmarked();
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
