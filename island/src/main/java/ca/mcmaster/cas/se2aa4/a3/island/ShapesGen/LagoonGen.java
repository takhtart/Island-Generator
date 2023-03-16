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

public class LagoonGen implements Buildable{

    private final int innerRadius;
    private final int outerRadius;

    public LagoonGen(int innerRadius, int outerRadius) {
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
    }

    

    LagoonGen(Map<String, String> options){
        this(Integer.parseInt(options.getOrDefault(Configuration.INNERRADIUS, "200")), Integer.parseInt(options.getOrDefault(Configuration.OUTERRADIUS, "400")));

        System.out.print(options);
    }


    public Mesh build(Mesh aMesh){
        List<Polygon> polygonsWithColors = new ArrayList<>();
        int centerX = 500;
        int centerY = 500;
        System.out.println(this.innerRadius + " " + this.outerRadius);

        Property land = Property.newBuilder().setKey("tileType").setValue("land").build();
        Property ocean = Property.newBuilder().setKey("tileType").setValue("ocean").build();
        Property lagoon = Property.newBuilder().setKey("tileType").setValue("lagoon").build();
        //Property beach = Property.newBuilder().setKey("tileType").setValue("beach").build();

        for (Polygon p: aMesh.getPolygonsList()){
            double centroidX = aMesh.getVertices(p.getCentroidIdx()).getX();
            double centroidY = aMesh.getVertices(p.getCentroidIdx()).getY();

            double distance = Math.sqrt(Math.pow(centroidX - centerX,2) + Math.pow(centroidY- centerY,2));
            

            if (distance <= outerRadius && distance >= this.innerRadius){
                String colorCode = 45 + "," + 173 + "," + 79;
                Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                Polygon tiles = Polygon.newBuilder(p).addProperties(color).addProperties(land).build();


                polygonsWithColors.add(tiles);
            }
            else if(distance > this.outerRadius) {
                String colorCode = 45 + "," + 49 + "," + 173;
                Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                Polygon tiles = Polygon.newBuilder(p).addProperties(color).addProperties(ocean).build();

                polygonsWithColors.add(tiles);
            }
            else {
                String colorCode = 45 + "," + 105 + "," + 173;
                Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                Polygon tiles = Polygon.newBuilder(p).addProperties(color).addProperties(lagoon).build();

                polygonsWithColors.add(tiles);
            }
        }
        return Mesh.newBuilder().addAllVertices(aMesh.getVerticesList()).addAllSegments(aMesh.getSegmentsList()).addAllPolygons(polygonsWithColors).build();
    }
}
