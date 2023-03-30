package ca.mcmaster.cas.se2aa4.a3.island.ShapesGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcmaster.cas.se2aa4.a3.island.adt.*;

import ca.mcmaster.cas.se2aa4.a3.island.ShapesGen.*;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;


public class YinYangGen implements Buildable{

    private final int outerRadius;

    public YinYangGen(int outerRadius) {
        this.outerRadius = outerRadius;
    }

    

    YinYangGen(Map<String, String> options){
        this(Integer.parseInt(options.getOrDefault(Configuration.OUTERRADIUS, "350")));
    }

    public IslandMesh build(IslandMesh aMesh){

        int fillCenterX_Right = (int)((aMesh.getWidth()/2) * (0.75));
        int fillCenterY_Right = (int)(aMesh.getHeight()/2);
        int fillCenterX_Left = (int)((aMesh.getWidth()/2) * (1.3));
        int fillCenterY_Left = (int)(aMesh.getHeight()/2) ;
        int fillRadius = this.outerRadius;

        int outerRadius = this.outerRadius;
        int centerXRight = (int)((aMesh.getWidth()/2) * (1.25));
        int centerYRight = (int)(aMesh.getHeight()/2);
        int centerXLeft = (int)((aMesh.getWidth()/2) * (0.75));
        int centerYLeft = (int)(aMesh.getHeight()/2);

        int headCenterX_Left = (int)((aMesh.getWidth()/2) * (0.8));
        int headCenterY_Left = (int)((aMesh.getHeight()/2) * (0.7));
        int headCenterX_Right = (int)((aMesh.getWidth()/2) * (1.4));
        int headCenterY_Right = (int)((aMesh.getHeight()/2) * (1.3));
        int headSize = this.outerRadius/2;
        int lagoonSize = this.outerRadius/5;


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
                //p.setColor(colorCode[0],colorCode[1],colorCode[2]);
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
        for (Tile p: aMesh.getTilesList()){
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
        for (Tile p: aMesh.getTilesList()){
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
        for (Tile p: aMesh.getTilesList()){
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
        for (Tile p: aMesh.getTilesList()){
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