package ca.mcmaster.cas.se2aa4.a3.island.seed;

import java.util.Map;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a3.island.adt.IslandMesh;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;

public class Seed {

    private final Configuration config;
    private final Map<String, String> options;
    private String seed;
    
    public Seed(Configuration c){
        this.config = c;
        this.options = config.export();
    }

    public String generate(){
        Random bag = new Random();

        if (options.get(Configuration.SHAPE).equals("random")){
            seed = "" + bag.nextInt(100000,999999) + bag.nextInt(100000,999999) + bag.nextInt(100000,999999) + bag.nextInt(100000,999999);
        }
        else{
            seed = "" + bag.nextInt(100000,999999) + bag.nextInt(100000,999999) + bag.nextInt(100000,999999);
        }

        return seed;

    }

    public String getSeedString(){
        return seed;
    }

    public String[] Split(String s, int interval) {
        int arrayLength = (int) Math.ceil(((s.length() / (double)interval)));
        String[] result = new String[arrayLength];
    
        int j = 0;
        int lastInd = result.length - 1;
        for (int i = 0; i < lastInd; i++) {
            result[i] = s.substring(j, j + interval);
            j += interval;
        } //Add the last bit
        result[lastInd] = s.substring(j);
    
        return result;
    }
}
