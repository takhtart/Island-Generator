package ca.mcmaster.cas.se2aa4.a3.island.altimetricProfiles;

import ca.mcmaster.cas.se2aa4.a3.island.adt.IslandMesh;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;

import java.beans.VetoableChangeSupport;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Elevation {
    private String elevation;

    public Elevation(String elevation){
        this.elevation = elevation;
    }

    public IslandMesh create(IslandMesh aMesh){
        // This code can be simplified with a switch case over the kind of mesh
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
