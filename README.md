# GameOfLife
'GameOfLife' is a JavaFX implementation of Conway's Game of Life.

**USE AT OWN RISK.**

## Overview
This project has been set up as a Maven project that uses JavaFX, FXML and 
CSS to render the GUI.
Maven can be run from the command line as shown below.
Maven resolves dependencies and builds the application independently of an IDE.

A description of 'Conway's Game of Life' can be found 
[here](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life).

There are three main sections to this application:
 * the 'Rules' panel
 * the 'Controls' panel
 * the grid

### The 'Rules' panel
The 'Rules' panel contains numerous checkboxes.
Initially these checkboxes reflect the standard rules for remaining alive and
being born.
However, these checkboxes allow the rules to be changed.
The tool-tips indicate the current rule definition.

### The 'Controls' panel
The 'Controls' panel contains a number of buttons, most of which are self 
explanitory.
The 'Reset' button returns the rules, speed of evolution and cell size back to 
the default values.
The 'Shadow' button displays the state of living cells in a more subtle manner.

### The Grid
The grid is the chequered section which hosts the living cells.
Mouse clicks toggle the cells between live and dead.
Pressing the 'Play' button will cause the live cells to evolve using the 
current rules.

## Dependencies
'GameOfLife' is dependent on the following:

  * Java 15.0.1
  * Apache Maven 3.6.3

The code is structured as a standard Maven project which requires Maven and a 
JDK to be installed. A quick web search will help, alternatively
[Oracle](https://www.java.com/en/download/) and 
[Apache](https://maven.apache.org/install.html) should guide you through the
install.

Also [OpenJFX](https://openjfx.io/openjfx-docs/) can help set up your 
favourite IDE to be JavaFX compatible, however, Maven does not require this.

## Cloning and Running
The following commands clone and execute the code:

	git clone https://github.com/PhilLockett/GameOfLife.git
	cd GameOfLife/
	mvn clean javafx:run

## Points of interest
This code has the following points of interest:

  * GameOfLife is a Maven project that uses JavaFX.
  * GameOfLife is styled with CSS.
  * GameOfLife is structured as an MVC project (FXML being the Video component).
  * Multi stage initialization minimizes the need for null checks. 
  * Data persistence is provided by the Serializable DataStore object.
  * The GUI is implemented in FXML using SceneBuilder.
  * A static Debug object helps control diagnostic output.
