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
    private final int innerRadius;
    private final int outerRadius;

    public Cross(int innerRadius, int outerRadius) {
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
    }

    

    Cross(Map<String, String> options){
        this(Integer.parseInt(options.getOrDefault(Configuration.INNERRADIUS, "200")), Integer.parseInt(options.getOrDefault(Configuration.OUTERRADIUS, "400")));

        System.out.print(options);
    }

    public Mesh build (Mesh aMesh){
    List<Polygon> polygonsWithColors = new ArrayList();
    double [] pointsY = {350,150,850,650,350,150,850,650};
    double [] pointsX = {150,350,650,850,150,350,650,850};
    
    Property land = Property.newBuilder().setKey("tileType").setValue("land").build();
    Property ocean = Property.newBuilder().setKey("tileType").setValue("ocean").build();
        
    for (Polygon p: aMesh.getPolygonsList()){
        boolean landTile = false;
        //for (int i = 0; i<5;i+=4){
            
        if (aMesh.getVertices(p.getCentroidIdx()).getX()>pointsX[0] && aMesh.getVertices(p.getCentroidIdx()).getX()<pointsX[3] && aMesh.getVertices(p.getCentroidIdx()).getY()>pointsY[1] && aMesh.getVertices(p.getCentroidIdx()).getY()<pointsY[2]){
            if(Math.abs(aMesh.getVertices(p.getCentroidIdx()).getX()-aMesh.getVertices(p.getCentroidIdx()).getY())<150){
                String colorCode = 45 + "," + 173 + "," + 79;
                Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                Polygon tiles = Polygon.newBuilder(p).addProperties(color).addProperties(land).build();


                polygonsWithColors.add(tiles);
                continue;
            }
            if(aMesh.getVertices(p.getCentroidIdx()).getX()+aMesh.getVertices(p.getCentroidIdx()).getY()>850 && aMesh.getVertices(p.getCentroidIdx()).getX()+aMesh.getVertices(p.getCentroidIdx()).getY()<1150){
                String colorCode = 45 + "," + 173 + "," + 79;
                Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                Polygon tiles = Polygon.newBuilder(p).addProperties(color).addProperties(land).build();


                polygonsWithColors.add(tiles);
                continue;
            }
                /* 
            if (aMesh.getVertices(p.getCentroidIdx()).getX()<aMesh.getVertices(p.getCentroidIdx()).getY()-100){
                    String colorCode = 45 + "," + 49 + "," + 173;
                    Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                    Polygon tiles = Polygon.newBuilder(p).addProperties(color).addProperties(ocean).build();
                    polygonsWithColors.add(tiles);
                    landTile = true;
                    continue;
                }
            if (aMesh.getVertices(p.getCentroidIdx()).getX()-100>aMesh.getVertices(p.getCentroidIdx()).getY()){
                    String colorCode = 45 + "," + 49 + "," + 173;
                    Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                    Polygon tiles = Polygon.newBuilder(p).addProperties(color).addProperties(ocean).build();
                    polygonsWithColors.add(tiles);
                    landTile = true; 
                    continue;
            }  */
                
        }
        //}
        String colorCode = 45 + "," + 49 + "," + 173;
        Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
        Polygon tiles = Polygon.newBuilder(p).addProperties(color).addProperties(ocean).build();
        polygonsWithColors.add(tiles);
        
    }
    return Mesh.newBuilder().addAllVertices(aMesh.getVerticesList()).addAllSegments(aMesh.getSegmentsList()).addAllPolygons(polygonsWithColors).build();
    }
}
