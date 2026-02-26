package cityrescue;
import cityrescue.exceptions.InvalidGridException;

public class CityMap {
    int width;
    int height;
    int[][] grid;


    public CityMap(int width, int height) {
        this.width = width;
        this.height = height;
    }


    public void initialiseGrid() throws InvalidGridException {
        if (this.width<=0 || this.height<=0) {
            throw new InvalidGridException("Invalid width/height for map grid");
        } else {
            this.grid = new int[this.width][this.height];
        }
    }

    public int[] getSize() {
        int y = this.grid.length;
        int x = this.grid[0].length;
        return new int[]{x,y};
    }


    // update map
    // check if move is legal
    // add blocked cell
    // remove blocked cell
}
