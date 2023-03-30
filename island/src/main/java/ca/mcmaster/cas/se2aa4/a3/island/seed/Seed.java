package ca.mcmaster.cas.se2aa4.a3.island.seed;
import java.util.Random;

public class Seed {

    private String seed;
    

    public String generate(){
        Random bag = new Random();
        seed = "" + bag.nextInt(100000,999999) + bag.nextInt(100000,999999) + bag.nextInt(100000,999999);
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
        } 
        result[lastInd] = s.substring(j);
    
        return result;
    }
}
