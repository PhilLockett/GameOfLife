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
 * DataStore is a class that serializes the settings data for saving and 
 * restoring to and from disc.
 */
package phillockett65.GameOfLife;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.util.ArrayList;

import phillockett65.Debug.Debug;

public class DataStore1 extends DataStore {
    private static final long serialVersionUID = 1L;

    // Debug delta used to adjust the local logging level.
    private static final int DD = 0;

    private Double mainX;
    private Double mainY;

    private Double width;
    private Double height;

    private ArrayList<Boolean> liveCheck;
    private ArrayList<Boolean> birthCheck;

    private Integer speed;
    private Integer size;


    /************************************************************************
     * Support code for the Initialization, getters and setters of DataStore1.
     */

    public DataStore1() {
        super();

        liveCheck = new ArrayList<Boolean>();
        birthCheck = new ArrayList<Boolean>();
    }



    /**
     * Data exchange from the model to this DataStore.
     * @return true if data successfully pulled from the model, false otherwise.
     */
    private boolean pull() {
        boolean success = true;
        Model model = Model.getInstance();

        mainX = model.getStage().getX();
        mainY = model.getStage().getY();

        width = model.getStage().getWidth();
        height = model.getStage().getHeight();

        liveCheck = model.getLiveChecks();
        birthCheck = model.getBirthChecks();

        speed = model.getSpeed();
        size = model.getSize();

        return success;
    }

    /**
     * Data exchange from this DataStore to the model.
     * @return true if data successfully pushed to the model, false otherwise.
     */
    private boolean push() {
        boolean success = true;
        Model model = Model.getInstance();

        model.getStage().setX(mainX);
        model.getStage().setY(mainY);

        model.getStage().setWidth(width);
        model.getStage().setHeight(height);

        model.setLiveChecks(liveCheck);
        model.setBirthChecks(birthCheck);

        model.setSpeed(speed);
        model.setSize(size);

        return success;
    }



    /************************************************************************
     * Support code for static public interface.
     */

    /**
     * Static method that instantiates a DataStore, populates it from the 
     * model and writes it to disc.
     * @return true if data successfully written to disc, false otherwise.
     */
    public static boolean writeData() {
        boolean success = false;

        DataStore1 store = new DataStore1();
        store.pull();
        store.dump();

        ObjectOutputStream objectOutputStream;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(Model.DATAFILE));

            objectOutputStream.writeObject(store);
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
            Debug.critical(DD, e.getMessage());
        }

        return success;
    }

    /**
     * Static method that instantiates a DataStore, populates it from disc 
     * and writes it to the model.
     * @return true if data successfully read from disc, false otherwise.
     */
    public static boolean readData() {
        boolean success = false;

        ObjectInputStream objectInputStream;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(Model.DATAFILE));

            DataStore base = (DataStore)objectInputStream.readObject();
            long SVUID = ObjectStreamClass.lookup(base.getClass()).getSerialVersionUID();
 
            DataStore1 store = null;
            if (SVUID == 1) {
                store = (DataStore1)base;
                success = store.push();
                store.dump();
            }

        } catch (IOException e) {
            Debug.critical(DD, e.getMessage());
        } catch (ClassNotFoundException e) {
            Debug.critical(DD, e.getMessage());
        }

        return success;
    }



    /************************************************************************
     * Support code for debug.
     */

     /**
      * Print data store on the command line.
      */
      public void dump() {
        Debug.info(DD, "");
        Debug.info(DD, "DataStore:");
        Debug.info(DD, "");
        Debug.info(DD, "liveCheck = " + liveCheck);
        Debug.info(DD, "birthCheck = " + birthCheck);
        Debug.info(DD, "");
        Debug.info(DD, "speed = " + speed);
        Debug.info(DD, "size = " + size);
        Debug.info(DD, "");
    }


}
