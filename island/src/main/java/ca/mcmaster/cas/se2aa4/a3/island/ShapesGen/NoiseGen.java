package ca.mcmaster.cas.se2aa4.a3.island.ShapesGen;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcmaster.cas.se2aa4.a3.island.ShapesGen.*;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a3.island.noise.Noise;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;

public class NoiseGen implements Buildable{

    private Property land = Property.newBuilder().setKey("tileType").setValue("land").build();
    private Property ocean = Property.newBuilder().setKey("tileType").setValue("ocean").build();

    private final int noiseSeed;

    public NoiseGen(int noiseSeed) {
        this.noiseSeed = noiseSeed;
    }

    
    NoiseGen(Map<String, String> options){
        this(Integer.parseInt(options.getOrDefault(Configuration.NOISESEED, "-1")));

        System.out.print(options);
    }

    public Mesh build(Mesh aMesh){

        List<Polygon> polygonsWithColors = new ArrayList<>();
        
        System.out.println("Selected Noisy Gen");
        
        Noise noise = new Noise();
        if (noiseSeed <= 0){
            noise.generate();
        }
        else {
            noise.generate(noiseSeed);
        }
        
        int width = 1000;
        int length = 1000;
        int centerX = length/2;
        int centerY = width/2;
        

        for (Polygon p: aMesh.getPolygonsList()){
            double centroidX = aMesh.getVertices(p.getCentroidIdx()).getX();
            double centroidY = aMesh.getVertices(p.getCentroidIdx()).getY();
            double distance = Math.sqrt(Math.pow((centroidX-centerX),2)+Math.pow((centroidY-centerY),2));

            double noiseval = noise.getValue(centroidX, centroidY)*(1/distance);
            //System.out.println(noiseval);
            if(centroidX > 100 && centroidX < width - 100 && centroidY > 100 && centroidY < length - 100){
                if (noiseval > 0.0){
                    String colorCode = 45 + "," + 173 + "," + 79;
                    Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                    Polygon tiles = Polygon.newBuilder(p).addProperties(color).addProperties(land).build();
                    polygonsWithColors.add(tiles);
    
                }
                else{
                    String colorCode = 45 + "," + 49 + "," + 173;
                    Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                    Polygon tiles = Polygon.newBuilder(p).addProperties(color).addProperties(ocean).build();
                    polygonsWithColors.add(tiles);
                }
            }
             else{
                String colorCode = 45 + "," + 49 + "," + 173;
                Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                Polygon tiles = Polygon.newBuilder(p).addProperties(color).addProperties(ocean).build();
                polygonsWithColors.add(tiles);
            }

        }

        return Mesh.newBuilder().addAllVertices(aMesh.getVerticesList()).addAllSegments(aMesh.getSegmentsList()).addAllPolygons(polygonsWithColors).build();

    }
}
