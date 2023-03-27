package adtTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import ca.mcmaster.cas.se2aa4.a3.island.adt.Edge;

public class EdgeTest {

    @Test
    public void GetIDX(){
        final Edge e = new Edge(1,2);
        assertEquals(1, e.getV1Idx());
        assertEquals(2, e.getV2Idx());
        assertNotEquals(2, e.getV1Idx());
        assertNotEquals(1, e.getV2Idx());
  
    }

    @Test
    public void edgeType(){
        final Edge e = new Edge(1,2);
        e.setEdgeType("default");
        assertEquals("default", e.getEdgeType());
        assertNotEquals("non-default", e.getEdgeType());
  
    }

    @Test
    public void marked(){
        final Edge e = new Edge(1,2);
        assertEquals(false, e.isMarked());
        assertNotEquals(true, e.isMarked());
        e.setMarked();
        assertEquals(true, e.isMarked());
        assertNotEquals(false, e.isMarked());
  
    }

    @Test
    public void color(){
        final Edge e = new Edge(1,2);
        assertEquals("0,0,0", e.getStringColor());
        assertNotEquals("50,50,50", e.getStringColor());
        e.setColor(50,50,50);
        assertEquals("50,50,50", e.getStringColor());
        assertNotEquals("0,0,0", e.getStringColor());
  
    }

    @Test
    public void thickness(){
        final Edge e = new Edge(1,2);
        e.setThickness(2);
        assertEquals(2, e.getThickness());
        assertNotEquals(1, e.getThickness());
  
    }
    
}
