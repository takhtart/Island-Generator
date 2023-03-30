package ca.mcmaster.cas.se2aa4.a3.island.altimetricProfiles;

import ca.mcmaster.cas.se2aa4.a3.island.adt.IslandMesh;

public class Elevation {
    private String elevation;

    public Elevation(String elevation){
        this.elevation = elevation;
    }

    public IslandMesh create(IslandMesh aMesh){
        switch (this.elevation){
            case "volcano":
                return volcano.setElevation(aMesh);
            case "hills":
                return hills.setElevation(aMesh);
            case "artic":
                return artic.setElevation(aMesh); 
            default:
                return volcano.setElevation(aMesh);
        }
    }
}
