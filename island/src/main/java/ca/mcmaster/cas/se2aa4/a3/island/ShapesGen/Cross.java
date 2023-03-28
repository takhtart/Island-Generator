package ca.mcmaster.cas.se2aa4.a3.island.ShapesGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Map;


import ca.mcmaster.cas.se2aa4.a3.island.ShapesGen.*;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a3.island.adt.*;


public class Cross implements Buildable{
    private final int width;
    private final int height;

    public Cross(int width, int height) {
        this.width = width;
        this.height = height;
    }

    

    Cross(Map<String, String> options){
        this(Integer.parseInt(options.getOrDefault(Configuration.INNERRADIUS, "700")), Integer.parseInt(options.getOrDefault(Configuration.OUTERRADIUS, "700")));

        System.out.print(options);
    }

    public IslandMesh build (IslandMesh aMesh){
    List<Tile> tilesWithColors = new ArrayList();

    int width = this.width;
    int height = this.height;

    int pointX1 = 500-width/2;
    int pointX2 = 500+width/2;
    int pointY1 = 500-height/2;
    int pointY2 = 500+height/2;
    
        
    for (Tile t: aMesh.getTilesList()){
            
        if (aMesh.getCorner(t.getCentroidIdx()).getX()>pointX1 && aMesh.getCorner(t.getCentroidIdx()).getX()<pointX2 && aMesh.getCorner(t.getCentroidIdx()).getY()>pointY1 && aMesh.getCorner(t.getCentroidIdx()).getY()<pointY2){
            if(Math.abs(aMesh.getCorner(t.getCentroidIdx()).getX()-aMesh.getCorner(t.getCentroidIdx()).getY())<125){

                t.setTileType("land");
                //t.setColor(45, 173, 79);

                tilesWithColors.add(t);
                continue;
            }
            if(aMesh.getCorner(t.getCentroidIdx()).getX()+aMesh.getCorner(t.getCentroidIdx()).getY()>875 && aMesh.getCorner(t.getCentroidIdx()).getX()+aMesh.getCorner(t.getCentroidIdx()).getY()<1125){
                t.setTileType("land");
                //t.setColor(45, 173, 79);


                tilesWithColors.add(t);
                continue;
            }
                
        }
        
        t.setTileType("ocean");
        t.setColor(45,49, 173);
        tilesWithColors.add(t);
        
    }
    IslandMesh Mesh = new IslandMesh(aMesh.getWidth(), aMesh.getHeight(), aMesh.getCornersList(), aMesh.getEdgesList(), aMesh.getTilesList());

    return Mesh;
    }
}
