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
 * Model is the class that captures the dynamic shared data plus some 
 * supporting constants and provides access via getters and setters.
 */
package phillockett65.GameOfLife;

import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import phillockett65.Debug.Debug;

public class Model {

    // Debug delta used to adjust the local logging level.
    private static final int DD = 0;

    private static final int MIN_SPEED = -3;
    private static final int MAX_SPEED = 3;
    private static final int INIT_SPEED = -1;
    private static final int MIN_SIZE = 3;
    private static final int MAX_SIZE = 30;
    private static final int INIT_SIZE = 10;

    public static final String DATAFILE = "Settings.ser";

    private static Model model = new Model();
    private Stage stage;
    private PrimaryController controller;



    /************************************************************************
     * General support code.
     */


    /**
     * Add or remove the unfocussed style from the given pane object.
     * @param pane to add/remove unfocussed style.
     * @param style named in .css to define unfocussed style.
     * @param state is true if we have focus, false otherwise.
     */
    public static void styleFocus(Pane pane, String style, boolean state) {
        if (state) {
            pane.getStyleClass().remove(style);
        } else {
            if (!pane.getStyleClass().contains(style)) {
                pane.getStyleClass().add(style);
            }
        }
    }



    /************************************************************************
     * Support code for the Initialization of the Model.
     */

    /**
     * Private default constructor - part of the Singleton Design Pattern.
     * Called at initialization only, constructs the single private instance.
     */
    private Model() {

    }

    /**
     * Singleton implementation.
     * @return the only instance of the model.
     */
    public static Model getInstance() { return model; }

    /**
     * Called by the controller after the constructor to initialise any 
     * objects after the controls have been initialised.
     */
    public void initialize() {
        Debug.trace(DD, "Model initialized.");

        initializeCheckBoxes();
        initializeControls();
        initializeStatusLine();
    }

    /**
     * Called by the controller after the stage has been set. Completes any 
     * initialization dependent on other components being initialized.
     */
    public void init(Stage primaryStage, PrimaryController primaryController) {
        Debug.trace(DD, "Model init.");
        
        stage = primaryStage;
        controller = primaryController;
        if (!readData())
            defaultSettings();
    }

    public Stage getStage() { return stage; }
    public String getTitle() { return stage.getTitle(); }
    public PrimaryController getController() { return controller; }

    public void close() {
        stage.close();
    }

    public void syncUI() {
        controller.syncUI();
    }


    /**
     * Set all attributes to the default values.
     */
    public void defaultSettings() {

        for (int i = 1; i <=8; ++i) {
            setLiveCheck(i, false);
        }
        setLiveCheck(2, true);
        setLiveCheck(3, true);

        for (int i = 1; i <=8; ++i) {
            setBirthCheck(i, false);
        }
        setBirthCheck(2, true);

        initSpeed();
        initSize();
        initPlay();
    }



    /************************************************************************
     * Support code for state persistence.
     */

    /**
     * Call the static DataStore1 method, to read the data from disc.
     * @return true if data successfully read from disc, false otherwise.
     */
    private boolean readData() {
        if (DataStore1.readData() == true) {
            return true;
        }

        return false;
    }


 
    /************************************************************************
     * Support code for "Check Boxes" panel.
     */

    private ArrayList<Boolean> liveCheck;

    ArrayList<Boolean> getLiveChecks() { return liveCheck; }
    void setLiveChecks(ArrayList<Boolean> checks) { liveCheck = checks; }

    public void setLiveCheck(int index, boolean state) { liveCheck.set(index, state); }
    public boolean isLiveCheck(int index) { return liveCheck.get(index); }

    public String getLiveCheckString() {
        ArrayList<Integer> checks = new ArrayList<Integer>(9);
        for (int i = 1; i <= 8; ++i) {
            if (isLiveCheck(i))
                checks.add(i);
        }

        if (checks.size() == 0)
            return "At least one Live checkbox must be selected.";

        String output = "";
        if (checks.size() == 1) {
            output += checks.get(0);
        } else {
            output += checks.get(0);
            final int max = checks.size()-1;
            for (int i = 1; i < max; ++i) {
                output += ", " + checks.get(i);
            }
            output += " or " + checks.get(max);
        }

        return "Must have " + output + " living neighbours to stay alive.";
    }

    private ArrayList<Boolean> birthCheck;

    ArrayList<Boolean> getBirthChecks() { return birthCheck; }
    void setBirthChecks(ArrayList<Boolean> checks) { birthCheck = checks; }

    public void setBirthCheck(int index, boolean state) { birthCheck.set(index, state); }
    public boolean isBirthCheck(int index) { return birthCheck.get(index); }

    public String getBirthCheckString() {
        ArrayList<Integer> checks = new ArrayList<Integer>(9);
        for (int i = 1; i <= 8; ++i) {
            if (isBirthCheck(i))
                checks.add(i);
        }

        if (checks.size() == 0)
            return "At least one Birth checkbox must be selected.";

        String output = "";
        if (checks.size() == 1) {
            output += checks.get(0);
        } else {
            output += checks.get(0);
            final int max = checks.size()-1;
            for (int i = 1; i < max; ++i) {
                output += ", " + checks.get(i);
            }
            output += " or " + checks.get(max);
        }

        return "Must have " + output + " living neighbours to be born.";
    }


    /**
     * Initialize "Check Boxes" panel.
     */
    private void initializeCheckBoxes() {
        liveCheck = new ArrayList<Boolean>(9);
        for (int i = 0; i <=8; ++i) {
            liveCheck.add(false);
        }

        birthCheck = new ArrayList<Boolean>(9);
        for (int i = 0; i <=8; ++i) {
            birthCheck.add(false);
        }
    }



    /************************************************************************
     * Support code for "Controls" panel.
     */

    private int speed = INIT_SPEED;

    public int getSpeed() { return speed; }
    public void setSpeed(int value) { speed = value; }
    public void initSpeed() { speed = INIT_SPEED; }

    public boolean isMinSpeed() { return getSpeed() == MIN_SPEED; }
    public boolean isMaxSpeed() { return getSpeed() == MAX_SPEED; }

    /**
     * Increment speed if possible.
     * @return true if speed is at maximum value, false otherwise.
     */
    public boolean incSpeed() {
        if (isMaxSpeed()) return true;

        ++speed;

        return isMaxSpeed();
    }

    /**
     * Decrement speed if possible.
     * @return true if speed is at minimum value, false otherwise.
     */
    public boolean decSpeed() {
        if (isMinSpeed()) return true;

        --speed;

        return isMinSpeed();
    }

    private int size = INIT_SIZE;

    public int getSize() { return size; }

    public void setSize(int value) { size = value; }
    public void initSize() { size = INIT_SIZE; }

    public boolean isMinSize() { return getSize() == MIN_SIZE; }
    public boolean isMaxSize() { return getSize() == MAX_SIZE; }

    /**
     * Increment size if possible.
     * @return true if size is at maximum value, false otherwise.
     */
    public boolean incSize() {
        if (isMaxSize()) return true;

        ++size;

        return isMaxSize();
    }

    /**
     * Decrement size if possible.
     * @return true if size is at minimum value, false otherwise.
     */
    public boolean decSize() {
        if (isMinSize()) return true;

        --size;

        return isMinSize();
    }

    private boolean play = false;

    public boolean isPlay() { return play; }
    public void initPlay() { play = false; }

    /**
     * Toggle the current state of play.
     * @return play;
     */
    public boolean togglePlay() { play = !play; return isPlay(); }


    /**
     * Initialize "Controls" panel.
     */
    private void initializeControls() {
    }



    /************************************************************************
     * Support code for "Status Line" panel.
     */

    /**
     * Initialize "Status Line" panel.
     */
    private void initializeStatusLine() {
    }


}
