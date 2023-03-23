package ca.mcmaster.cas.se2aa4.a3.island.ShapesGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcmaster.cas.se2aa4.a3.island.adt.*;

import ca.mcmaster.cas.se2aa4.a3.island.ShapesGen.*;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;


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

    public IslandMesh build(IslandMesh aMesh){
        

        List<Tile> polygonsWithColors = new ArrayList<>();

        int fillCenterX_Right = 350;
        int fillCenterY_Right = 500;
        int fillCenterX_Left = 725;
        int fillCenterY_Left = 500;
        int fillRadius = this.outerRadius;

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


        //Property land = Property.newBuilder().setKey("tileType").setValue("land").build();
        //Property ocean = Property.newBuilder().setKey("tileType").setValue("ocean").build();
        //Property lagoon = Property.newBuilder().setKey("tileType").setValue("lagoon").build();
        //Property beach = Property.newBuilder().setKey("tileType").setValue("beach").build();
        int[] colorCode = {0,0,0};
        for (Tile p: aMesh.getTilesList()){
            double centroidX = aMesh.getCorner(p.getCentroidIdx()).getX();
            double centroidY = aMesh.getCorner(p.getCentroidIdx()).getY();

            double distance = Math.sqrt(Math.pow(centroidX - centerXRight,2) + Math.pow(centroidY- centerYRight,2));
            if (distance <= outerRadius){
                colorCode[0] = 45;
                colorCode[1] = 173;
                colorCode[2] = 79;
                p.setColor(colorCode[0],colorCode[1],colorCode[2]);
                p.setTileType("land");

            }
            else if(distance > outerRadius) {
                colorCode[0] = 45;
                colorCode[1] = 49;
                colorCode[2] = 173;
                p.setColor(colorCode[0],colorCode[1],colorCode[2]);
                p.setTileType("ocean");
            }
        }

        int i = 0;
        for (Tile p: polygonsWithColors){
            double centroidX = aMesh.getCorner(p.getCentroidIdx()).getX();
            double centroidY = aMesh.getCorner(p.getCentroidIdx()).getY();
            double distance = Math.sqrt(Math.pow(centroidX - centerXLeft,2) + Math.pow(centroidY- centerYLeft,2));

            if (p.getTiletype().equals("ocean")){
                if (distance <= fillRadius && centroidX < 500){
                    colorCode[0] = 45;
                    colorCode[1] = 173;
                    colorCode[2] = 79;
                    p.setColor(colorCode[0],colorCode[1],colorCode[2]);
                    p.setTileType("land");

                }
            }
            i++;
        }

        i = 0;
        for (Tile p: polygonsWithColors){
            double centroidX = aMesh.getCorner(p.getCentroidIdx()).getX();
            double centroidY = aMesh.getCorner(p.getCentroidIdx()).getY();
            double distance = Math.sqrt(Math.pow(centroidX - fillCenterX_Right,2) + Math.pow(centroidY- fillCenterY_Right,2));
            double distance2 = Math.sqrt(Math.pow(centroidX - fillCenterX_Left,2) + Math.pow(centroidY- fillCenterY_Left,2));

            if (p.getTiletype().equals("land")){
                if ((distance <= fillRadius && centroidX > 500) || (distance2 <= fillRadius && centroidX < 500)){
                    colorCode[0] = 45;
                    colorCode[1] = 49;
                    colorCode[2] = 173;
                    p.setColor(colorCode[0],colorCode[1],colorCode[2]);
                    p.setTileType("ocean");

                }
            }
            i++;
        }


        i = 0;
        for (Tile p: polygonsWithColors){
            double centroidX = aMesh.getCorner(p.getCentroidIdx()).getX();
            double centroidY = aMesh.getCorner(p.getCentroidIdx()).getY();

            double distance = Math.sqrt(Math.pow(centroidX - headCenterX_Right,2) + Math.pow(centroidY- headCenterY_Right,2));
            double distance2 = Math.sqrt(Math.pow(centroidX - headCenterX_Left,2) + Math.pow(centroidY- headCenterY_Left,2));

            if (p.getTiletype().equals("ocean")){
                if (distance <= headSize || distance2 <= headSize){
                    colorCode[0] = 45;
                    colorCode[1] = 173;
                    colorCode[2] = 79;
                    p.setColor(colorCode[0],colorCode[1],colorCode[2]);
                    p.setTileType("land");

                }
            }
            

            i++;
        }

        //Lagoon
        i = 0;
        for (Tile p: polygonsWithColors){
            double centroidX = aMesh.getCorner(p.getCentroidIdx()).getX();
            double centroidY = aMesh.getCorner(p.getCentroidIdx()).getY();

            double distance = Math.sqrt(Math.pow(centroidX - headCenterX_Right,2) + Math.pow(centroidY- headCenterY_Right,2));
            double distance2 = Math.sqrt(Math.pow(centroidX - headCenterX_Left,2) + Math.pow(centroidY- headCenterY_Left,2));

            if (p.getTiletype().equals("land")){
                if (distance <= lagoonSize || distance2 <= lagoonSize){
                    colorCode[0] = 45;
                    colorCode[1] = 105;
                    colorCode[2] = 173;
                    p.setColor(colorCode[0],colorCode[1],colorCode[2]);
                    p.setTileType("lagoon");

                }
            }
            i++;
        }

        
        IslandMesh Mesh = new IslandMesh(aMesh.getWidth(), aMesh.getHeight(), aMesh.getCornersList(), aMesh.getEdgesList(), aMesh.getTilesList());

        return Mesh;
    }
}