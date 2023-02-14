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
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;

    public Mesh generate() {
        List<Vertex> vertices = new ArrayList<>();
        // Create all the vertices
        int loopx = 0;
        int loopy = 0;

        for(int x = 0; x < width; x += square_size*2) {
            loopx += square_size*2;
            for(int y = 0; y < height; y += square_size*2) {
                //loopy += square_size*2;
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y).build());
                vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY((double) y).build());
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y+square_size).build());
                vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY((double) y+square_size).build());
                
            }
        }
        //WIP
        
        /* if (((width - square_size) % 40) != 0){
            for (int x = 0; x <= width; x += square_size) {
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) height).build());
            }
            for (int y = 0; y < height; y+= square_size){
                vertices.add(Vertex.newBuilder().setX((double) width).setY((double) y).build());
            }
        } */

        /* for(int x = loopx; x < width; x += square_size*2) {
            for(int y = loopy; y < height; y += square_size*2) {
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y).build());
                vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY((double) y).build());
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y+square_size).build());
                vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY((double) y+square_size).build());
                
            }
        } */

        
        


        // Distribute colors randomly. Vertices are immutable, need to enrich them
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
        

        
        /* ArrayList<Integer> Overlap = new ArrayList<Integer>();
        //Sets Top-Most Dots To Match Dot Colour Underneath (If Overlapping) This is done so that verticies and the segments they are connected by use the right colours
        for (int i = 0; i < verticesWithColors.size(); i++) {
            
            double x1 = verticesWithColors.get(i).getX();
            double y1 = verticesWithColors.get(i).getY();
            int red = Colors.get(i)[0];
            int green = Colors.get(i)[1];
            int blue = Colors.get(i)[2];
            int transparencyAlpha = 255;
            int[] RGB = {red,green,blue,transparencyAlpha};

            String colorCode = red + "," + green + "," + blue + "," + transparencyAlpha;

            for (int j = i; j < verticesWithColors.size()-1; j++) {
                double x2 = verticesWithColors.get(j+1).getX();
                double y2 = verticesWithColors.get(j+1).getY();

                if (Double.compare(x1, x2) == 0 && Double.compare(y1, y2) == 0){
                    Property color = Property.newBuilder().setKey("rgba_color").setValue(colorCode).build();
                    Vertex colored = Vertex.newBuilder(verticesWithColors.get(j+1)).setProperties(0,color).build();
                    verticesWithColors.set(j+1, colored);
                    Colors.set(j+1,RGB);

                    //verticesWithColors.remove(j+1);
                    //Colors.remove(j+1);
                }
            }

            
        } */



        //Segments Generation Between Vertices:
        List<Segment> segments = new ArrayList<>();
        for (int i = 0; i < verticesWithColors.size()-3; i+=4) {

            int v1_idx = i;
            int v2_idx = i+1;
            int v3_idx = i+2;
            int v4_idx = i+3;
            int v1nexty_idx = i+4;
            int v2nexty_idx = i+5;
            int v2nextx_idx = v2_idx+(width/square_size)*2+1;
            int v4nextx_idx = v4_idx+(width/square_size)*2+1;

            
            segments.add(Structs.Segment.newBuilder().setV1Idx(v1_idx).setV2Idx(v2_idx).build()); 
            segments.add(Structs.Segment.newBuilder().setV1Idx(v1_idx).setV2Idx(v3_idx).build());
            segments.add(Structs.Segment.newBuilder().setV1Idx(v2_idx).setV2Idx(v4_idx).build());
            segments.add(Structs.Segment.newBuilder().setV1Idx(v3_idx).setV2Idx(v4_idx).build());
            
            

            if (verticesWithColors.get(v4_idx).getY() != height && v2nexty_idx < verticesWithColors.size() && v1nexty_idx < verticesWithColors.size()) {
                segments.add(Structs.Segment.newBuilder().setV1Idx(v3_idx).setV2Idx(v1nexty_idx).build());
                segments.add(Structs.Segment.newBuilder().setV1Idx(v4_idx).setV2Idx(v2nexty_idx).build());
            }
            if (verticesWithColors.get(v4_idx).getX() != width && v2nextx_idx < verticesWithColors.size() && v4nextx_idx < verticesWithColors.size()) {
                segments.add(Structs.Segment.newBuilder().setV1Idx(v2_idx).setV2Idx(v2nextx_idx).build());
                segments.add(Structs.Segment.newBuilder().setV1Idx(v4_idx).setV2Idx(v4nextx_idx).build());
            }

        }

        /* List<Structs.Segment> segments = new ArrayList<>();
        for (int i = 0; i < verticesWithColors.size()-3; i+=4) {

            int v1_idx = i;
            int v2_idx = i+1;
            int v3_idx = i+2;
            int v4_idx = i+3;
            int v1nexty_idx = i+4;
            int v2nexty_idx = i+5;
            int v2nextx_idx = v2_idx+(width/square_size)*2+1;
            int v4nextx_idx = v4_idx+(width/square_size)*2+1;

            segments.add(Structs.Segment.newBuilder().setV1Idx(v1_idx).setV2Idx(v2_idx).build()); 
            segments.add(Structs.Segment.newBuilder().setV1Idx(v1_idx).setV2Idx(v3_idx).build());
            segments.add(Structs.Segment.newBuilder().setV1Idx(v2_idx).setV2Idx(v4_idx).build());
            segments.add(Structs.Segment.newBuilder().setV1Idx(v3_idx).setV2Idx(v4_idx).build());


        }

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
        } */

         


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


        /* for (int i = 0; i < segmentsWithColors.size()-1; ) {
            
        
           int v3 = 0;
           int v1 = i;
           int v2 = i + 1;
           if (i==0){
            v3 = i+3;
           }
           else{
            if (i%2 == 0 ){
                v3 = i+3;
            }
            else{
                v3 = i+5;
            }
        }
           int v4 = i + 2;
           
           Polygon polygon = Polygon.newBuilder().addSegmentIdxs(v1).addSegmentIdxs(v2).addSegmentIdxs(v3).addSegmentIdxs(v4).build();
           polygons.add(polygon);
            if (i%2 == 0 || (i-99)%102 == 0 || i == 99){
                i+=3;
        /* */

        //kyen code
        List<Polygon> polygons = new ArrayList<>();
        for (int i = 0; i < segmentsWithColors.size()-(height/square_size)*3-1;) {
            Polygon polygon1 = Polygon.newBuilder().addSegmentIdxs(i).addSegmentIdxs(i+1).addSegmentIdxs(i+3).addSegmentIdxs(i+2).build();
            double centroidX = (verticesWithColors.get(segmentsWithColors.get(i).getV1Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(i+1).getV2Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(i+3).getV2Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(i+2).getV1Idx()).getX())/4;
            double centroidY = (verticesWithColors.get(segmentsWithColors.get(i).getV1Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(i+1).getV2Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(i+3).getV2Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(i+2).getV1Idx()).getY())/4;
            verticesWithColors.add(Vertex.newBuilder().setX(centroidX).setY(centroidY).build());
            polygons.add(polygon1);
            if ((i-((height/square_size)*4-4))%((height/square_size)*4+2) == 0 || i == ((height/square_size)*4-4)){
                i+=6;
            }
            else {
                Polygon polygon2 = Polygon.newBuilder().addSegmentIdxs(i+3).addSegmentIdxs(i+4).addSegmentIdxs(i+8).addSegmentIdxs(i+5).build();
                polygons.add(polygon2);
                centroidX = (verticesWithColors.get(segmentsWithColors.get(i+3).getV1Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(i+4).getV2Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(i+8).getV2Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(i+5).getV1Idx()).getX())/4;
                centroidY = (verticesWithColors.get(segmentsWithColors.get(i+3).getV1Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(i+4).getV2Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(i+8).getV2Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(i+5).getV1Idx()).getY())/4;
                verticesWithColors.add(Vertex.newBuilder().setX(centroidX).setY(centroidY).build());
                i+=8;
            }
        }
        for (int i = segmentsWithColors.size()-(height/square_size)*3-1; i < segmentsWithColors.size()-1;){
            Polygon polygon3 = Polygon.newBuilder().addSegmentIdxs(i).addSegmentIdxs(i+1).addSegmentIdxs(i+3).addSegmentIdxs(i+2).build();
            polygons.add(polygon3);
            double centroidX = (verticesWithColors.get(segmentsWithColors.get(i).getV1Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(i+1).getV2Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(i+3).getV2Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(i+2).getV1Idx()).getX())/4;
            double centroidY = (verticesWithColors.get(segmentsWithColors.get(i).getV1Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(i+1).getV2Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(i+3).getV2Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(i+2).getV1Idx()).getY())/4;
            verticesWithColors.add(Vertex.newBuilder().setX(centroidX).setY(centroidY).build());
            i+=3;
        }
        int count = (height/square_size)*4+3;
        
        
        for (int i = 6; i < segmentsWithColors.size()-((height/square_size)*3+2);) {
            if ((i-((width/square_size)*4))%((width/square_size)*4+2) == 0 || i == ((width/square_size)*4)){
                Polygon polygon3 = Polygon.newBuilder().addSegmentIdxs(i).addSegmentIdxs(i-2).addSegmentIdxs(i+1).addSegmentIdxs(count).build();
                polygons.add(polygon3);
                double centroidX = (verticesWithColors.get(segmentsWithColors.get(i).getV1Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(i-2).getV2Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(i+1).getV2Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(count).getV1Idx()).getX())/4;
                double centroidY = (verticesWithColors.get(segmentsWithColors.get(i).getV1Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(i-2).getV2Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(i+1).getV2Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(count).getV1Idx()).getY())/4;
                verticesWithColors.add(Vertex.newBuilder().setX(centroidX).setY(centroidY).build());
                i+=8;
                count+=6;
            }
            else {
                Polygon polygon1 = Polygon.newBuilder().addSegmentIdxs(i).addSegmentIdxs(i-4).addSegmentIdxs(i+1).addSegmentIdxs(count).build();
                polygons.add(polygon1);
                double centroidX = (verticesWithColors.get(segmentsWithColors.get(i).getV1Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(i-4).getV2Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(i+1).getV2Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(count).getV1Idx()).getX())/4;
                double centroidY = (verticesWithColors.get(segmentsWithColors.get(i).getV1Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(i-4).getV2Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(i+1).getV2Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(count).getV1Idx()).getY())/4;
                verticesWithColors.add(Vertex.newBuilder().setX(centroidX).setY(centroidY).build());

                if (((i+1)-((width/square_size)*4-5))%((width/square_size)*4+2) == 0 || (i+1) == ((width/square_size)*4-5)){
                    Polygon polygon2 = Polygon.newBuilder().addSegmentIdxs(i+1).addSegmentIdxs(i-1).addSegmentIdxs(i+6).addSegmentIdxs(count+3).build();
                    polygons.add(polygon2);
                    centroidX = (verticesWithColors.get(segmentsWithColors.get(i+1).getV1Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(i-1).getV2Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(i+6).getV2Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(count+3).getV1Idx()).getX())/4;
                    centroidY = (verticesWithColors.get(segmentsWithColors.get(i+1).getV1Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(i-1).getV2Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(i+6).getV2Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(count+3).getV1Idx()).getY())/4;
                    verticesWithColors.add(Vertex.newBuilder().setX(centroidX).setY(centroidY).build());
                }
                else {
                    Polygon polygon2 = Polygon.newBuilder().addSegmentIdxs(i+1).addSegmentIdxs(i-1).addSegmentIdxs(i+8).addSegmentIdxs(count+3).build();
                    polygons.add(polygon2);
                    centroidX = (verticesWithColors.get(segmentsWithColors.get(i+1).getV1Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(i-1).getV2Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(i+8).getV2Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(count+3).getV1Idx()).getX())/4;
                    centroidY = (verticesWithColors.get(segmentsWithColors.get(i+1).getV1Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(i-1).getV2Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(i+8).getV2Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(count+3).getV1Idx()).getY())/4;
                    verticesWithColors.add(Vertex.newBuilder().setX(centroidX).setY(centroidY).build());
                }

                if ((i-((width/square_size)*4-6))%((width/square_size)*4+2) == 0 || i == ((width/square_size)*4-6)){
                    i+=6;
                }
                else {
                    i+=8;
                }
                if ((i <= segmentsWithColors.size()-((height/square_size)*3+3) && i >= segmentsWithColors.size()-((height/square_size)*7-3))) {
                    count += 6;
                }
                else {
                    count += 8;
                }
            }
        }
        
        /* for (int i = 6; i < segmentsWithColors.size()-(height/square_size)*3+2;) {
            
            int v3 = 0;
            int v1 = i;
            int v2 = 0;
            if (i==6){
                v2 = i-4;
            }
            else{
                if (i%2 == 0){
                    v2 = i-4;
                }
                else{
                    v2 = i-2;
                }
            }

            if (i==6){
             v3 = i+1;
            }
            else{
             if (i%2 == 0 ){
                 v3 = i+1;
             }
             else{
                 v3 = i+7;
             }
         }
         int v4 = 0;
         if (i==6){
            v4 = (width/square_size)*2 + i-3;
           }
           else{
            if (i%2 == 0 ){
                v4 = (width/square_size)*2 + i-3;
            }
            else{
                v4 = (width/square_size)*2 + i;
            }
        }
            
            Polygon polygon = Polygon.newBuilder().addSegmentIdxs(v1).addSegmentIdxs(v2).addSegmentIdxs(v3).addSegmentIdxs(v4).build();
            polygons.add(polygon);

            

            if ((i-((width/square_size)*4-5))%((width/square_size)*4+2) == 0 || i == ((width/square_size)*4-5) ){
                i+=5;
            }
            else if (i%2 == 0){
                i+=1;
            }
            else{
                i+=7;
            }
         } */

      // Segments Generation Between Vertices: (CHANGED CODE)
        

        //polygon code (NEW CODE)
        /* List<Polygon> polygons = new ArrayList<>();
        for (int i = 0; i < ((height/square_size)+1)*((height/square_size)+1); i+=4) {
            polygons.add(Structs.Polygon.newBuilder().addSegmentIdxs(i).addSegmentIdxs(i+1).addSegmentIdxs(i+2).addSegmentIdxs(i+3).build()); 
            double centroidX = (verticesWithColors.get(segmentsWithColors.get(i).getV1Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(i+1).getV2Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(i+2).getV1Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(i+3).getV2Idx()).getX())/4;
            double centroidY = (verticesWithColors.get(segmentsWithColors.get(i).getV1Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(i+1).getV2Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(i+2).getV1Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(i+3).getV2Idx()).getY())/4;
            verticesWithColors.add(Vertex.newBuilder().setX(centroidX).setY(centroidY).build());
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
            polygons.add(Structs.Polygon.newBuilder().addSegmentIdxs(v1).addSegmentIdxs(v2).addSegmentIdxs(v3).addSegmentIdxs(v4).build()); 
            double centroidX = (verticesWithColors.get(segmentsWithColors.get(v1).getV1Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(v2).getV2Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(v3).getV1Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(v4).getV2Idx()).getX())/4;
            double centroidY = (verticesWithColors.get(segmentsWithColors.get(v1).getV1Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(v2).getV2Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(v3).getV1Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(v4).getV2Idx()).getY())/4;
            verticesWithColors.add(Vertex.newBuilder().setX(centroidX).setY(centroidY).build());
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
            polygons.add(Structs.Polygon.newBuilder().addSegmentIdxs(v1).addSegmentIdxs(v2).addSegmentIdxs(v3).addSegmentIdxs(v4).build()); 
            double centroidX = (verticesWithColors.get(segmentsWithColors.get(v1).getV1Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(v2).getV2Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(v3).getV1Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(v4).getV2Idx()).getX())/4;
            double centroidY = (verticesWithColors.get(segmentsWithColors.get(v1).getV1Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(v2).getV2Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(v3).getV1Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(v4).getV2Idx()).getY())/4;
            verticesWithColors.add(Vertex.newBuilder().setX(centroidX).setY(centroidY).build());
            verticesWithColors.add(Vertex.newBuilder().setX(centroidX).setY(centroidY).build());
        }  
        counter = 0;
        for (int i = ((height/square_size)+1)*((height/square_size)+1)+(height/square_size+1)*((height/square_size-1)/2); i<segments.size()-1;i+=2){
            int v1 = i;
            int v2 = i+1;
            int v3 = 2+counter;
            counter+=4;
            int v4 = v3+(height/square_size)*2+1;
            polygons.add(Structs.Polygon.newBuilder().addSegmentIdxs(v1).addSegmentIdxs(v2).addSegmentIdxs(v3).addSegmentIdxs(v4).build()); 
            double centroidX = (verticesWithColors.get(segmentsWithColors.get(v1).getV1Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(v2).getV2Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(v3).getV1Idx()).getX() + verticesWithColors.get(segmentsWithColors.get(v4).getV2Idx()).getX())/4;
            double centroidY = (verticesWithColors.get(segmentsWithColors.get(v1).getV1Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(v2).getV2Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(v3).getV1Idx()).getY() + verticesWithColors.get(segmentsWithColors.get(v4).getV2Idx()).getY())/4;
            verticesWithColors.add(Vertex.newBuilder().setX(centroidX).setY(centroidY).build());
            verticesWithColors.add(Vertex.newBuilder().setX(centroidX).setY(centroidY).build());
        } */ 

        System.out.println("|Segments| = " + segmentsWithColors.size());
        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segmentsWithColors).addAllPolygons(polygons).build();

    }

    
 

}
