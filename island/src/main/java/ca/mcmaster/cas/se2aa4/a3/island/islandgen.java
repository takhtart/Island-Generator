package ca.mcmaster.cas.se2aa4.a3.island;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a3.island.ShapesGen.LagoonGen;
import ca.mcmaster.cas.se2aa4.a3.island.ShapesGen.NoiseGen;
import ca.mcmaster.cas.se2aa4.a3.island.ShapesGen.YinYangGen;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;


public class islandgen{
    public Mesh generateisland(Mesh aMesh){
        List<Polygon> polygonsWithColors = new ArrayList<>(aMesh.getPolygonsList());

        //Beach
        Property beach = Property.newBuilder().setKey("tileType").setValue("beach").build();
        int i = 0;
        for (Polygon p: polygonsWithColors){
            if (p.getProperties(1).getValue().equals("land")){
                for(Integer n: p.getNeighborIdxsList()){
                    Polygon neighbour = polygonsWithColors.get(n);
                    if (neighbour.getProperties(1).getValue().equals("lagoon") || neighbour.getProperties(1).getValue().equals("ocean")){
                        String colorCode = 199 + "," + 190 + "," + 111;
                        Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();

                        
                        Polygon tiles = Polygon.newBuilder(p).setProperties(0,color).setProperties(1,beach).build();
                        polygonsWithColors.set(i, tiles); 
                    }

                }
            }
            i++;
        }

     //aMesh.toString();
     return Mesh.newBuilder().addAllVertices(aMesh.getVerticesList()).addAllSegments(aMesh.getSegmentsList()).addAllPolygons(polygonsWithColors).build();
}

}