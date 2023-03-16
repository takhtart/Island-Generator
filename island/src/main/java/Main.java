import java.io.IOException;

import ca.mcmaster.cas.se2aa4.a2.io.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a3.island.islandgen;
import ca.mcmaster.cas.se2aa4.a3.island.ShapesGen.Buildable;
import ca.mcmaster.cas.se2aa4.a3.island.ShapesGen.ShapeFactory;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;

public class Main {
    
    public static void main(String[] args) throws IOException {
        Configuration config = new Configuration(args);
        Buildable specification = ShapeFactory.create(config);
        Mesh aMesh = new MeshFactory().read(config.input());
        Mesh theMesh = specification.build(aMesh);
        islandgen island = new islandgen();
        Mesh enriched = island.generateisland(theMesh);
        
        new MeshFactory().write(enriched, config.export(Configuration.OUTPUT));
    }
    
}
