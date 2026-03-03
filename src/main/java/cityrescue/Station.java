package cityrescue;

import java.util.ArrayList;

public class Station {
    String stationName;
    int xCoord;
    int yCoord;
    ArrayList<Unit> stationedUnits = new ArrayList<>();
    int stationID;
    static int numOfStations = 0;
    int maxUnits = 10;

    public Station(int xCoord, int yCoord, String stationName) {
        this.stationID = ++numOfStations;
        this.stationName = stationName;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }
}
