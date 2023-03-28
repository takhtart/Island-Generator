package ca.mcmaster.cas.se2aa4.a3.island.ShapesGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcmaster.cas.se2aa4.a3.island.ShapesGen.*;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a3.island.adt.*;

public class LagoonGen implements Buildable{

    private final int innerRadius;
    private final int outerRadius;

    public LagoonGen(int innerRadius, int outerRadius) {
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
    }

    

    LagoonGen(Map<String, String> options){
        this(Integer.parseInt(options.getOrDefault(Configuration.INNERRADIUS, "200")), Integer.parseInt(options.getOrDefault(Configuration.OUTERRADIUS, "400")));

        System.out.println(options);
    }


    public IslandMesh build(IslandMesh aMesh){
        List<Tile> polygonsWithColors = new ArrayList<>();
        double centerX = aMesh.getWidth()/2;
        double centerY = aMesh.getHeight()/2;
        System.out.println(this.innerRadius + " " + this.outerRadius);

        for (Tile t: aMesh.getTilesList()){
            int[] colorCode = {0,0,0};
            double centroidX = aMesh.getCorner(t.getCentroidIdx()).getX();
            double centroidY = aMesh.getCorner(t.getCentroidIdx()).getY();

            double distance = Math.sqrt(Math.pow(centroidX - centerX,2) + Math.pow(centroidY- centerY,2));
            

            if (distance <= outerRadius && distance >= this.innerRadius){
                /* colorCode[0] = 45;
                colorCode[1] = 173;
                colorCode[2] = 79;
                t.setColor(colorCode[0],colorCode[1],colorCode[2]); */
                t.setTileType("land");
            }
            else if(distance > this.outerRadius) {
                colorCode[0] = 45;
                colorCode[1] = 49;
                colorCode[2] = 173;
                t.setColor(colorCode[0],colorCode[1],colorCode[2]); 
                t.setTileType("ocean");
            }
            else {
                colorCode[0] = 45;
                colorCode[1] = 105;
                colorCode[2] = 173;
                t.setColor(colorCode[0],colorCode[1],colorCode[2]);
                t.setTileType("lagoon");
            }
        }
        IslandMesh Mesh = new IslandMesh(aMesh.getWidth(), aMesh.getHeight(), aMesh.getCornersList(), aMesh.getEdgesList(), aMesh.getTilesList());

        return Mesh;
    }
}
