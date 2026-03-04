package cityrescue;

import cityrescue.exceptions.InvalidGridException;

/**
 * This class defines the city map object and includes several methods and attributes that assist in creating and maintaining the current grid.
 */
public class CityMap {
    int width;
    int height;
    Object[][] grid;

    /**
     * This is the constructor for the map.
     * @param width Value of the width or X axis
     * @param height Value of the height or Y axis
     */
    public CityMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Creates an empty grid for the map using the size defined in the map object.
     * @throws InvalidGridException Throws if values are below or equal to zero
     */
    public void initialiseGrid() throws InvalidGridException {
        if (this.width<=0 || this.height<=0) {
            throw new InvalidGridException("Invalid width/height for map grid");
        } else {
            this.grid = new Object[this.width][this.height];
        }
    }

    /**
     * Gets the size of the current map object's grid.
     * @return An integer array containing two values x and y respectively
     */
    public int[] getSize() {
        int y = this.grid.length;
        int x = this.grid[0].length;
        return new int[]{x,y};
    }

    /**
     * Sets an element on the grid from null to an object.
     * @param x X coordinate
     * @param y Y coordinate
     * @param object Object to place at designated coordinates
     */
    public void addObstacle(int x, int y, Object object) {
        this.grid[y][x] = object;
    }

    /**
     * Sets an element on the grid from an object to null.
     * @param x X coordinate
     * @param y Y coordinate
     */
    public void removeObstacle(int x, int y) {
        this.grid[y][x] = null;
    }

    /**
     * Checks if specified grid reference is blocked by an obstacle.
     * @param x X coordinate to check
     * @param y Y coordinate to check
     * @return A boolean value of true or false
     */
    public boolean isBlocked(int x, int y) {
        return this.grid[y][x] != null;
    }
}
