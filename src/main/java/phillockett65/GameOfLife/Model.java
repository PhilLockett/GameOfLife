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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;
import phillockett65.Debug.Debug;

public class Model {

    // Debug delta used to adjust the local logging level.
    private static final int DD = 0;

    public static final String DATAFILE = "Settings.ser";
    public static final double TOPBARHEIGHT = 32.0;
    private static final String TOPBARICON = "top-bar-icon";

    private static Model model = new Model();
    private Stage stage;
    private PrimaryController controller;



    /************************************************************************
     * General support code.
     */

    /**
     * Convert a real colour value (0.0 to 1.0) to an int (0 too 256).
     * @param value to convert.
     * @return converted value.
     */
    private int colourRealToInt(double value) {
        return (int)(value * 256);
    }

 
    /**
     * Builds the cancel button as a Pane.
     * Does not include the mouse click handler.
     * @return the Pane that represents the cancel button.
     */
    public static Pane buildCancelButton() {
        final double iconSize = TOPBARHEIGHT;
        final double cancelPadding = 0.3;

        Pane cancel = new Pane();
        cancel.setPrefWidth(iconSize);
        cancel.setPrefHeight(iconSize);
        cancel.getStyleClass().add(TOPBARICON);

        double a = iconSize * cancelPadding;
        double b = iconSize - a;
        Line line1 = new Line(a, a, b, b);
        line1.setStroke(Color.WHITE);
        line1.setStrokeWidth(4.0);
        line1.setStrokeLineCap(StrokeLineCap.ROUND);

        Line line2 = new Line(a, b, b, a);
        line2.setStroke(Color.WHITE);
        line2.setStrokeWidth(4.0);
        line2.setStrokeLineCap(StrokeLineCap.ROUND);

        cancel.getChildren().addAll(line1, line2);

        return cancel;
    }

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
        initializeSelections();
        initializeSpinners();
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

        setInteger(10);
        setDouble(1.0);
        setDay("Tuesday");

        setMonth("July");
        setBestDay("New Year");
        setMyColour(Color.RED);
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

    private ArrayList<Boolean> birthCheck;

    ArrayList<Boolean> getBirthChecks() { return birthCheck; }
    void setBirthChecks(ArrayList<Boolean> checks) { birthCheck = checks; }

    public void setBirthCheck(int index, boolean state) { birthCheck.set(index, state); }
    public boolean isBirthCheck(int index) { return birthCheck.get(index); }


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
     * Support code for "Selections" panel.
     */

    private String month = "";
    private ObservableList<String> monthList = FXCollections.observableArrayList();

    private String bestDay = "";
    private ObservableList<String> bestDayList = FXCollections.observableArrayList();

    private Color myColour = Color.WHITE;

    public ObservableList<String> getMonthList() { return monthList; }
    public void setMonth(String value) { month = value; }
    public String getMonth() { return month; }

    public ObservableList<String> getBestDayList() { return bestDayList; }
    public void setBestDay(String value) { bestDay = value; }
    public String getBestDay() { return bestDay; }

    public Color getMyColour() { return myColour; }
    public void setMyColour(Color colour) { myColour = colour; }

    /**
     * @return myColour as a displayable RGB string.
     */
    public String getMyColourString() {
        return String.format("rgb(%d, %d, %d)",
                colourRealToInt(myColour.getRed()),
                colourRealToInt(myColour.getGreen()),
                colourRealToInt(myColour.getBlue()));
    }

    /**
     * Initialize "Selections" panel.
     */
    private void initializeSelections() {
        bestDayList.add("New Year");
        bestDayList.add("Good Friday");
        bestDayList.add("Easter Monday");
        bestDayList.add("Victoria Day");
        bestDayList.add("Canada Day");
        bestDayList.add("Civic Holiday");
        bestDayList.add("Labour Day");
        bestDayList.add("Thanksgiving Day");
        bestDayList.add("Remembrance Day");
        bestDayList.add("Christmas Day");
        bestDayList.add("Boxing Day");

        monthList.addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    }



    /************************************************************************
     * Support code for "Spinners" panel.
     */

    private SpinnerValueFactory<Integer> integerSVF;

    private SpinnerValueFactory<Double>  doubleSVF;

    private SpinnerValueFactory<String>  daySVF;

    private ObservableList<String> daysOfWeekList = FXCollections.observableArrayList();

    public SpinnerValueFactory<Integer> getIntegerSVF() { return integerSVF; }
    public SpinnerValueFactory<Double> getDoubleSVF() { return doubleSVF; }
    public SpinnerValueFactory<String> getDaySpinnerSVF() { return daySVF; }

    public int getInteger() { return integerSVF.getValue(); }
    public double getDouble() { return doubleSVF.getValue(); }
    public String getDay() { return daySVF.getValue(); }
    public void setInteger(int value) { integerSVF.setValue(value); }
    public void setDouble(double value) { doubleSVF.setValue(value); }
    public void setDay(String value) { daySVF.setValue(value); }

    /**
     * Selected Integer has changed, so synchronize values.
     */
    public void syncInteger() {  }

    /**
     * Selected Double has changed, so synchronize values.
     */
    public void syncDouble() {  }

    /**
     * Selected Day has changed, so synchronize values.
     */
    public void syncDay() {  }


    /**
     * Initialize "Spinners" panel.
     */
    private void initializeSpinners() {
        integerSVF = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 10);
        doubleSVF = new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 20.0, 1.0, 0.2);

        daysOfWeekList.add("Monday");
        daysOfWeekList.add("Tuesday");
        daysOfWeekList.add("Wednesday");
        daysOfWeekList.add("Thursday");
        daysOfWeekList.add("Friday");
        daysOfWeekList.add("Saturday");
        daysOfWeekList.add("Sunday");

        daySVF = new SpinnerValueFactory.ListSpinnerValueFactory<String>(daysOfWeekList);
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
