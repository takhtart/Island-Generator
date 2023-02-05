package ca.mcmaster.cas.se2aa4.a2.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.SegmentOrBuilder;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

public class DotGen {

    private final int width = 40;
    private final int height = 40;
    private final int square_size = 20;

    public Mesh generate() {
        List<Vertex> vertices = new ArrayList<>();
        // Create all the vertices
        for(int x = 0; x < width; x += square_size) {
            for(int y = 0; y < height; y += square_size) {
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y).build());
                vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY((double) y).build());
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y+square_size).build());
                vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY((double) y+square_size).build());
                
            }
        }


        // Distribute colors randomly. Vertices are immutable, need to enrich them
        List<Vertex> verticesWithColors = new ArrayList<>();
        List<int[]> Colors = new ArrayList<int[]>();
        Random bag = new Random();
        for(Vertex v: vertices){
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).build();
            verticesWithColors.add(colored);
            int[] RGB = {red,green,blue};
            Colors.add(RGB);
            
        }

        

        // Segments Generation Between Veritices:
        List<Structs.Segment> segments = new ArrayList<>();
        for (int i = 0; i < vertices.size()-3; i+=4) {

            int v1_idx = i;
            int v2_idx = i+1;
            int v3_idx = i+2;
            int v4_idx = i+3;

        
                segments.add(Structs.Segment.newBuilder().setV1Idx(v1_idx).setV2Idx(v2_idx).build()); 
                segments.add(Structs.Segment.newBuilder().setV1Idx(v1_idx).setV2Idx(v3_idx).build());
                segments.add(Structs.Segment.newBuilder().setV1Idx(v2_idx).setV2Idx(v4_idx).build());
                segments.add(Structs.Segment.newBuilder().setV1Idx(v3_idx).setV2Idx(v4_idx).build());
        }

        List<Structs.Segment> segmentsWithColors = new ArrayList<>();
        for(Segment s: segments) {
            int v1 = s.getV1Idx();
            int v2 = s.getV2Idx();

            int red = (Colors.get(v1)[0] + Colors.get(v2)[0])/2;
            int green = (Colors.get(v1)[1] + Colors.get(v2)[1])/2;
            int blue = (Colors.get(v1)[2] + Colors.get(v2)[2])/2;


            System.out.println("V1: " + v1);
            System.out.println(Colors.get(v1)[0] + "," + Colors.get(v1)[1] + "," + Colors.get(v1)[2]);

            System.out.println("V2: " + v2);
            System.out.println(Colors.get(v2)[0] + "," + Colors.get(v2)[1] + "," + Colors.get(v2)[2]);


            System.out.println("Average: " + v1 + " and " + v2);
            System.out.println(red + "," + green + "," + blue);


            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Segment colored = Segment.newBuilder(s).addProperties(color).build();
            segmentsWithColors.add(colored);

        }

        System.out.println("|Segments| = " + segmentsWithColors.size());
        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segmentsWithColors).build();

    }

    
 

}
