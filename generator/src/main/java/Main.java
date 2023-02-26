import ca.mcmaster.cas.se2aa4.a2.generator.DotGen;
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
        options.addOption("h", false, "help");
        options.addOption("help", false, "help");
      
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        DotGen generator = new DotGen();
        Mesh myMesh = generator.generateIrregular(20,100);
        if(cmd.hasOption("grid")) {
            GridMesh gen = new GridMesh(); 
            int polygons = 625;
            if(cmd.hasOption("polygons")){
                polygons = Integer.parseInt(cmd.getOptionValue("polygons"));
            }
            
            myMesh = gen.generate(polygons);
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
            myMesh = generator.generateIrregular(relaxations, polygons);
        }
        if(cmd.hasOption("help") || cmd.hasOption("h")){
            System.out.println("-grid   displays a grid mesh");
            System.out.println("-polygons (number of polygons)  displays number of polygons given");
            System.out.println("-relax (number of relaxations)  displays number of relaxations given");
        }
        MeshFactory factory = new MeshFactory();
        factory.write(myMesh, args[0]);
    }

}
