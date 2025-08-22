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
import java.util.LinkedList;

import javafx.scene.Scene;
import javafx.stage.Stage;
import phillockett65.Debug.Debug;

public class Model {

    // Debug delta used to adjust the local logging level.
    private static final int DD = 0;

    private static final long SECOND = 1000000000L;
    private static final int MIN_SPEED = -3;
    private static final int MAX_SPEED = 3;
    private static final int INIT_SPEED = -1;

    private static final int MIN_SIZE = 3;
    private static final int MAX_SIZE = 30;
    private static final int INIT_SIZE = 10;

    private static final int MAX_XPOS = 1000;
    private static final int INIT_XPOS = 0;

    private static final int MAX_YPOS = 1000;
    private static final int INIT_YPOS = 0;

    public static final String DATAFILE = "Settings.ser";

    private static Model model = new Model();
    private Stage stage;
    private Scene scene;
    private PrimaryController controller;



    /************************************************************************
     * General support code.
     */

    static public int encode(int x, int y) { return (x << 16) | y; }
    static public int extractX(int v) { return (v >> 16) & 0xFFFF; }
    static public int extractY(int v) { return v & 0xFFFF; }


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
        initializeEarthCanvas();
    }

    /**
     * Called by the controller after the stage has been set. Completes any 
     * initialization dependent on other components being initialized.
     */
    public void init(Stage primaryStage, Scene primaryScene, PrimaryController primaryController) {
        Debug.trace(DD, "Model init.");
        
        stage = primaryStage;
        scene = primaryScene;
        controller = primaryController;
        if (!readData())
            defaultSettings();
    }

    public Stage getStage() { return stage; }
    public Scene getScene() { return scene; }
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
        setBirthCheck(3, true);

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

    private int speed = INIT_SPEED;     // Actually a delta time denominator.

    public int getSpeed() { return speed; }
    public void setSpeed(int value) { speed = value; }
    public void initSpeed() { speed = INIT_SPEED; }

    public long getDelta() { 
        if (speed < 0) {
            return SECOND >> -speed;
        }

        return SECOND << speed;
    }

    public boolean isMinSpeed() { return getSpeed() == MIN_SPEED; }
    public boolean isMaxSpeed() { return getSpeed() == MAX_SPEED; }

    /**
     * Increment speed if possible.
     * @return true if speed is at maximum value, false otherwise.
     */
    public boolean decSpeed() {
        if (isMaxSpeed()) return true;

        ++speed;

        return isMaxSpeed();
    }

    /**
     * Decrement speed if possible.
     * @return true if speed is at minimum value, false otherwise.
     */
    public boolean incSpeed() {
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

    private int xOff = INIT_XPOS;
    public int getXOffset() { return xOff; }
    public int getX(int x) { return (x - xOff + MAX_XPOS) % MAX_XPOS; }
    public int getXPosition(int x) { return ((xOff + x) % MAX_XPOS) * size; }
    public int moveLeft() { xOff = (xOff - 1 + MAX_XPOS) % MAX_XPOS; return xOff; }
    public int moveRight() { xOff = (xOff + 1) % MAX_XPOS; return xOff; }

    private int yOff = INIT_YPOS;
    public int getYOffset() { return yOff; }
    public int getY(int y) { return (y - yOff + MAX_YPOS) % MAX_YPOS; }
    public int getYPosition(int y) { return ((yOff + y) % MAX_YPOS) * size; }
    public int moveUp() { yOff = (yOff - 1 + MAX_YPOS) % MAX_YPOS; return yOff; }
    public int moveDown() { yOff = (yOff + 1) % MAX_YPOS; return yOff; }

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
     * Support code for "Earth" canvas.
     */

    private byte[][] landscape;
    public double getEarthWidth() {
        double width = getScene().getWidth();
        
        return width - (160 + 4);
    }

    public double getEarthHeight() {
        double height = model.getScene().getHeight();
        
        return height;
    }

    private int livingNeighbours(final int l, final int x, final int r, final int u, final int y, final int d) {
        int count = 0;
        // Debug.info(DD, "livingNeighboursSafe(" + x + ", " + y + ")");

        if (isLiving(l, u)) ++count;
        if (isLiving(l, y)) ++count;
        if (isLiving(l, d)) ++count;

        if (isLiving(x, u)) ++count;
        if (isLiving(x, d)) ++count;

        if (isLiving(r, u)) ++count;
        if (isLiving(r, y)) ++count;
        if (isLiving(r, d)) ++count;

        // Debug.info(DD, "livingNeighboursSafe() -> " + count);
        return count;
    }

    public void nextGenSafeX(int x, LinkedList<Integer> toggles) {
        final int l = (x == 0) ? (MAX_XPOS-1) : x-1;
        final int r = (x == MAX_XPOS-1) ? 0 : x+1;
        
        for (int y = 0; y < MAX_YPOS; ++y) {
            final int u = (y == 0) ? (MAX_YPOS-1) : y-1;
            final int d = (y == MAX_YPOS-1) ? 0 : y+1;

            final int count = livingNeighbours(l, x, r, u, y, d);
            final boolean living = isLiving(x, y);

            if (living) {
                if (isLiveCheck(count) != true) {
                    toggles.add(encode(x, y));
                }
            } else {
                if (isBirthCheck(count) == true) {
                    toggles.add(encode(x, y));
                }
            }
        }
    }

    public void nextGenSafeY(int y, LinkedList<Integer> toggles) {
        final int u = (y == 0) ? (MAX_YPOS-1) : y-1;
        final int d = (y == MAX_YPOS-1) ? 0 : y+1;

        for (int x = 1; x < (MAX_XPOS-1); ++x) {
            final int l = x-1;
            final int r = x+1;

            final int count = livingNeighbours(l, x, r, u, y, d);
            final boolean living = isLiving(x, y);

            if (living) {
                if (isLiveCheck(count) != true) {
                    toggles.add(encode(x, y));
                }
            } else {
                if (isBirthCheck(count) == true) {
                    toggles.add(encode(x, y));
                }
            }
        }
    }


    public boolean isLiving(int x, int y) {
        return ((landscape[x][y] & 0x01) == 1);
    }

    private int livingNeighbours(int x, int y) {
        int count = 0;

        final int u = y-1;
        final int d = y+1;
        final int l = x-1;
        final int r = x+1;
        if (isLiving(l, u)) ++count;
        if (isLiving(l, y)) ++count;
        if (isLiving(l, d)) ++count;

        if (isLiving(x, u)) ++count;
        if (isLiving(x, d)) ++count;

        if (isLiving(r, u)) ++count;
        if (isLiving(r, y)) ++count;
        if (isLiving(r, d)) ++count;

        return count;
    }

    public void toggle(int x, int y) {
        landscape[x][y] ^= 1;
    }

    public void toggle(int pos) {
        final int x = extractX(pos);
        final int y = extractY(pos);
        toggle(x, y);
    }

    public boolean toggleSelected(int x, int y) {
        // Debug.info(DD, "toggleSelected() " + x + " " + y);

        toggle(x, y);

        return isLiving(x, y);
    }

    public LinkedList<Integer> nextGeneration() {
        Debug.info(DD, "nextGeneration() ");
        LinkedList<Integer> toggles = new LinkedList<>();

        // Apply rules and create a list of all cells that should change state.
        for (int x = 1; x < (MAX_XPOS-1); ++x) {
            for (int y = 1; y < (MAX_YPOS-1); ++y) {
                final int count = livingNeighbours(x, y);
                final boolean living = isLiving(x, y);

                if (living) {
                    if (isLiveCheck(count) != true) {
                        toggles.add(encode(x, y));
                    }
                } else {
                    if (isBirthCheck(count) == true) {
                        toggles.add(encode(x, y));
                    }
                }
            }
        }
    
        nextGenSafeX(0, toggles);
        nextGenSafeY(0, toggles);
        nextGenSafeX(MAX_XPOS-1, toggles);
        nextGenSafeY(MAX_YPOS-1, toggles);

        // Now change the state of all cells that should change state.
        for (Integer pos : toggles) {
            toggle(pos);
        }

        return toggles;
    }


    /**
     * Initialize "Earth" canvas.
     */
    private void initializeEarthCanvas() {
        landscape = new byte[MAX_XPOS][MAX_YPOS];
    }


}
