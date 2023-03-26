package ca.mcmaster.cas.se2aa4.a3.island.altimetricProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcmaster.cas.se2aa4.a3.island.adt.*;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;

public class artic implements ElevationBuildable{
    artic(Map<String, String> options){
        this(Integer.parseInt(options.getOrDefault(Configuration.ELEVATIONLEVEL, "4")));

        System.out.print(options);
    }

    private int levels;

    public artic(int levels){
        this.levels = levels;
    }

    public IslandMesh setElevation(IslandMesh aMesh){
        double centerX = aMesh.getHeight() / 2;
        double centerY = aMesh.getWidth() / 2;

        for (Tile t: aMesh.getTilesList()){
            if (t.getTiletype().equals("land")){
                double centroidX = aMesh.getCorner(t.getCentroidIdx()).getX();
                double centroidY = aMesh.getCorner(t.getCentroidIdx()).getY();

                double distance = Math.sqrt(Math.pow(centroidX - centerX,2) + Math.pow(centroidY- centerY,2));

                for (int i = 4; i >= 1; i--){
                    if (distance <= (100*i)){
                        t.setElevation(i);
                    }
                    else if (distance > 400){
                        t.setElevation(4);
                    }
                }

                if (t.getElevation() == 1){
                        t.setColor(t.getR()+ 30,t.getG(),t.getB());
                    }
                    else if (t.getElevation() == 2){
                        t.setColor(t.getR()+ 60,t.getG(),t.getB());
                    }
                    else if (t.getElevation() == 3){
                        t.setColor(t.getR()+ 90,t.getG(),t.getB());
                    }
                    else if (t.getElevation() == 4){
                        t.setColor(t.getR()+ 120,t.getG(),t.getB());
                    }
                    else if (t.getElevation() == 5){
                        t.setColor(t.getR()+ 150,t.getG(),t.getB());
                    }
                }
        }
        for (Tile t: aMesh.getTilesList()){
            if (t.getTiletype().equals("land")){
                int elevation = t.getElevation();
                for (Integer c:t.getCorners()){
                    aMesh.getCornersList().get(c).setElevation(elevation);
                }
        }
    }






        IslandMesh Mesh = new IslandMesh(aMesh.getWidth(), aMesh.getHeight(), aMesh.getCornersList(), aMesh.getEdgesList(), aMesh.getTilesList());

        return Mesh;
    }


}
