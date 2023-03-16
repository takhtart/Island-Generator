package ca.mcmaster.cas.se2aa4.a3.island.noise;
import java.util.Random;

import de.articdive.jnoise.generators.noise_parameters.fade_functions.FadeFunction;
import de.articdive.jnoise.generators.noise_parameters.interpolation.Interpolation;
import de.articdive.jnoise.generators.noisegen.perlin.PerlinNoiseGenerator;
import de.articdive.jnoise.modules.octavation.fractal_functions.FractalFunction;
import de.articdive.jnoise.pipeline.JNoise;
public class Noise {

    JNoise noise = null;

    public void generate(int seed){
        //PerlinNoiseGenerator perlinCosine=PerlinNoiseGenerator.newBuilder().setSeed(3301).setInterpolation(Interpolation.COSINE).build();
        System.out.println("Seed: " + seed);
        JNoise noisePipeline  = JNoise.newBuilder().perlin(seed,Interpolation.COSINE,FadeFunction.IMPROVED_PERLIN_NOISE).clamp(0.0, 1.0).scale(1/400.0).build();
        JNoise noisePipeline2 = JNoise.newBuilder().octavation(noisePipeline,4,1,1,FractalFunction.FBM,false).build();
        

        noise = noisePipeline;
    }

    public void generate(){
        //PerlinNoiseGenerator perlinCosine=PerlinNoiseGenerator.newBuilder().setSeed(3301).setInterpolation(Interpolation.COSINE).build();
        Random bag = new Random();
        int seed = bag.nextInt(3000,999999999);
        System.out.println("Seed: " + seed);
        JNoise noisePipeline  = JNoise.newBuilder().perlin(seed,Interpolation.COSINE,FadeFunction.IMPROVED_PERLIN_NOISE).clamp(0.0, 1.0).scale(1/400.0).build();
        JNoise noisePipeline2 = JNoise.newBuilder().octavation(noisePipeline,4,1,1,FractalFunction.FBM,false).build();
        

        noise = noisePipeline;
    }

    public double getValue(double x, double y){
        double noiseval = noise.evaluateNoise(x,y);
        //System.out.println(noise);
        return noiseval;
    }


}

