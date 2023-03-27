package ca.mcmaster.cas.se2aa4.a3.island.configuration;

import org.apache.commons.cli.*;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    public static final String INPUT = "i";
    public static final String OUTPUT = "o";
    public static final String SHAPE = "shape";
    public static final String HELP = "help";
    public static final String MODE = "mode";
    public static final String INNERRADIUS = "ir";
    public static final String OUTERRADIUS = "or";
    public static final String RADIUS = "r";
    public static final String NOISESEED = "ns";
    public static final String LAGOON = "lagoon";
    public static final String LAKE = "lakes";
    public static final String AQUIFERS = "aquifers";
    public static final String ELEVATIONLEVEL = "el";
    public static final String BIOME = "biome";


    private CommandLine cli;

    public Configuration(String[] args) {
        try {
            this.cli = parser().parse(options(), args);
            if (cli.hasOption(HELP)) {
                help();
            }
        } catch (ParseException pe) {
            throw new IllegalArgumentException(pe);
        }
    }

    public String input() {
        return this.cli.getOptionValue(INPUT);
    }

    public String output() {
        return this.cli.getOptionValue(OUTPUT, "output.svg");
    }

    public String shape() {
        return this.cli.getOptionValue(SHAPE);
    }

    public String innerradius() {
        return this.cli.getOptionValue(INNERRADIUS);
    }

    public String outerradius() {
        return this.cli.getOptionValue(OUTERRADIUS);
    }

    public Boolean lagoon(){
        return this.cli.hasOption(LAGOON) == true;
    }
    public String lake(){
        return this.cli.getOptionValue(LAKE);
    }
    public String aquifer(){
        return this.cli.getOptionValue(AQUIFERS);
    }
    public String elevationLevel(){
        return this.cli.getOptionValue(ELEVATIONLEVEL);
    }
    public String biome(){
        return this.cli.getOptionValue(BIOME);
    }

    private void help() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar island.jar", options());
        System.exit(0);
    }

    public Map<String, String> export() {
        Map<String, String> result = new HashMap<>();
        for(Option o: cli.getOptions()){
            result.put(o.getOpt(), o.getValue(""));
        }
        return result;
    }

    public String export(String key) {
        return cli.getOptionValue(key);
    }

    private CommandLineParser parser() {
        return new DefaultParser();
    }

    private Options options() {
        Options options = new Options();
        options.addOption(new Option(INPUT, true, "Input Mesh"));
        options.addOption(new Option(OUTPUT, true, "Output File Name"));
        options.addOption(new Option(SHAPE, true, "Island Type"));
        options.addOption(new Option(INNERRADIUS, true, "Inner Radius"));
        options.addOption(new Option(OUTERRADIUS, true, "Outer Radius"));
        options.addOption(new Option(RADIUS, true, "Single Radius"));
        options.addOption(new Option(NOISESEED, true, "NoiseGen Seed"));
        options.addOption(new Option(LAGOON, false, "Generate Lagoon"));
        options.addOption(new Option(LAKE, true, "Generate Lakes"));
        options.addOption(new Option(AQUIFERS, true, "Generate Aquifers"));
        options.addOption(new Option(ELEVATIONLEVEL, true, "Range of elevation levels that exist"));
        options.addOption(new Option(BIOME, true, "Whittiker Selection"));
        // Global help
        options.addOption(new Option(HELP, false, "print help message"));
        return options;
    }

}
