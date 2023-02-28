import ca.mcmaster.cas.se2aa4.a2.generator.IrregMesh;
import ca.mcmaster.cas.se2aa4.a2.generator.GridMesh;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import org.apache.commons.cli.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        
        Options options = new Options();
        options.addOption("grid", false, "display grid mesh");
        options.addOption("polygons", true, "number of polygons");
        options.addOption("relax", true, "number of relaxations");
        options.addOption("transparency", true, "transparency alpha");
        options.addOption("h", false, "help");
        options.addOption("help", false, "help");
      
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
         IrregMesh generator = new IrregMesh();
        Mesh myMesh = generator.generateIrregular(20,100,255);
        int transparencyAlpha = 255;
        if(cmd.hasOption("transparency")) {
            transparencyAlpha = Integer.parseInt(cmd.getOptionValue("transparency"));
        }
        if(cmd.hasOption("grid")) { 
            GridMesh gen = new GridMesh(); 
            int polygons = 625;
            if(cmd.hasOption("polygons")){
                polygons = Integer.parseInt(cmd.getOptionValue("polygons"));
            }
            
            myMesh = gen.generate(polygons, transparencyAlpha);
        }
        else{
            int relaxations = 20;
            int polygons = 100;
            if(cmd.hasOption("relaxation")){
                relaxations = Integer.parseInt(cmd.getOptionValue("polygons"));
            }
            if(cmd.hasOption("polygons")){
                polygons = Integer.parseInt(cmd.getOptionValue("polygons"));
            }
            myMesh = generator.generateIrregular(relaxations, polygons, transparencyAlpha);
        } 
        
        if(cmd.hasOption("help") || cmd.hasOption("h")){
            
            System.out.println("\n ----------------------------------------------------");
            System.out.println("-grid                                           displays a grid mesh");
            System.out.println("-polygons (number of polygons)                  displays number of polygons given (must be square number for grid mesh) ");
            System.out.println("-relax (number of relaxations)                  displays number of relaxations given (irregular mesh only)");
            System.out.println("-transparency (transparency number)             displays transparency of colors (0-255)");
            System.out.println("--------------------------------------------------------- \n");
        }   
        MeshFactory factory = new MeshFactory();
        factory.write(myMesh, args[0]);
    }

}
