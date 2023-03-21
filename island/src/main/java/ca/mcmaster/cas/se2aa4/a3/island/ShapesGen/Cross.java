package ca.mcmaster.cas.se2aa4.a3.island.ShapesGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Map;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import ca.mcmaster.cas.se2aa4.a3.island.ShapesGen.*;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;

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

    public Mesh build (Mesh aMesh){
    List<Polygon> polygonsWithColors = new ArrayList();

    int width = this.width;
    int height = this.height;

    int pointX1 = 500-width/2;
    int pointX2 = 500+width/2;
    int pointY1 = 500-height/2;
    int pointY2 = 500+height/2;
    
    Property land = Property.newBuilder().setKey("tileType").setValue("land").build();
    Property ocean = Property.newBuilder().setKey("tileType").setValue("ocean").build();
        
    for (Polygon p: aMesh.getPolygonsList()){
            
        if (aMesh.getVertices(p.getCentroidIdx()).getX()>pointX1 && aMesh.getVertices(p.getCentroidIdx()).getX()<pointX2 && aMesh.getVertices(p.getCentroidIdx()).getY()>pointY1 && aMesh.getVertices(p.getCentroidIdx()).getY()<pointY2){
            if(Math.abs(aMesh.getVertices(p.getCentroidIdx()).getX()-aMesh.getVertices(p.getCentroidIdx()).getY())<125){
                String colorCode = 45 + "," + 173 + "," + 79;
                Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                Polygon tiles = Polygon.newBuilder(p).addProperties(color).addProperties(land).build();


                polygonsWithColors.add(tiles);
                continue;
            }
            if(aMesh.getVertices(p.getCentroidIdx()).getX()+aMesh.getVertices(p.getCentroidIdx()).getY()>875 && aMesh.getVertices(p.getCentroidIdx()).getX()+aMesh.getVertices(p.getCentroidIdx()).getY()<1125){
                String colorCode = 45 + "," + 173 + "," + 79;
                Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                Polygon tiles = Polygon.newBuilder(p).addProperties(color).addProperties(land).build();


                polygonsWithColors.add(tiles);
                continue;
            }
                
        }
        String colorCode = 45 + "," + 49 + "," + 173;
        Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
        Polygon tiles = Polygon.newBuilder(p).addProperties(color).addProperties(ocean).build();
        polygonsWithColors.add(tiles);
        
    }
    return Mesh.newBuilder().addAllVertices(aMesh.getVerticesList()).addAllSegments(aMesh.getSegmentsList()).addAllPolygons(polygonsWithColors).build();
    }
}
