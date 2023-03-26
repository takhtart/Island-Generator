package ca.mcmaster.cas.se2aa4.a3.island.ShapesGen;

import java.util.Map;

import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a3.island.noise.Noise;
import ca.mcmaster.cas.se2aa4.a3.island.adt.*;

public class NoiseGen implements Buildable{
    private int noiseSeed;
    public static int fSeed;

    public NoiseGen(int noiseSeed) {
        this.noiseSeed = noiseSeed;
    }

    
    NoiseGen(Map<String, String> options){
        this(Integer.parseInt(options.getOrDefault(Configuration.NOISESEED, "-1")));

        System.out.print(options);
    }

    public IslandMesh build(IslandMesh aMesh){

        System.out.println("Selected Noisy Gen");
        
        Noise noise = new Noise();
        if (noiseSeed <= 0){
            noise.generate();
            this.noiseSeed = noise.getSeed();
        }
        else {
            noise.generate(noiseSeed);
        }
        fSeed = noise.getSeed();
        
        int width = 1000;
        int length = 1000;
        int centerX = length/2;
        int centerY = width/2;
        

        for (Tile t: aMesh.getTilesList()){
            double centroidX = aMesh.getCorner(t.getCentroidIdx()).getX();
            double centroidY = aMesh.getCorner(t.getCentroidIdx()).getY();
            double distance = Math.sqrt(Math.pow((centroidX-centerX),2)+Math.pow((centroidY-centerY),2));

            double noiseval = noise.getValue(centroidX, centroidY)*(1/distance);
            //System.out.println(noiseval);
            if(centroidX > 100 && centroidX < width - 100 && centroidY > 100 && centroidY < length - 100){
                if (noiseval > 0.0){
                    //String colorCode = 45 + "," + 173 + "," + 79;
                    t.setColor(45, 173, 79);
                    t.setTileType("land");
    
                }
                else{
                    //String colorCode = 45 + "," + 49 + "," + 173;
                    t.setColor(45, 49, 173);
                    t.setTileType("ocean");
                }
            }
             else{
                //String colorCode = 45 + "," + 49 + "," + 173;
                t.setColor(45, 49, 173);
                t.setTileType("ocean");
            }

        }

        IslandMesh Mesh = new IslandMesh(aMesh.getWidth(), aMesh.getHeight(), aMesh.getCornersList(), aMesh.getEdgesList(), aMesh.getTilesList());

        return Mesh;

    }
}
