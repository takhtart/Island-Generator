package ca.mcmaster.cas.se2aa4.a2.generator;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

public class GridMesh {
    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;

    public Mesh generate() {
        List<Vertex> vertices = new ArrayList<>();

        for(int x = 0; x < width; x += square_size*2) {
            for(int y = 0; y < height; y += square_size*2) {
                //loopy += square_size*2;
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y).build());
                vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY((double) y).build());
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y+square_size).build());
                vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY((double) y+square_size).build());
                
            }
        }

        // Distribute colors randomly to vertices
        List<Vertex> verticesWithColors = new ArrayList<>();
        List<int[]> Colors = new ArrayList<int[]>();
        Random bag = new Random();
        for(Vertex v: vertices){
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            int transparencyAlpha = 255;
            
            String colorCode = red + "," + green + "," + blue + "," + transparencyAlpha;
            Property color = Property.newBuilder().setKey("rgba_color").setValue(colorCode).build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).build();
            
            verticesWithColors.add(colored);
            int[] RGBA = {red,green,blue,transparencyAlpha};
            Colors.add(RGBA);
            
        }

        //Generate Segments Between Vertices (Squares)
        List<Structs.Segment> segments = new ArrayList<>();
        for (int i = 0; i < verticesWithColors.size()-3; i+=4) {

            int v1_idx = i;
            int v2_idx = i+1;
            int v3_idx = i+2;
            int v4_idx = i+3;

            segments.add(Structs.Segment.newBuilder().setV1Idx(v1_idx).setV2Idx(v2_idx).build()); 
            segments.add(Structs.Segment.newBuilder().setV1Idx(v1_idx).setV2Idx(v3_idx).build());
            segments.add(Structs.Segment.newBuilder().setV1Idx(v2_idx).setV2Idx(v4_idx).build());
            segments.add(Structs.Segment.newBuilder().setV1Idx(v3_idx).setV2Idx(v4_idx).build());


        }

        //Generate Segments Between Squares
        for (int i = 2; i < verticesWithColors.size()-3; i+=4) {

            int v1_idx = i;
            int v2_idx = i+1;
            int v3_idx = i+2;
            int v4_idx = i+3;
            if (verticesWithColors.get(v2_idx).getY() != height) {
                segments.add(Structs.Segment.newBuilder().setV1Idx(v1_idx).setV2Idx(v3_idx).build());
                segments.add(Structs.Segment.newBuilder().setV1Idx(v2_idx).setV2Idx(v4_idx).build());
           }
        }

        for (int i = 0; i < verticesWithColors.size()-3; i+=4) {
            int v1_idx = i+1;
            int v2_idx = i+3;
            int v3_idx = v1_idx+(width/square_size)*2+1;
            int v4_idx = v2_idx+(width/square_size)*2+1;
            if (verticesWithColors.get(v2_idx).getX() != width) {
                segments.add(Structs.Segment.newBuilder().setV1Idx(v1_idx).setV2Idx(v3_idx).build());
                segments.add(Structs.Segment.newBuilder().setV1Idx(v2_idx).setV2Idx(v4_idx).build());
            }
        } 

         


        //Adds Average Colours Of The 2 Vertices Each Segment is Connected To
        List<Structs.Segment> segmentsWithColors = new ArrayList<>();
        for(Segment s: segments) {
            int v1 = s.getV1Idx();
            int v2 = s.getV2Idx();

            int red = (Colors.get(v1)[0] + Colors.get(v2)[0])/2;
            int green = (Colors.get(v1)[1] + Colors.get(v2)[1])/2;
            int blue = (Colors.get(v1)[2] + Colors.get(v2)[2])/2;
            int transparencyAlpha = 255;


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
        

        //Polygon Generation
        List<Polygon> polygons = new ArrayList<>();
        for (int i = 0; i < ((height/square_size)+1)*((height/square_size)+1); i+=4) {
            
            double centroidX = (verticesWithColors.get(segmentsWithColors.get(i).getV1Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(i+1).getV2Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(i+2).getV1Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(i+3).getV2Idx()).getX())/4;
            double centroidY = (verticesWithColors.get(segmentsWithColors.get(i).getV1Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(i+1).getV2Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(i+2).getV1Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(i+3).getV2Idx()).getY())/4;
            verticesWithColors.add(Vertex.newBuilder().setX(centroidX).setY(centroidY).build());
            polygons.add(Structs.Polygon.newBuilder().addSegmentIdxs(i).addSegmentIdxs(i+1).addSegmentIdxs(i+3).addSegmentIdxs(i+2).setCentroidIdx(verticesWithColors.size()-1).build()); 
        }
        int counter = 0;
         for (int i = ((height/square_size)+1)*((height/square_size)+1); i < ((height/square_size)+1)*((height/square_size)+1)+(height/square_size)*(height/square_size)/2; i+=2) {
            int v1 = i;
            int v2 = i+1;
            int v3 = (v1-((height/square_size)+1)*((height/square_size)+1))*2+3+counter;
            if((v3+1)%(((height/square_size)+1)*2)==0){
                counter +=4;
                v3+=4;
            }
            int v4 = v3+1;
            
            double centroidX = (verticesWithColors.get(segmentsWithColors.get(v1).getV1Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(v2).getV2Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(v3).getV1Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(v4).getV2Idx()).getX())/4;
            double centroidY = (verticesWithColors.get(segmentsWithColors.get(v1).getV1Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(v2).getV2Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(v3).getV1Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(v4).getV2Idx()).getY())/4;
            verticesWithColors.add(Vertex.newBuilder().setX(centroidX).setY(centroidY).build());
            polygons.add(Structs.Polygon.newBuilder().addSegmentIdxs(v3).addSegmentIdxs(v1).addSegmentIdxs(v4).addSegmentIdxs(v2).setCentroidIdx(verticesWithColors.size()-1).build()); 
        }
        counter = 0;
        for (int i = ((height/square_size)+1)*((height/square_size)+1)+(height/square_size+1)*((height/square_size-1)/2)+1; i<segments.size()-2;i+=2){
            int v1 = i;
            int v2 = i+1;
            int v3 = (v1-((height/square_size)+1)*((height/square_size)/2));
            if((v3+27)%(((height/square_size)+1))==0){
                counter-=2;
                continue;
            }
            v3+=counter;
            int v4 = v3+(height/square_size)-2;
             
            double centroidX = (verticesWithColors.get(segmentsWithColors.get(v1).getV1Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(v2).getV2Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(v3).getV1Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(v4).getV2Idx()).getX())/4;
            double centroidY = (verticesWithColors.get(segmentsWithColors.get(v1).getV1Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(v2).getV2Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(v3).getV1Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(v4).getV2Idx()).getY())/4;
            verticesWithColors.add(Vertex.newBuilder().setX(centroidX).setY(centroidY).build());
            polygons.add(Structs.Polygon.newBuilder().addSegmentIdxs(v1).addSegmentIdxs(v3).addSegmentIdxs(v2).addSegmentIdxs(v4).setCentroidIdx(verticesWithColors.size()-1).build());
        }  
        counter = 0;
        for (int i = ((height/square_size)+1)*((height/square_size)+1)+(height/square_size+1)*((height/square_size-1)/2); i<segments.size()-1;i+=2){
            int v1 = i;
            int v2 = i+1;
            int v3 = 2+counter;
            counter+=4;
            int v4 = v3+(height/square_size)*2+1;
            
            double centroidX = (verticesWithColors.get(segmentsWithColors.get(v1).getV1Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(v2).getV2Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(v3).getV1Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(v4).getV2Idx()).getX())/4;
            double centroidY = (verticesWithColors.get(segmentsWithColors.get(v1).getV1Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(v2).getV2Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(v3).getV1Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(v4).getV2Idx()).getY())/4;
            verticesWithColors.add(Vertex.newBuilder().setX(centroidX).setY(centroidY).build());
            polygons.add(Structs.Polygon.newBuilder().addSegmentIdxs(v1).addSegmentIdxs(v3).addSegmentIdxs(v2).addSegmentIdxs(v4).setCentroidIdx(verticesWithColors.size()-1).build()); 
        } 
        List <Polygon> polygonsWithNeighbors = new ArrayList<>();
        List<Integer> neighbor = new ArrayList<>();
        for (int i = 0; i<polygons.size()*4;i++){
            Polygon p = polygons.get(i/4);
            int vertex;
            if(i %4 == 1 || i%4 == 2){
                vertex = segmentsWithColors.get(p.getSegmentIdxs(i%4)).getV2Idx();
            }
            else{
                vertex = segmentsWithColors.get(p.getSegmentIdxs(i%4)).getV1Idx();
            }
            counter = 0;
            for (Polygon o: polygons){
                if (counter != i/4){
                    if (vertex == segmentsWithColors.get(o.getSegmentIdxs(0)).getV1Idx()){
                        neighbor.add(counter);
                    }
                    else if (vertex == segmentsWithColors.get(o.getSegmentIdxs(1)).getV2Idx()){
                        neighbor.add(counter);
                    }
                    else if (vertex == segmentsWithColors.get(o.getSegmentIdxs(2)).getV2Idx()){
                        neighbor.add(counter);
                    }
                    else if (vertex == segmentsWithColors.get(o.getSegmentIdxs(3)).getV1Idx()){
                        neighbor.add(counter);
                    }
                }
                counter++;
            }
            if(i%4==3){
                LinkedHashSet<Integer> set = new LinkedHashSet<>(neighbor);
                List<Integer> uniqueNeighbor = new ArrayList<>(set);
                for (int j = 0; j<uniqueNeighbor.size();j++){
                    p = Polygon.newBuilder(p).addNeighborIdxs(uniqueNeighbor.get(j)).build();
                }
                polygonsWithNeighbors.add(p);
                neighbor.clear();
            }
        }
        System.out.println("|Segments| = " + segmentsWithColors.size());
        for (int i = 0; i<polygonsWithNeighbors.get(1).getNeighborIdxsCount(); i++){
            System.out.println("Neighbors: " + polygonsWithNeighbors.get(15).getNeighborIdxs(i));
        }
        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segmentsWithColors).addAllPolygons(polygonsWithNeighbors).build();

    }
}
