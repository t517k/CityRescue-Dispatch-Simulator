package cityrescue;
import  cityrescue.enums.*;

import java.util.ArrayList;

public abstract class Unit {
    static int numberOfUnits = 0;
    UnitType type;
    UnitStatus status;
    int unitID;
    Station homeStation;
    int xCoord;
    int yCoord;
    Incident currentIncident;
    int manhattanDistance;

    public void setManhattanDistance(int destinationX, int destinationY) {
        this.manhattanDistance = (Math.abs(this.xCoord - destinationX) + Math.abs(this.yCoord - destinationY));
    }
}

