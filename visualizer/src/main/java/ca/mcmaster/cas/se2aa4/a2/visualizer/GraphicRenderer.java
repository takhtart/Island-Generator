package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.List;

public class GraphicRenderer {

    private static final int THICKNESS = 3;
    public void render(Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);


        //Displays Segments (Below Vertices)
        for (Segment s: aMesh.getSegmentsList()) {
            
            List<Vertex> v = aMesh.getVerticesList();
            int v1 = s.getV1Idx();
            int v2 = s.getV2Idx();
            double centre_x1 = v.get(v1).getX();
            double centre_y1 = v.get(v1).getY();
            double centre_x2 = v.get(v2).getX();
            double centre_y2 = v.get(v2).getY();
            canvas.setColor(extractColor(s.getPropertiesList()));
            Line2D.Double Line = new Line2D.Double(centre_x1,centre_y1,centre_x2,centre_y2);
            canvas.draw(Line);

    

        }

        //Displays Vertices (On Top Of Segments)
        for (Vertex v: aMesh.getVerticesList()) {
            double centre_x = v.getX() - (THICKNESS/2.0d);
            double centre_y = v.getY() - (THICKNESS/2.0d);
            Color old = canvas.getColor();
            canvas.setColor(extractColor(v.getPropertiesList()));
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
            canvas.fill(point);
            canvas.setColor(old);
        }

        
        //System.out.println("Vertices: " + aMesh.getVerticesList());
        //System.out.println("Segments: " + aMesh.getSegmentsList());
        
    }

    private Color extractColor(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                System.out.println(p.getValue());
                val = p.getValue();
            }
        }
        if (val == null)
            return Color.BLACK;
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        return new Color(red, green, blue);
    }

}
