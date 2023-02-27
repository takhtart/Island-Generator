package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class DotGenTest {

    @Test
    public void meshIsNotNull() {
        IrregMesh generator = new IrregMesh();
        Structs.Mesh aMesh = generator.generateIrregular(20,100,255);
        assertNotNull(aMesh);
    }

}
