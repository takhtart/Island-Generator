package ca.mcmaster.cas.se2aa4.a2.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.locationtech.jts.algorithm.Distance;

import java.util.Random;
import java.lang.Math;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.SegmentOrBuilder;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

public class DotGen {

    private final double width = 500;
    private final double height = 500;

    public Mesh generate() {        // Create all the vertices
        Random bag = new Random();
        int VertexCount = 625;
        // random vertices:
        List<Structs.Vertex> vertices = new ArrayList<>();

        //Inital Random Point
        Structs.Vertex v = Structs.Vertex.newBuilder().setX(bag.nextDouble(0.0, width)).setY(bag.nextDouble(0.0, height)).build();
        vertices.add(v);

        //Adds Random Point (But Ensures That No Point Overlaps)
        for (int i = 0; i < VertexCount; i++){
            boolean posoverlap = true;
            double x = 0;
            double y = 0;

            while (posoverlap == true){
                int count = 0;
                x = bag.nextDouble(0.0, width);
                y = bag.nextDouble(0.0, height);

                for (int j = 0; j < vertices.size(); j++) {
                    double x2 = vertices.get(j).getX();
                    double y2 = vertices.get(j).getY();

                    double distance = Math.sqrt(Math.pow((x2-x),2)+Math.pow((y2-y),2));

                    //Set Gap Between Vertices By Modifying If Statement
                    if (distance > 10){
                        count++;
                    }
                    if (count == vertices.size()){
                        posoverlap = false;
                    }
                    
                }

            }
            



            v = Structs.Vertex.newBuilder().setX(x).setY(y).build();
            vertices.add(v);
        }
        // random segments:
        List<Structs.Segment> segments = new ArrayList<>();
        for (int i = 0; i < bag.nextInt(10,50); i++) {
            int v1_idx = bag.nextInt(vertices.size());
            int v2_idx = bag.nextInt(vertices.size());
            Structs.Segment s = Structs.Segment.newBuilder().setV1Idx(v1_idx).setV2Idx(v2_idx).build();
            segments.add(s);
        } 

       
        return Mesh.newBuilder().addAllVertices(vertices).addAllSegments(segments).build();

    }

    
}


