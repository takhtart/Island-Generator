package ca.mcmaster.cas.se2aa4.a2.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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

    private final int width = 500;
    private final int height = 500;
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
                System.out.println();
                
            }
        }

        System.out.println(vertices);


        // Distribute colors randomly. Vertices are immutable, need to enrich them
        List<Vertex> verticesWithColors = new ArrayList<>();
        Random bag = new Random();
        for(Vertex v: vertices){
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).build();
            verticesWithColors.add(colored);
        }

        // Segments Generation Between Veritices:
        List<Structs.Segment> segments = new ArrayList<>();
        
        for (int i = 0; i < vertices.size()-3; i++) {

            double test = 0;
            int v1_idx = i;
            int v2_idx = i+1;
            int v3_idx = i+2;
            int v4_idx = i+3;

            List<Vertex> vertex = new ArrayList<>(vertices);
            double v1x = vertex.get(v1_idx).getX();
            double v1y = vertex.get(v1_idx).getY();
            double v2x = vertex.get(v2_idx).getX();
            double v2y = vertex.get(v2_idx).getY();
            double v3x = vertex.get(v3_idx).getX();
            double v3y = vertex.get(v3_idx).getY();
            double v4x = vertex.get(v4_idx).getX();
            double v4y = vertex.get(v4_idx).getY();

            

            if (v1y == v2y || v1x == v2x){
                segments.add(Structs.Segment.newBuilder().setV1Idx(v1_idx).setV2Idx(v2_idx).build()); 
            }
            if (v1y == v3y || v1x == v3x){
                segments.add(Structs.Segment.newBuilder().setV1Idx(v1_idx).setV2Idx(v3_idx).build());
            }
            if (v1y == v4y || v1x == v4x){
                segments.add(Structs.Segment.newBuilder().setV1Idx(v1_idx).setV2Idx(v4_idx).build());
            }
            if (v2y == v3y || v2x == v3x){
                segments.add(Structs.Segment.newBuilder().setV1Idx(v2_idx).setV2Idx(v3_idx).build());
            }
            if (v2y == v4y || v2x == v4x){
                segments.add(Structs.Segment.newBuilder().setV1Idx(v2_idx).setV2Idx(v4_idx).build());
            }
            if (v3y == v4y || v3x == v4x){
                segments.add(Structs.Segment.newBuilder().setV1Idx(v3_idx).setV2Idx(v4_idx).build());
            }
        }

        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segments).build();
    }

}
