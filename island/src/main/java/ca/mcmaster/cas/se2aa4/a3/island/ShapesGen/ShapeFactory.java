package ca.mcmaster.cas.se2aa4.a3.island.ShapesGen;

import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;

import java.util.HashMap;
import java.util.Map;

public class ShapeFactory {

    private static final Map<String, Class> bindings = new HashMap<>();

    static {
        bindings.put("lagoon", LagoonGen.class);
        bindings.put("random", NoiseGen.class);
        //bindings.put("yinyang", YinYangGen.class);
        //bindings.put("cross", Cross.class);
    }

    public static Buildable create(Configuration configuration) {
        Map<String, String> options = configuration.export();
        // This code can be simplified with a switch case over the kind of mesh
        try {
            Class klass = bindings.get(options.get(Configuration.SHAPE));
            return (Buildable) klass.getDeclaredConstructor(Map.class).newInstance(options);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

}
