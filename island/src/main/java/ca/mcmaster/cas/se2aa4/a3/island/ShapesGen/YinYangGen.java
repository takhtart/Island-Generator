package ca.mcmaster.cas.se2aa4.a3.island.ShapesGen;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcmaster.cas.se2aa4.a3.island.ShapesGen.*;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;


public class YinYangGen implements Buildable{

    private final int lagoonSize;
    private final int outerRadius;

    public YinYangGen(int lagoonSize, int outerRadius) {
        this.lagoonSize = lagoonSize;
        this.outerRadius = outerRadius;
    }

    

    YinYangGen(Map<String, String> options){
        this(Integer.parseInt(options.getOrDefault(Configuration.INNERRADIUS, "50")), Integer.parseInt(options.getOrDefault(Configuration.OUTERRADIUS, "400")));

        System.out.print(options);
    }

    public Mesh build(Mesh aMesh){
        List<Polygon> polygonsWithColors = new ArrayList<>();
        int fillCenterX_Right = 350;
        int fillCenterY_Right = 500;
        int fillCenterX_Left = 725;
        int fillCenterY_Left = 500;
        int fillRadius = 400;

        int outerRadius = this.outerRadius;
        int centerXRight = 625;
        int centerYRight = 500;
        int centerXLeft = 375;
        int centerYLeft = 500;

        int headCenterX_Right = 400;
        int headCenterY_Right = 300;
        int headCenterX_Left = 700;
        int headCenterY_Left = 700;
        int headSize = 200;
        int lagoonSize = this.lagoonSize;

        Property land = Property.newBuilder().setKey("tileType").setValue("land").build();
        Property ocean = Property.newBuilder().setKey("tileType").setValue("ocean").build();
        Property lagoon = Property.newBuilder().setKey("tileType").setValue("lagoon").build();
        //Property beach = Property.newBuilder().setKey("tileType").setValue("beach").build();

        for (Polygon p: aMesh.getPolygonsList()){
            double centroidX = aMesh.getVertices(p.getCentroidIdx()).getX();
            double centroidY = aMesh.getVertices(p.getCentroidIdx()).getY();

            double distance = Math.sqrt(Math.pow(centroidX - centerXRight,2) + Math.pow(centroidY- centerYRight,2));
            if (distance <= outerRadius){
                String colorCode = 45 + "," + 173 + "," + 79;
                Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                Polygon tiles = Polygon.newBuilder(p).addProperties(color).addProperties(land).build();


                polygonsWithColors.add(tiles);
            }
            else if(distance > outerRadius) {
                String colorCode = 45 + "," + 49 + "," + 173;
                Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                Polygon tiles = Polygon.newBuilder(p).addProperties(color).addProperties(ocean).build();

                polygonsWithColors.add(tiles);
            }
        }

        int i = 0;
        for (Polygon p: polygonsWithColors){
            double centroidX = aMesh.getVertices(p.getCentroidIdx()).getX();
            double centroidY = aMesh.getVertices(p.getCentroidIdx()).getY();
            double distance = Math.sqrt(Math.pow(centroidX - centerXLeft,2) + Math.pow(centroidY- centerYLeft,2));

            if (p.getProperties(1).getValue().equals("ocean")){
                if (distance <= fillRadius && centroidX < 500){
                    String colorCode = 45 + "," + 173 + "," + 79;
                    Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();

                    Polygon tiles = Polygon.newBuilder(p).setProperties(0,color).setProperties(1,land).build();
                    polygonsWithColors.set(i, tiles);  

                }
            }
            i++;
        }

        i = 0;
        for (Polygon p: polygonsWithColors){
            double centroidX = aMesh.getVertices(p.getCentroidIdx()).getX();
            double centroidY = aMesh.getVertices(p.getCentroidIdx()).getY();
            double distance = Math.sqrt(Math.pow(centroidX - fillCenterX_Right,2) + Math.pow(centroidY- fillCenterY_Right,2));
            double distance2 = Math.sqrt(Math.pow(centroidX - fillCenterX_Left,2) + Math.pow(centroidY- fillCenterY_Left,2));

            if (p.getProperties(1).getValue().equals("land")){
                if ((distance <= fillRadius && centroidX > 500) || (distance2 <= fillRadius && centroidX < 500)){
                    String colorCode = 45 + "," + 49 + "," + 173;
                    Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();

                    Polygon tiles = Polygon.newBuilder(p).setProperties(0,color).setProperties(1,ocean).build();
                    polygonsWithColors.set(i, tiles); 

                }
            }
            i++;
        }


        i = 0;
        for (Polygon p: polygonsWithColors){
            double centroidX = aMesh.getVertices(p.getCentroidIdx()).getX();
            double centroidY = aMesh.getVertices(p.getCentroidIdx()).getY();

            double distance = Math.sqrt(Math.pow(centroidX - headCenterX_Right,2) + Math.pow(centroidY- headCenterY_Right,2));
            double distance2 = Math.sqrt(Math.pow(centroidX - headCenterX_Left,2) + Math.pow(centroidY- headCenterY_Left,2));

            if (p.getProperties(1).getValue().equals("ocean")){
                if (distance <= headSize || distance2 <= headSize){
                    String colorCode = 45 + "," + 173 + "," + 79;
                    Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();

                    Polygon tiles = Polygon.newBuilder(p).setProperties(0,color).setProperties(1,land).build();
                    polygonsWithColors.set(i, tiles); 

                }
            }
            

            i++;
        }

        //Lagoon
        i = 0;
        for (Polygon p: polygonsWithColors){
            double centroidX = aMesh.getVertices(p.getCentroidIdx()).getX();
            double centroidY = aMesh.getVertices(p.getCentroidIdx()).getY();

            double distance = Math.sqrt(Math.pow(centroidX - headCenterX_Right,2) + Math.pow(centroidY- headCenterY_Right,2));
            double distance2 = Math.sqrt(Math.pow(centroidX - headCenterX_Left,2) + Math.pow(centroidY- headCenterY_Left,2));

            if (p.getProperties(1).getValue().equals("land")){
                if (distance <= lagoonSize || distance2 <= lagoonSize){
                    String colorCode = 45 + "," + 105 + "," + 173;
                    Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();

                    Polygon tiles = Polygon.newBuilder(p).setProperties(0,color).setProperties(1,lagoon).build();
                    polygonsWithColors.set(i, tiles); 

                }
            }
            i++;
        }

        
        return Mesh.newBuilder().addAllVertices(aMesh.getVerticesList()).addAllSegments(aMesh.getSegmentsList()).addAllPolygons(polygonsWithColors).build();
    }
}