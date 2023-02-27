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
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

public class IrregMesh {
    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;

    
    public Mesh generateIrregular(int relaxations, int numPolygons, int transparencyAlpha) {
        
        relaxations++;
        int VertexCount = numPolygons;
        Random bag = new Random();   
        // random vertices:
        List<Structs.Vertex> points = new ArrayList<>();

        //Inital Random Point
        Structs.Vertex v = Structs.Vertex.newBuilder().setX(bag.nextDouble(0.0, width)).setY(bag.nextDouble(0.0, height)).build();
        points.add(v);
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

                for (int j = 0; j < points.size(); j++) {
                    double x2 = points.get(j).getX();
                    double y2 = points.get(j).getY();

                    double distance = Math.sqrt(Math.pow((x2-x),2)+Math.pow((y2-y),2));

                    //Set Gap Between Vertices By Modifying If Statement
                    if (distance > 1){
                        count++;
                    }
                    if (count == points.size()){
                        posoverlap = false;
                    }
                    
                }

            }

            v = Structs.Vertex.newBuilder().setX(x).setY(y).build();
            points.add(v);
        }
        List<Structs.Segment> segments = new ArrayList<>();
        List<Vertex> vertices = new ArrayList<>();
        List<Polygon> polygons = new ArrayList<>();
        List<Polygon> polygonsWithCentroids = new ArrayList<>();
        for (int a = 0; a < relaxations; a++){
            List<Coordinate> coordlist = new ArrayList<>();
            for (int i = 0; i < points.size(); i++) {
                Coordinate coordinate = new Coordinate(points.get(i).getX(),points.get(i).getY());
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
            for (int j = 0 ; j < geometryCollection.getNumGeometries(); j++){
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
                segments.add(Structs.Segment.newBuilder().setV1Idx((total + newVertices) - 1).setV2Idx(total).build()); 
                total += newVertices;
                s.add(total - 1);

                polygons.add(Structs.Polygon.newBuilder().addAllSegmentIdxs(s).build());


            }
            points = new ArrayList<>();

            for (Polygon p: polygons){
                double centroidX = 0;
                double centroidY = 0;
                for (int i = 0; i<p.getSegmentIdxsCount();i++){
                    centroidX += vertices.get(segments.get(p.getSegmentIdxs(i)).getV1Idx()).getX();
                    centroidY += vertices.get(segments.get(p.getSegmentIdxs(i)).getV1Idx()).getY();
                }
                centroidX /= p.getSegmentIdxsCount();
                centroidY /= p.getSegmentIdxsCount();
                points.add(Vertex.newBuilder().setX(centroidX).setY(centroidY).build());
                if (a == relaxations-1) { 
                    vertices.add(Vertex.newBuilder().setX(centroidX).setY(centroidY).build());
                    polygonsWithCentroids.add(Structs.Polygon.newBuilder(p).setCentroidIdx(vertices.size()-1).build());
                }
            }
        }

        //DelaunayTriangulationBuilder
        DelaunayTriangulationBuilder delaunayBuilder = new DelaunayTriangulationBuilder();
        GeometryFactory factory = new GeometryFactory();

        List<Coordinate> centroidCoords = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            Coordinate coordinate = new Coordinate(points.get(i).getX(),points.get(i).getY());
            centroidCoords.add(coordinate);
        }

        delaunayBuilder.setSites(centroidCoords);
        Geometry triangles = delaunayBuilder.getTriangles(factory);
        GeometryCollection trianglesCollection = (GeometryCollection) triangles;

        List<org.locationtech.jts.geom.Polygon> generatedTriangles = new ArrayList<>(); 
        for (int j = 0 ; j < trianglesCollection.getNumGeometries() - 1; j++){
            org.locationtech.jts.geom.Polygon geometryTrianglePolygon = (org.locationtech.jts.geom.Polygon) trianglesCollection.getGeometryN(j);
            generatedTriangles.add(geometryTrianglePolygon);
        }

        List <Polygon> polygonsWithNeighbors = new ArrayList<>();
        

        for (Polygon p: polygonsWithCentroids){
            List<Integer> neighbor = new ArrayList<>();
            for (org.locationtech.jts.geom.Polygon tp: generatedTriangles){
                Coordinate[] triangleCoordinates = tp.getCoordinates();

                double x1 = triangleCoordinates[0].x;
                double y1 = triangleCoordinates[0].y;
                double x2 = triangleCoordinates[1].x;
                double y2 = triangleCoordinates[1].y;
                double x3 = triangleCoordinates[2].x;
                double y3 = triangleCoordinates[2].y;
                
                
                if (vertices.get(p.getCentroidIdx()).getX() == x1 && vertices.get(p.getCentroidIdx()).getY() == y1){
                    int polygonCounter = 0;
                    for (Polygon sp: polygonsWithCentroids){
                        if (vertices.get(sp.getCentroidIdx()).getX() == x2 && vertices.get(sp.getCentroidIdx()).getY() == y2){
                            neighbor.add(polygonCounter);
                        }
                        else if (vertices.get(sp.getCentroidIdx()).getX() == x3 && vertices.get(sp.getCentroidIdx()).getY() == y3){
                            neighbor.add(polygonCounter);
                        }
                        polygonCounter++;
                    }
                }
                else if (vertices.get(p.getCentroidIdx()).getX() == x2 && vertices.get(p.getCentroidIdx()).getY() == y2){
                    int polygonCounter = 0;
                    for (Polygon sp: polygonsWithCentroids){
                        if (vertices.get(sp.getCentroidIdx()).getX() == x1 && vertices.get(sp.getCentroidIdx()).getY() == y1){
                            neighbor.add(polygonCounter);
                        }
                        else if (vertices.get(sp.getCentroidIdx()).getX() == x3 && vertices.get(sp.getCentroidIdx()).getY() == y3){
                            neighbor.add(polygonCounter);
                        }
                        polygonCounter++;
                    }
                }
                else if (vertices.get(p.getCentroidIdx()).getX() == x3 && vertices.get(p.getCentroidIdx()).getY() == y3){
                    int polygonCounter = 0;
                    for (Polygon sp: polygonsWithCentroids){
                        if (vertices.get(sp.getCentroidIdx()).getX() == x2 && vertices.get(sp.getCentroidIdx()).getY() == y2){
                            neighbor.add(polygonCounter);
                        }
                        else if (vertices.get(sp.getCentroidIdx()).getX() == x1 && vertices.get(sp.getCentroidIdx()).getY() == y1){
                            neighbor.add(polygonCounter);
                        }
                        polygonCounter++;
                    }
                }
           }
            LinkedHashSet<Integer> set = new LinkedHashSet<>(neighbor);
            List<Integer> uniqueNeighbor = new ArrayList<>(set);

            polygonsWithNeighbors.add(Structs.Polygon.newBuilder(p).addAllNeighborIdxs(uniqueNeighbor).build());

        }
        List<Vertex> verticesWithColors = new ArrayList<>();
        List<int[]> Colors = new ArrayList<int[]>();
        Random colorBag = new Random();
        for(Vertex u: vertices){
            int red = colorBag.nextInt(255);
            int green = colorBag.nextInt(255);
            int blue = colorBag.nextInt(255);
            
            String colorCode = red + "," + green + "," + blue + "," + transparencyAlpha;
            Property color = Property.newBuilder().setKey("rgba_color").setValue(colorCode).build();
            Vertex colored = Vertex.newBuilder(u).addProperties(color).build();
            
            verticesWithColors.add(colored);
            int[] RGBA = {red,green,blue,transparencyAlpha};
            Colors.add(RGBA);
            
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
            System.out.println(red + "," + green + "," + blue + "," + transparencyAlpha);


            String colorCode = red + "," + green + "," + blue + "," + transparencyAlpha;
            Property color = Property.newBuilder().setKey("rgba_color").setValue(colorCode).build();
            Segment colored = Segment.newBuilder(s).addProperties(color).build();
            segmentsWithColors.add(colored);

        }



        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segmentsWithColors).addAllPolygons(polygonsWithNeighbors).build();
    }
    
}


