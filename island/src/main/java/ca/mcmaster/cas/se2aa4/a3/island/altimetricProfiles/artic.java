package ca.mcmaster.cas.se2aa4.a3.island.altimetricProfiles;

import ca.mcmaster.cas.se2aa4.a3.island.adt.*;

public class artic {
    private int levels;

    public artic(int levels){
        this.levels = levels;
    }

    public IslandMesh setElevation(IslandMesh aMesh){


















        IslandMesh Mesh = new IslandMesh(aMesh.getWidth(), aMesh.getHeight(), aMesh.getCornersList(), aMesh.getEdgesList(), aMesh.getTilesList());

        return Mesh;
    }


}
