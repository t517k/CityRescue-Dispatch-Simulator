package cityrescue;

import cityrescue.exceptions.InvalidGridException;

public class CityMap {
    int[][] grid;
    int[][] blockedCells;
    boolean legalMove;

    public CityMap(int[][] grid, int[][] blockedCells, boolean legalMove) {
        this.grid = grid;
        this.blockedCells = blockedCells;
        this.legalMove = legalMove;
    }

    // Methods...
    public int[][] initialiseGrid(int width, int height) throws InvalidGridException {
        if (width<=0 || height<=0) {
            throw new InvalidGridException("Invalid width/height for map grid");
        } else {

            return new int[width][height];
        }
    }
    // update map
    // check if move is legal
    // add blocked cell
    // remove blocked cell
}
