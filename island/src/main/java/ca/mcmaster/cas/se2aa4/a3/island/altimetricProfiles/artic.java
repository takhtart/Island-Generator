package ca.mcmaster.cas.se2aa4.a3.island.altimetricProfiles;

import ca.mcmaster.cas.se2aa4.a3.island.adt.*;

public class artic{
    public static IslandMesh setElevation(IslandMesh aMesh){
        double centerX = aMesh.getHeight() / 2;
        double centerY = aMesh.getWidth() / 2;

        for (Tile t: aMesh.getTilesList()){
            if (t.getTiletype().equals("land")){
                double centroidX = aMesh.getCorner(t.getCentroidIdx()).getX();
                double centroidY = aMesh.getCorner(t.getCentroidIdx()).getY();

                double distance = Math.sqrt(Math.pow(centroidX - centerX,2) + Math.pow(centroidY- centerY,2));

                for (int i = 4; i >= 1; i--){
                    if (distance <= (100*i)){
                        t.setElevation(i);
                    }
                    else if (distance > 400){
                        t.setElevation(4);
                    }
                }

                }
        }
        for (Tile t: aMesh.getTilesList()){
            if (t.getTiletype().equals("land")){
                int elevation = t.getElevation();
                for (Integer c:t.getCorners()){
                    aMesh.getCornersList().get(c).setElevation(elevation);
                }
        }
    }






        IslandMesh Mesh = new IslandMesh(aMesh.getWidth(), aMesh.getHeight(), aMesh.getCornersList(), aMesh.getEdgesList(), aMesh.getTilesList());

        return Mesh;
    }


}
