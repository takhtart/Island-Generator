package ca.mcmaster.cas.se2aa4.a3.island.altimetricProfiles;

import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a3.island.noise.Noise;
import ca.mcmaster.cas.se2aa4.a3.island.ShapesGen.NoiseGen;
import ca.mcmaster.cas.se2aa4.a3.island.adt.*;

public class hills {
    public static IslandMesh setElevation(IslandMesh aMesh){
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
