package adtTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import ca.mcmaster.cas.se2aa4.a3.island.adt.Tile;

public class TileTest {

    Tile t1 = new Tile(null, null, 0, null);
    Tile t2 = new Tile(null, null, 0, null);
    

    @Test
    public void testColor(){
        assertEquals("0,0,0", t1.getStringColor());
        assertNotEquals("0,255,255",t1.getStringColor());
        t1.setColor(0, 255, 255);
        assertEquals(0,t1.getR());
        assertEquals(255,t1.getG());
        assertEquals(255,t1.getB());
        assertNotEquals(255, t1.getR());
        assertNotEquals(0, t1.getG());
        assertNotEquals(0, t1.getB());
        assertEquals("0,255,255",t1.getStringColor());
        assertNotEquals("0,0,0",t1.getStringColor());
  
    }

    @Test
    public void testMarked(){
        t1.setMarked();
        t2.setMarked();
        t2.setUnmarked();
        assertEquals(true,t1.isMarked());
        assertEquals(false,t2.isMarked());
        assertNotEquals(false, t1.isMarked());
        assertNotEquals(true, t2.isMarked());
    }

    @Test
    public void testBiome(){
        assertEquals("",t1.getBiome());
        t1.setBiome("taiga");
        assertEquals("taiga",t1.getBiome());
        assertNotEquals("",t1.getBiome());
        t1.setBiome("Arctic_Tundra");
        assertEquals("Arctic_Tundra",t1.getBiome());
        assertNotEquals("taiga",t1.getBiome());
        assertNotEquals("",t1.getBiome());
    }

    @Test
    public void testTileType(){
        t1.setTileType("ocean");
        assertEquals("ocean",t1.getTiletype());
        assertNotEquals("land",t1.getTiletype());
        t1.setTileType("land");
        assertEquals("land",t1.getTiletype());
        assertNotEquals("ocean",t1.getTiletype());
    }

    @Test
    public void testElevation(){
        t1.setElevation(5);
        assertEquals(5,t1.getElevation());
        assertNotEquals(1,t1.getElevation());
        t1.setElevation(1);
        assertEquals(1,t1.getElevation());
        assertNotEquals(5,t1.getElevation());
    }

    @Test
    public void testCentroid(){
        final Tile t3 = new Tile(null, null, 5, null);
        assertEquals(5,t3.getCentroidIdx());
        assertNotEquals(0,t3.getCentroidIdx()); 
    }

    @Test
    public void testAquifer(){
        assertEquals(false,t1.isAquifer());
        t2.setAquifer();
        assertEquals(true,t2.isAquifer());
        assertNotEquals(false,t2.isAquifer());
    }

    @Test
    public void testHumidity(){
        assertEquals(1,t1.getHumidity());
        t1.setHumidity(4);
        assertEquals(4,t1.getHumidity());
        assertNotEquals(1,t1.getHumidity());
        t1.addHumidity(1);
        assertEquals(5,t1.getHumidity());
        assertNotEquals(4,t1.getHumidity());
        assertNotEquals(1,t1.getHumidity());
    }

}
