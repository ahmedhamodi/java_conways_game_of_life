package hamodi_life;

/**
 *
 * @author Ahmed Hamodi
 */
public interface LifeInterface {
    
    /**
     * Constructor - initializes an empty blank grid
     * @param size sets grid dimensions
     */

    /**
     * Set all grid cells to blank
     */
    void killAllCells();
    
    /**
     * Loads a start pattern to the grid
     * @param startGrid a  int [][] loaded with 1's and 0's
     */
    void setPattern(int [][] startGrid);
    
    /**
     * Counts how many adjacent cells are alive
     * @param cellRow = row address of test cell
     *  0 < cellRow < gridSize - 1
     * @param cellCol = column address of test cell
     * 0 < cellCol < gridSize - 1
     * @return int count of adjacent live cells
     */
    int countNeighbours(int cellRow, int cellCol);
    
    /**
     * @param cellRow = row address of test cell     * 
     *  0 < cellRow < gridSize - 1
     * @param cellCol = column address of test cell
     * 0 < cellCol < gridSize - 1
     * @return int = state of cell,  1 for live, 0 for dead
     */
    int applyRules(int cellRow, int cellCol );
    
    /**
     * Moves the game ahead one step by reading the
     * previous grid, applying the rules, and creating
     * a new grid.
     */
    void takeStep();
    
    /**
     * Creates a string representation of the grid
     * @return String
     */
    @Override
    String toString();
    
    /**
     * Creates a string containing an HTML representation of the grid
     * @return String
     */
    
    
    
}
