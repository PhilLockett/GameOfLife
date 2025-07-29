# GameOfLife
'GameOfLife' is a JavaFX application 'framework' that uses Maven, FXML and CSS.

**USE AT OWN RISK.**

## Overview
This project has been set up as a Maven project that uses JavaFX, FXML and 
CSS to render the GUI. Maven can be run from the command line as shown below.
Maven resolves dependencies and builds the application independently of an IDE.

The intention of this application is to provide a 'framework' from which other
applications can be derived. It includes a number of different controls as 
examples that can be cloned and modified as needed.

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

## Customization
Once cloned, the following files should be modified:
  * README.md
  * pom.xml
  * src/main/java/phillockett65/GameOfLife/App.java
  * src/main/java/phillockett65/GameOfLife/DataStore.java
  * src/main/java/phillockett65/GameOfLife/Model.java
  * src/main/java/phillockett65/GameOfLife/PrimaryController.java
  * src/main/resources/phillockett65/GameOfLife/primary.fxml

In the above files, the following changes need to be made:
  * change all occurrences of 'GameOfLife' to the new Application name
  * change all occurrences of 'phillockett65' to your domain

Finally rename the directories/folders in the same way:
  * change all occurrences of 'GameOfLife' to the new Application name
  * change all occurrences of 'phillockett65' to your domain

The GUI layout can be modified as desired by editing the 'primary.fxml' file. 
The SceneBuilder application makes editing the layout easier than modifiying 
'primary.fxml' directly.

FXML also uses cascading style sheets for the presentation. To change the 
colours and fonts used, edit the 'application.css' file.

## Points of interest
This code has the following points of interest:

  * GameOfLife is a Maven project that uses JavaFX.
  * GameOfLife provides multiple examples of different types of JavaFX controls.
  * GameOfLife is styled with CSS.
  * GameOfLife is structured as an MVC project (FXML being the Video component).
  * Multi stage initialization minimizes the need for null checks. 
  * Data persistence is provided by the Serializable DataStore object.
  * The GUI is implemented in FXML using SceneBuilder.
  * A static Debug object helps control diagnostic output.
  * The Command Pattern is used to support an Undo/Redo mechanism.
