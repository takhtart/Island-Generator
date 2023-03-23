import java.io.IOException;

import ca.mcmaster.cas.se2aa4.a2.io.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a3.island.islandgen;
import ca.mcmaster.cas.se2aa4.a3.island.ShapesGen.Buildable;
import ca.mcmaster.cas.se2aa4.a3.island.ShapesGen.ShapeFactory;
import ca.mcmaster.cas.se2aa4.a3.island.adt.IslandMesh;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a3.island.exporter.Exporter;
import ca.mcmaster.cas.se2aa4.a3.island.importer.Importer;

public class Main {
    
    public static void main(String[] args) throws IOException {
        Configuration config = new Configuration(args);
        Buildable specification = ShapeFactory.create(config);
        Mesh aMesh = new MeshFactory().read(config.input());
        IslandMesh islandmesh = new Importer().buildADT(aMesh.getVerticesList(), aMesh.getSegmentsList(), aMesh.getPolygonsList());
        IslandMesh theMesh = specification.build(islandmesh);
        IslandMesh island = new islandgen().generateisland(theMesh);
        Mesh enriched = new Exporter().buildStructs(island.getCornersList(), island.getEdgesList(), island.getTilesList());

        
        new MeshFactory().write(enriched, config.export(Configuration.OUTPUT));
    }
    
}
