package ca.mcmaster.cas.se2aa4.a3.island;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;


public class islandgen {
    public Mesh generateisland(Mesh aMesh, String Shape){
        List<Polygon> polygonsWithColors = new ArrayList<>();
        if (Shape == "circle"){
            int width = 1000;
            int length = 1000;

            List<int[]> Colors = new ArrayList<int[]>();
            Random colorBag = new Random();
            /* for (int i = 0; i < aMesh.getPolygonsList().size(); i++) {
                Polygon p = aMesh.getPolygonsList().get(i);
                int red = colorBag.nextInt(255);
                int green = colorBag.nextInt(255);
                int blue = colorBag.nextInt(255);
            
                String colorCode = red + "," + green + "," + blue;
                Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                Polygon colored = Polygon.newBuilder(p).addProperties(color).build();
            
                polygonsWithColors.add(colored);
                int[] RGBA = {red,green,blue};
                Colors.add(RGBA);
            }
        } */

        //Outer Circle
        int outerRadius = 400;
        int innerRadius = 200;
        int centerX = 500;
        int centerY = 500;
        
        Property land = Property.newBuilder().setKey("tileType").setValue("land").build();
        Property ocean = Property.newBuilder().setKey("tileType").setValue("ocean").build();
        Property lagoon = Property.newBuilder().setKey("tileType").setValue("lagoon").build();
        Property beach = Property.newBuilder().setKey("tileType").setValue("beach").build();


        for (Polygon p: aMesh.getPolygonsList()){
            double centroidX = aMesh.getVertices(p.getCentroidIdx()).getX();
            double centroidY = aMesh.getVertices(p.getCentroidIdx()).getY();

            double distance = Math.sqrt(Math.pow(centroidX - centerX,2) + Math.pow(centroidY- centerY,2));
            

            if (distance <= outerRadius && distance >= innerRadius){
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
            else {
                String colorCode = 45 + "," + 105 + "," + 173;
                Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                Polygon tiles = Polygon.newBuilder(p).addProperties(color).addProperties(lagoon).build();

                polygonsWithColors.add(tiles);
            }
        }
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
    }

     return Mesh.newBuilder().addAllVertices(aMesh.getVerticesList()).addAllSegments(aMesh.getSegmentsList()).addAllPolygons(polygonsWithColors).build();
}

}