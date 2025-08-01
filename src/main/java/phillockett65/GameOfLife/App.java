/*  GameOfLife - a JavaFX application 'framework' that uses Maven, FXML and CSS.
 *
 *  Copyright 2025 Philip Lockett.
 *
 *  This file is part of GameOfLife.
 *
 *  GameOfLife is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  GameOfLife is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with GameOfLife.  If not, see <https://www.gnu.org/licenses/>.
 */

/*
 * Boilerplate code responsible for launching the JavaFX application. 
 */
package phillockett65.GameOfLife;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("primary.fxml"));

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        PrimaryController controller = fxmlLoader.getController();

        ObservableList<Image> icons = stage.getIcons();
        icons.add(new Image(getClass().getResourceAsStream("icon32.png")));

        // root.getStylesheets().add(App.class.getResource("application.css").toExternalForm());

        stage.setTitle("Game Of Life 1.0");
        stage.setOnCloseRequest(e -> Platform.exit());
        // stage.resizableProperty().setValue(false);
        stage.initStyle(StageStyle.DECORATED);
        stage.setScene(scene);

        stage.setMinWidth(300); // 284
        stage.setMinHeight(550);// 511

        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                System.out.println("Width: " + newSceneWidth);
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                System.out.println("Height: " + newSceneHeight);
            }
        });

        stage.focusedProperty().addListener((obs, oldVal, newVal) -> 
            controller.setFocus(newVal));

        controller.init(stage, scene);
        
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        // Write current state to disc using the latest version of the DataStore.
        DataStore1.writeData();
    }

    public static void main(String[] args) {
        launch();
    }

}