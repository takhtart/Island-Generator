package ca.mcmaster.cas.se2aa4.a3.island;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;


public class islandgen {
    public void generateisland(Mesh aMesh, String Shape){
        if (Shape == "circle"){
            int width = 1000;
            int length = 1000;

            List<int[]> Colors = new ArrayList<int[]>();
            Random colorBag = new Random();
            List<Polygon> polygonsWithColors = new ArrayList<>();
            for (int i = 0; i < aMesh.getPolygonsList().size(); i++) {
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
        }


        
    }
}
