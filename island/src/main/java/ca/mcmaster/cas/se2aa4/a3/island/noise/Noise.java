package ca.mcmaster.cas.se2aa4.a3.island.noise;
import java.util.Random;

import de.articdive.jnoise.generators.noise_parameters.fade_functions.FadeFunction;
import de.articdive.jnoise.generators.noise_parameters.interpolation.Interpolation;
import de.articdive.jnoise.pipeline.JNoise;
public class Noise {

    JNoise noise = null;
    private int noiseSeed;

    public void generate(int seed){
        System.out.println("Seed: " + seed);
        JNoise noisePipeline  = JNoise.newBuilder().perlin(seed,Interpolation.COSINE,FadeFunction.IMPROVED_PERLIN_NOISE).clamp(0.0, 1.0).scale(1/400.0).build();

        noise = noisePipeline;
        noiseSeed = seed;
    }

    public void generateElevation(int seed){
        JNoise noisePipeline  = JNoise.newBuilder().perlin(seed,Interpolation.COSINE,FadeFunction.IMPROVED_PERLIN_NOISE).clamp(0.0, 1.0).scale(1/397.0).build();

        noise = noisePipeline;
    }

    public void generate(){
     
        Random bag = new Random();
        int seed = bag.nextInt(3000,999999999);
        System.out.println("Seed: " + seed);
        JNoise noisePipeline  = JNoise.newBuilder().perlin(seed,Interpolation.COSINE,FadeFunction.IMPROVED_PERLIN_NOISE).clamp(0.0, 1.0).scale(1/400.0).build(); 

        noise = noisePipeline;
        noiseSeed = seed;
    }

    public double getValue(double x, double y){
        double noiseval = noise.evaluateNoise(x,y);
        return noiseval;
    }

    public int getSeed(){
        return noiseSeed; 
    }


}

