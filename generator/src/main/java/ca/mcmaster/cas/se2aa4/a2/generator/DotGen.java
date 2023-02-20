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
import java.awt.geom.Rectangle2D;
import java.lang.Math;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.SegmentOrBuilder;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import org.locationtech.jts.*;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

public class DotGen {
    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;

    private final int VertexCount = 625;
    
    public Mesh generateIrregular() {
        
        Random bag = new Random();   
        // random vertices:
        List<Structs.Vertex> vertice = new ArrayList<>();

        //Inital Random Point
        Structs.Vertex v = Structs.Vertex.newBuilder().setX(bag.nextDouble(0.0, width)).setY(bag.nextDouble(0.0, height)).build();
        vertice.add(v);
        //Adds Random Point (But Ensures That No Point Overlaps)
        for (int i = 0; i < VertexCount-1; i++){
            boolean posoverlap = true;
            double x = 0;
            double y = 0;

            int whilecount = 0;
            while (posoverlap == true){
                int count = 0;
                whilecount ++;
                if (whilecount > 100){
                    throw new java.lang.RuntimeException("Too Many Vertices For Plot Area, Please Select A Lower Vertices Count Than " + VertexCount);
                    
                }
                x = bag.nextDouble(0.0, width);
                y = bag.nextDouble(0.0, height);

                for (int j = 0; j < vertice.size(); j++) {
                    double x2 = vertice.get(j).getX();
                    double y2 = vertice.get(j).getY();

                    double distance = Math.sqrt(Math.pow((x2-x),2)+Math.pow((y2-y),2));

                    //Set Gap Between Vertices By Modifying If Statement
                    if (distance > 10){
                        count++;
                    }
                    if (count == vertice.size()){
                        posoverlap = false;
                    }
                    
                }

            }

            v = Structs.Vertex.newBuilder().setX(x).setY(y).build();
            vertice.add(v);
        }
        List<Structs.Segment> segments = new ArrayList<>();
        List<Vertex> vertices = new ArrayList<>();
        List<Polygon> polygons = new ArrayList<>();
        for (int a = 0; a < 20; a++){
            List<Coordinate> coordlist = new ArrayList<>();
            for (int i = 0; i < vertice.size(); i++) {
                Coordinate coordinate = new Coordinate(vertice.get(i).getX(),vertice.get(i).getY());
                coordlist.add(coordinate);
            }


            //VoronoiDiagramBuilder
            GeometryFactory factory = new GeometryFactory();
            Envelope envelope = new Envelope(0,500,0,500);
            VoronoiDiagramBuilder irregularMesh = new VoronoiDiagramBuilder();

            
            irregularMesh.setSites(coordlist);
            irregularMesh.setClipEnvelope(envelope);


            Geometry preEnvelope = irregularMesh.getDiagram(factory);
            Geometry polygonsVD = preEnvelope.intersection(new GeometryFactory().toGeometry(envelope));
            GeometryCollection geometryCollection = (GeometryCollection) polygonsVD;

            
            //JTS Polygons to Structs Polygon
            segments = new ArrayList<>();
            vertices = new ArrayList<>();
            polygons = new ArrayList<>();
            int total = 0;
            for (int j = 0 ; j < geometryCollection.getNumGeometries() - 1; j++){
                org.locationtech.jts.geom.Polygon geometryPolygon = (org.locationtech.jts.geom.Polygon) geometryCollection.getGeometryN(j);
                
                Coordinate[] polygonCoordinates = geometryPolygon.getCoordinates();

                //Line Segments: JTS
                /* List<LineSegment> lineSegments = new ArrayList<>();
                for (int k = 0; k < polygonCoordinates.length - 1; k++){
                    Coordinate start = polygonCoordinates[k];
                    Coordinate end = polygonCoordinates[k+1];
                    lineSegments.add(new LineSegment(start,end));
                }
                lineSegments.add(new LineSegment(polygonCoordinates[polygonCoordinates.length - 1],polygonCoordinates[0])); */


                //JTS Polygon Coordinates to Vertices
                List<Integer> s = new ArrayList<>();
                int newVertices = 0;
                for(int m = 0; m < polygonCoordinates.length ; m++, newVertices ++) {
                    double x1 = polygonCoordinates[m].x;
                    double y1 = polygonCoordinates[m].y;
                    vertices.add(Vertex.newBuilder().setX(x1).setY(y1).build());
                    
                }
                //Segment Generation + Structs Polygon
                for (int p = total; p <  (total + newVertices) - 1; p++) {
                    int v1_idx = p;
                    int v2_idx = p+1;

                    segments.add(Structs.Segment.newBuilder().setV1Idx(v1_idx).setV2Idx(v2_idx).build()); 
                    s.add(p);
                }
                segments.add(Structs.Segment.newBuilder().setV1Idx(total).setV2Idx(total).build()); 
                total += newVertices;
                s.add(total - 1);

                polygons.add(Structs.Polygon.newBuilder().addAllSegmentIdxs(s).build());


            }
            vertice = new ArrayList<>();
            for (Polygon p: polygons){
                double centroidX = 0;
                double centroidY = 0;
                for (int i = 0; i<p.getSegmentIdxsCount();i++){
                    centroidX += vertices.get(segments.get(p.getSegmentIdxs(i)).getV1Idx()).getX();
                    centroidY += vertices.get(segments.get(p.getSegmentIdxs(i)).getV1Idx()).getY();
                }
                centroidX /= p.getSegmentIdxsCount();
                centroidY /= p.getSegmentIdxsCount();
                vertice.add(Vertex.newBuilder().setX(centroidX).setY(centroidY).build());
            }
        }
        return Mesh.newBuilder().addAllVertices(vertices).addAllSegments(segments).addAllPolygons(polygons).build();
    }
    
}


