import java.io.IOException;

import ca.mcmaster.cas.se2aa4.a2.io.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a3.island.islandgen;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;

public class Main {
    
    public static void main(String[] args) throws IOException {
        Configuration config = new Configuration(args);
        Mesh aMesh = new MeshFactory().read(config.input());
        islandgen island = new islandgen();
        Mesh enriched = island.generateisland(aMesh, "circle");
        
        new MeshFactory().write(enriched, config.export(Configuration.OUTPUT));
    }
    
}
