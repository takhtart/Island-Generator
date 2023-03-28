package ca.mcmaster.cas.se2aa4.a3.island.altimetricProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcmaster.cas.se2aa4.a3.island.noise.Noise;
import ca.mcmaster.cas.se2aa4.a3.island.ShapesGen.NoiseGen;
import ca.mcmaster.cas.se2aa4.a3.island.adt.*;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;

public class hills implements ElevationBuildable{
    hills(Map<String, String> options){
        this(Integer.parseInt(options.getOrDefault(Configuration.ELEVATIONLEVEL, "4")));

        System.out.print(options);
    }

    private int levels;

    public hills(int levels){
        this.levels = levels;
    }

    public IslandMesh setElevation(IslandMesh aMesh){
        List<Tile> tilesWithElevation = new ArrayList<>(aMesh.getTilesList());
        List<Corner> cornersWithElevation = new ArrayList<>(aMesh.getCornersList());


        int seed = NoiseGen.fSeed;

        Noise noise = new Noise();
        noise.generateElevation(seed);

        double maxNoise = 0.0;
        for (Tile m: tilesWithElevation){
            if (m.getTiletype().equals("land")){
                double centroidX = aMesh.getCorner(m.getCentroidIdx()).getX();
                double centroidY = aMesh.getCorner(m.getCentroidIdx()).getY();

                double noiseval = noise.getValue(centroidX, centroidY);
                if (noiseval > maxNoise){
                    maxNoise = noiseval;
                }
            }
        }
        System.out.println(maxNoise);

        double scale = maxNoise/4;
        for (Tile t: tilesWithElevation){
            if (t.getTiletype().equals("land")){
                double centroidX = aMesh.getCorner(t.getCentroidIdx()).getX();
                double centroidY = aMesh.getCorner(t.getCentroidIdx()).getY();

                double noiseval = noise.getValue(centroidX, centroidY);
                //System.out.println(noiseval);
                for (int i = 5; i >= 1; i--){
                    if (noiseval <= (i*scale)){
                        t.setElevation(i);
                    }
                }

                /* if (t.getElevation() == 1){
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
                }  */
            }

        }

        for (Tile t: tilesWithElevation){
            if (t.getTiletype().equals("land")){
                int elevation = t.getElevation();
                for (Integer c:t.getCorners()){
                    cornersWithElevation.get(c).setElevation(elevation);
                }
        }
    }


        IslandMesh Mesh = new IslandMesh(aMesh.getWidth(), aMesh.getHeight(), cornersWithElevation, aMesh.getEdgesList(), tilesWithElevation);

        return Mesh;
    }


}
