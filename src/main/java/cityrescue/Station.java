package cityrescue;

import java.util.ArrayList;

/**
 * This class defines a station's main attributes and also contains a constructor to pass in required values.
 */
public class Station {
    String stationName;
    int xCoord;
    int yCoord;
    ArrayList<Unit> stationedUnits = new ArrayList<>();
    int stationID;
    static int numOfStations = 0;
    int maxUnits = 10;

    /**
     * This constructor is used to instantiate a station.
     * @param xCoord X coordinate of the new station
     * @param yCoord Y coordinate of the new station
     * @param stationName Name of the new station as a string
     */
    public Station(int xCoord, int yCoord, String stationName) {
        this.stationID = ++numOfStations;
        this.stationName = stationName;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }
}
