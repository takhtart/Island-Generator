package adtTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import ca.mcmaster.cas.se2aa4.a3.island.adt.Corner;

public class CornerTest {
    
    @Test
    public void testXY(){
        final Corner c = new Corner(20.0, 23.0);
        assertEquals(20.0, c.getX());
        assertEquals(23.0, c.getY());

        assertNotEquals(23.0, c.getX());
        assertNotEquals(20.0, c.getY());
    }

    @Test
    public void Elevation(){
        final Corner c = new Corner(20.0, 23.0);
        c.setElevation(1);
        assertEquals(1, c.getElevation());
        assertNotEquals(3, c.getElevation());
    }

    @Test
    public void landCorner(){
        final Corner c = new Corner(20.0, 23.0);
        assertEquals(false, c.isLandCorner());
        assertNotEquals(true, c.isLandCorner());
        c.setLandCorner();
        assertEquals(true, c.isLandCorner());
        assertNotEquals(false, c.isLandCorner());
    }

    @Test
    public void Color(){
        final Corner c = new Corner(20.0, 23.0);
        assertEquals("0,0,0", c.getStringColor());
        assertNotEquals("50,50,50", c.getStringColor());
        c.setColor(50,50,50);
        assertEquals("50,50,50", c.getStringColor());
        assertNotEquals("0,0,0", c.getStringColor());
    }

}
