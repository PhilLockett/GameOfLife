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
 * PrimaryController is the class that is responsible for centralizing control.
 * It is instantiated by the FXML loader creates the Model.
 */
package phillockett65.GameOfLife;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import phillockett65.Debug.Debug;

public class PrimaryController {

    // Debug delta used to adjust the local logging level.
    private static final int DD = 0;

    private Model model;

    @FXML
    private VBox root;


    /************************************************************************
     * General support code.
     */



     /************************************************************************
     * Support code for the Initialization of the Controller.
     */

    /**
     * Default constructor.
     * Called by the FXMLLoader().
     */
    public PrimaryController() {
        Debug.trace(DD, "PrimaryController constructed.");
        model = Model.getInstance();
    }

    /**
     * Called by the FXML mechanism to initialize the controller. Called after 
     * the constructor to initialise all the controls.
     */
    @FXML public void initialize() {
        Debug.trace(DD, "PrimaryController initialized.");
        model.initialize();

        initializeCheckBoxes();
        initializeSelections();
        initializeEarthCanvas();
    }

    /**
     * Called by Application after the stage has been set. Completes any 
     * initialization dependent on other components being initialized.
     * 
     * @param mainController used to call the centralized controller.
     */
    public void init(Stage primaryStage) {
        Debug.trace(DD, "PrimaryController init.");
        model.init(primaryStage, this);
        syncUI();
    }

    /**
     * Set the styles based on the focus state.
     * @param state is true if we have focus, false otherwise.
     */
    public void setFocus(boolean state) {
        Model.styleFocus(root, "unfocussed-root", state);
    }

    /**
     * Synchronise all controls with the model. This should be the last step 
     * in the initialisation.
     */
    public void syncUI() {
        live1CheckBox.setSelected(model.isLiveCheck(1));
        live2CheckBox.setSelected(model.isLiveCheck(2));
        live3CheckBox.setSelected(model.isLiveCheck(3));
        live4CheckBox.setSelected(model.isLiveCheck(4));
        live5CheckBox.setSelected(model.isLiveCheck(5));
        live6CheckBox.setSelected(model.isLiveCheck(6));
        live7CheckBox.setSelected(model.isLiveCheck(7));
        live8CheckBox.setSelected(model.isLiveCheck(8));
        birth1CheckBox.setSelected(model.isBirthCheck(1));
        birth2CheckBox.setSelected(model.isBirthCheck(2));
        birth3CheckBox.setSelected(model.isBirthCheck(3));
        birth4CheckBox.setSelected(model.isBirthCheck(4));
        birth5CheckBox.setSelected(model.isBirthCheck(5));
        birth6CheckBox.setSelected(model.isBirthCheck(6));
        birth7CheckBox.setSelected(model.isBirthCheck(7));
        birth8CheckBox.setSelected(model.isBirthCheck(8));
    }






    /************************************************************************
     * Support code for "Check Boxes" panel.
     */

    @FXML
    private CheckBox live1CheckBox;

    @FXML
    private CheckBox live2CheckBox;

    @FXML
    private CheckBox live3CheckBox;

    @FXML
    private CheckBox live4CheckBox;

    @FXML
    private CheckBox live5CheckBox;

    @FXML
    private CheckBox live6CheckBox;

    @FXML
    private CheckBox live7CheckBox;

    @FXML
    private CheckBox live8CheckBox;


    @FXML
    private CheckBox birth1CheckBox;

    @FXML
    private CheckBox birth2CheckBox;

    @FXML
    private CheckBox birth3CheckBox;

    @FXML
    private CheckBox birth4CheckBox;

    @FXML
    private CheckBox birth5CheckBox;

    @FXML
    private CheckBox birth6CheckBox;

    @FXML
    private CheckBox birth7CheckBox;

    @FXML
    private CheckBox birth8CheckBox;


    /**
     * Initialize "Check Boxes" panel.
     */
    private void initializeCheckBoxes() {
        live1CheckBox.setTooltip(new Tooltip("First check box"));
        live2CheckBox.setTooltip(new Tooltip("Second check box"));
        live3CheckBox.setTooltip(new Tooltip("Third check box"));
    }



    /************************************************************************
     * Support code for "Selections" panel.
     */

    @FXML
    private Button fasterButton;

    @FXML
    private Button slowerButton;

    @FXML
    private Button biggerButton;

    @FXML
    private Button smallerButton;

    @FXML
    private Button clearDataButton;

    @FXML
    private Button playButton;


    @FXML
    void fasterButtonActionPerformed(ActionEvent event) {
        Debug.info(DD, "fasterButtonActionPerformed()");

    }

    @FXML
    void slowerButtonActionPerformed(ActionEvent event) {
        Debug.info(DD, "slowerButtonActionPerformed()");

    }

    @FXML
    void biggerButtonActionPerformed(ActionEvent event) {
        Debug.info(DD, "biggerButtonActionPerformed()");

    }

    @FXML
    void smallerButtonActionPerformed(ActionEvent event) {
        Debug.info(DD, "smallerButtonActionPerformed()");

    }

    @FXML
    private void clearDataButtonActionPerformed(ActionEvent event) {
        Debug.info(DD, "clearDataButtonActionPerformed()");
        clearData();
    }

    @FXML
    void playButtonActionPerformed(ActionEvent event) {
        Debug.info(DD, "playButtonActionPerformed()");

    }

    /**
     * Clear all current data to the default settings then update the UI.
     */
    private void clearData() {
        model.defaultSettings();
        syncUI();
    }

    /**
     * Initialize "Selections" panel.
     */
    private void initializeSelections() {
        clearDataButton.setTooltip(new Tooltip("Caution! This irreversible action will reset the form data to default values"));
    }



    /************************************************************************
     * Support code for "Earth" canvas.
     */

    @FXML
    private Canvas earth;

    /**
     * Initialize "Selections" panel.
     */
    private void initializeEarthCanvas() {
    }
}