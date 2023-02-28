# Assignment A2: Mesh Generator

  - Author #1 [takhtart@mcmaster.ca]
  - Author #2 [sok13@mcmaster.ca]
  - Author #3 [kugana7@mcmaster.ca]

## How to run the product

Grid Mesh Procedure:
`mvn package`
`java -jar generator/target/2aa4.mesh.generator-jar-with-dependencies.jar generator/sample.mesh -grid`
`java -jar visualizer/target/2aa4.mesh.visualizer-jar-with-dependencies.jar generator/sample.mesh visualizer/sample.svg`

Description/Scenario:
In order to generate a full grid mesh, the user would need to first ensure they are in the correct directory of `a2---mesh-generator-team-38/`.Then type the command `mvn package`. Next, the generator can be ran using the command `java -jar generator/target/2aa4.mesh.generator-jar-with-dependencies.jar generator/sample.mesh -grid`. Finally, the visualizer can be ran by typing in `java -jar visualizer/target/2aa4.mesh.visualizer-jar-with-dependencies.jar generator/sample.mesh visualizer/sample.svg`, and the user would be able to find a full grid mesh consisting of a default of 25 by 25 polygons. If the user wishes to specify the number of polygons to be generated, a `-polygons (insert number of polygons)` flag can be added when the generator command is called. Furthermore, if the user wishes to change the transparency of the colors of the segments, vertices and polygons, a `-transparency (transparency number)` flag can be added when the generator command is called. The transparency number ranges from 0-255. Additionally, debug mode can be switched into using the `-X` flag when running the visualizer command. Ensure all 3 command lines in the procedure are ran when adding or removing any flags.

Irregular Mesh Procedure:
`mvn package`
`java -jar generator/target/2aa4.mesh.generator-jar-with-dependencies.jar generator/sample.mesh`
`java -jar visualizer/target/2aa4.mesh.visualizer-jar-with-dependencies.jar generator/sample.mesh visualizer/sample.svg`

Description/Scenario:
In order to generate an irregular mesh, follow the previous procedure but remove the `-grid` flag before you call the generator. The user would then be able to observe an irregular mesh consisting of a default of 100 polygons and a relaxation of 20. Once again, the `-polygons (insert number of polygons)` flag can be used when running the generator. If the user wishes to change the relaxation of the grid, the `-relax (insert number of relxations)` flag can be used when running the generator. `-transparency (transparency number)` and debug mode `-X` can also be used for the irregular mesh when running the generator and visualizer respectively. Ensure all 3 command lines in the procedure are ran when adding or removing any flags.

### Installation instructions

This product is handled by Maven, as a multi-module project. We assume here that you have cloned the project in a directory named `A2`

To install the different tooling on your computer, simply run:

```
mosser@azrael A2 % mvn install
```

After installation, you'll find an application named `generator.jar` in the `generator` directory, and a file named `visualizer.jar` in the `visualizer` one. 

### Generator

To run the generator, go to the `generator` directory, and use `java -jar` to run the product. The product takes one single argument (so far), the name of the file where the generated mesh will be stored as binary.

```
mosser@azrael A2 % cd generator 
mosser@azrael generator % java -jar generator.jar sample.mesh
mosser@azrael generator % ls -lh sample.mesh
-rw-r--r--  1 mosser  staff    29K 29 Jan 10:52 sample.mesh
mosser@azrael generator % 
```

### Visualizer

To visualize an existing mesh, go the the `visualizer` directory, and use `java -jar` to run the product. The product take two arguments (so far): the file containing the mesh, and the name of the file to store the visualization (as an SVG image).

```
mosser@azrael A2 % cd visualizer 
mosser@azrael visualizer % java -jar visualizer.jar ../generator/sample.mesh sample.svg

... (lots of debug information printed to stdout) ...

mosser@azrael visualizer % ls -lh sample.svg
-rw-r--r--  1 mosser  staff    56K 29 Jan 10:53 sample.svg
mosser@azrael visualizer %
```
To viualize the SVG file:

  - Open it with a web browser
  - Convert it into something else with tool slike `rsvg-convert`

## How to contribute to the project

When you develop features and enrich the product, remember that you have first to `package` (as in `mvn package`) it so that the `jar` file is re-generated by maven.

## Backlog

### Definition of Done

-- Feature is Fully Implemented And Tested --

### Product Backlog

| Id | Feature title | Who? | Start | End | Status |
|:--:|---------------|------|-------|-----|--------|
| F01 | Draw Segments Between Vertices To Visualize The Squares | Team | 02/01/2023 | 02/02/2023 | D |
| F02 | Display Segments As The Averages Of The Vertices It's Connected To | Tarnveer | 02/06/2023 | 02/07/2023 | D |
| F03 | Add transparency to colours | Kyen, Tarnveer |02/08/2023 | 02/08/2023 | D |
| F04 | Create polygons for all squares | Team | 02/08/2023 | 02/13/2023 | D |
| F05 | Create centroids for all polygons | Aswin, Kyen | 02/13/2023 | 02/13/2023 | D |
| F06 | Create neighboring indexes for all polygons | Aswin | 02/16/2023| 02/20/2023 | D |
| F07 | Create visualization mode | Aswin, Kyen | 02/20/2023|02/23/2023| D |
| F08 | Generate Random Points For Irregular Mesh | Tarnveer | 02/23/2023 | 02/23/2023 | D |
| F09 | Compute Voronoi Diagram | Kyen | 02/23/2023| 02/24/2023 | D |
| F10 | Change mesh using Lloyd Relaxation | Aswin | 02/24/2023| 02/24/2023 | D | 
| F11 | Delaunay triangulation for neighbouring relationships | Kyen | 02/24/2023 | 02/25/2023 | D |


