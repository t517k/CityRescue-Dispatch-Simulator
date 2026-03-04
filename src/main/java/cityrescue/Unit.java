package cityrescue;
import  cityrescue.enums.*;

/**
 * This is an abstract class that is extended by FireEngine, PoliceCar and Ambulance classes.
 * It defines a unit's main attributes and provides a method to calculate manhattan distance.
 */
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

    /**
     * This method calculates the unit's current manhattan distance from its destination when called.
     * @param destinationX X coordinate of the destination
     * @param destinationY Y coordinate of the destination
     */
    public void setManhattanDistance(int destinationX, int destinationY) {
        this.manhattanDistance = (Math.abs(this.xCoord - destinationX) + Math.abs(this.yCoord - destinationY));
    }
}

