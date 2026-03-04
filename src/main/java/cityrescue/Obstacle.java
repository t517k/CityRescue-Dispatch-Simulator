package cityrescue;

/**
 * This class defines an obstacle's main attributes and also contains a constructor to pass in required values.
 */
public class Obstacle {
    int xCoord;
    int yCoord;

    /**
     * This constructor is used to instantiate an obstacle.
     * @param xCoord X coordinate of the new obstacle
     * @param yCoord Y coordinate of the new obstacle
     */
    public Obstacle(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }
}
