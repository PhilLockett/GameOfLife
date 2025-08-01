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

import java.util.ArrayList;

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

    private int idToIndex(String id) {
        return Integer.valueOf(id.substring(1));
    }


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
        initializeControls();
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
     * Synchronise all controls with the model. This should be the last step 
     * in the initialisation.
     */
    public void syncUI() {
        for (int i = 0; i < liveCheckBoxes.size(); ++i) {
            liveCheckBoxes.get(i).setSelected(model.isLiveCheck(i + 1));
        }
        for (int i = 0; i < birthCheckBoxes.size(); ++i) {
            birthCheckBoxes.get(i).setSelected(model.isBirthCheck(i + 1));
        }
        updateLiveTooltips();
        updateBirthTooltips();
    }



    /************************************************************************
     * Support code for "Check Boxes" panel.
     */

    private ArrayList<CheckBox> liveCheckBoxes;

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
    void liveCheckboxActionPerformed(ActionEvent event) {
        CheckBox checkBox = (CheckBox)event.getSource();
        int id = idToIndex(checkBox.getId());
        Debug.info(DD, "liveCheckboxActionPerformed() " + id);
        model.setLiveCheck(id, checkBox.isSelected());
        updateLiveTooltips();
    }


    private ArrayList<CheckBox> birthCheckBoxes;

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

    @FXML
    void birthCheckboxActionPerformed(ActionEvent event) {
        CheckBox checkBox = (CheckBox)event.getSource();
        int id = idToIndex(checkBox.getId());
        Debug.info(DD, "birthCheckboxActionPerformed() " + id);
        model.setBirthCheck(id, checkBox.isSelected());
        updateBirthTooltips();
    }


    private void updateLiveTooltips() {
        for (int i = 0; i < liveCheckBoxes.size(); ++i) {
            String tip = model.getLiveCheckString();
            liveCheckBoxes.get(i).setTooltip(new Tooltip(tip));
        }
    }

    private void updateBirthTooltips() {
        for (int i = 0; i < birthCheckBoxes.size(); ++i) {
            String tip = model.getBirthCheckString();
            birthCheckBoxes.get(i).setTooltip(new Tooltip(tip));
        }
    }

    /**
     * Initialize "Check Boxes" panel.
     */
    private void initializeCheckBoxes() {
        liveCheckBoxes = new ArrayList<CheckBox>(8);
        liveCheckBoxes.add(live1CheckBox);
        liveCheckBoxes.add(live2CheckBox);
        liveCheckBoxes.add(live3CheckBox);
        liveCheckBoxes.add(live4CheckBox);
        liveCheckBoxes.add(live5CheckBox);
        liveCheckBoxes.add(live6CheckBox);
        liveCheckBoxes.add(live7CheckBox);
        liveCheckBoxes.add(live8CheckBox);

        updateLiveTooltips();
        for (int i = 0; i < liveCheckBoxes.size(); ++i) {
            String id = "L" + (i+1);
            CheckBox checkBox = liveCheckBoxes.get(i);
            checkBox.setId(id);
        }

        birthCheckBoxes = new ArrayList<CheckBox>(8);
        birthCheckBoxes.add(birth1CheckBox);
        birthCheckBoxes.add(birth2CheckBox);
        birthCheckBoxes.add(birth3CheckBox);
        birthCheckBoxes.add(birth4CheckBox);
        birthCheckBoxes.add(birth5CheckBox);
        birthCheckBoxes.add(birth6CheckBox);
        birthCheckBoxes.add(birth7CheckBox);
        birthCheckBoxes.add(birth8CheckBox);

        updateBirthTooltips();
        for (int i = 0; i < birthCheckBoxes.size(); ++i) {
            String id = "B" + (i+1);
            CheckBox checkBox = birthCheckBoxes.get(i);
            checkBox.setId(id);
        }

    }



    /************************************************************************
     * Support code for "Controls" panel.
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
        fasterButton.setDisable(model.incSpeed());
        slowerButton.setDisable(false);
        Debug.info(DD, "fasterButtonActionPerformed() " + model.getSpeed());

    }

    @FXML
    void slowerButtonActionPerformed(ActionEvent event) {
        slowerButton.setDisable(model.decSpeed());
        fasterButton.setDisable(false);
        Debug.info(DD, "slowerButtonActionPerformed() " + model.getSpeed());

    }

    @FXML
    void biggerButtonActionPerformed(ActionEvent event) {
        biggerButton.setDisable(model.incSize());
        smallerButton.setDisable(false);
        Debug.info(DD, "biggerButtonActionPerformed() " + model.getSize());

    }

    @FXML
    void smallerButtonActionPerformed(ActionEvent event) {
        smallerButton.setDisable(model.decSize());
        biggerButton.setDisable(false);
        Debug.info(DD, "smallerButtonActionPerformed() " + model.getSize());

    }

    @FXML
    private void clearDataButtonActionPerformed(ActionEvent event) {
        Debug.info(DD, "clearDataButtonActionPerformed()");
        clearData();
    }

    @FXML
    void playButtonActionPerformed(ActionEvent event) {
        model.togglePlay();
        Debug.info(DD, "playButtonActionPerformed() " + model.isPlay());

        if (model.isPlay())
            playButton.setText("Pause");
        else
            playButton.setText("Play");
    }

    /**
     * Clear all current data to the default settings then update the UI.
     */
    private void clearData() {
        model.defaultSettings();
        syncUI();
    }

    /**
     * Initialize "Controls" panel.
     */
    private void initializeControls() {
        clearDataButton.setTooltip(new Tooltip("Caution! This irreversible action will reset the form data to default values"));
    }



    /************************************************************************
     * Support code for "Earth" canvas.
     */

    @FXML
    private Canvas earth;

    /**
     * Initialize "Earth" canvas.
     */
    private void initializeEarthCanvas() {
    }
}
