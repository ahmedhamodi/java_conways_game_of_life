package hamodi_life;

/**
 *
 * @author Ahmed Hamodi
 */
public class Life implements LifeInterface{
    private int [][] grid;
    
    /**
     * constructor
     * pre: none
     * post: none
     */
    public Life() {
        grid = new int[50][50];
        for (int[] grid1 : grid) {
            for (int col = 0; col < grid[0].length; col++) {
                grid1[col] = 0;
            }
        }
    }
    
    /**
     * overloaded constructor #1
     * pre: none
     * post: none
     * @param length 
     */
    public Life(int length) {
        grid = new int[length][length];
        for (int[] grid1 : grid) {
            for (int col = 0; col < grid[0].length; col++) {
                grid1[col] = 0;
            }
        }
    }
    
    /**
     * overloaded constructor #2
     * pre: none
     * post: none
     * @param g 
     */
    public Life(int[][] g) {
        grid = new int[g.length][g[0].length];
        for (int row = 0; row < grid.length; row++) {
            System.arraycopy(g[row], 0, grid[row], 0, grid[0].length);
//            for (int col = 0; col < grid[0].length; col++) {
//                grid[row][col] = g[row][col];
//            }
        }
    }
    
    /**
     * Kills all cells - creates an empty grid
     * pre: none
     * post: empty grid
     */
    @Override
    public void killAllCells() {
        for (int[] grid1 : grid) {
            for (int col = 0; col < grid[0].length; col++) {
                grid1[col] = 0;
            }
        }
    }

    /**
     * Sets pattern of the grid
     * pre: none
     * post: pattern set to grid
     * @param startGrid 
     */
    @Override
    public void setPattern(int[][] startGrid) {
        grid = new int[startGrid.length][startGrid[0].length];
        for (int row = 0; row < grid.length; row++) {
            System.arraycopy(startGrid[row], 0, grid[row], 0, grid[0].length);
        }
    }

    /**
     * counts the amount of live neighbors around a cell
     * pre: none
     * post: number of alive neighbors returned
     * @param cellRow
     * @param cellCol
     * @return # of alive neighbors
     */
    @Override
    public int countNeighbours(int cellRow, int cellCol) {
        int alive;
        if (grid[cellRow][cellCol] == 1) { //is alive
            alive = -1;
        } else { //is not alive
            alive = 0;
        }
        for (int i = cellRow-1; i <= cellRow+1; i++) {
            for (int j = cellCol-1; j <= cellCol+1; j++) {
                try {
                    if (grid[i][j] == 1) {
                        //System.out.println("Cell alive @ " + cellRow + " and " + cellCol);
                        alive ++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
//                    System.err.println(e.getMessage());
                }
            }
        }
        //System.out.print(alive + ", ");
        return alive;
    }

    /**
     * Checks what to do with a cell based on the amount of live neighbors
     * pre: none
     * post: changes cells state
     * @param cellRow
     * @param cellCol
     * @return new cell state
     */
    @Override
    public int applyRules(int cellRow, int cellCol) {
        int alive = countNeighbours(cellRow, cellCol);
        int currentlyAlive = grid[cellRow][cellCol];
        if (currentlyAlive == 0) { //currently dead
            if (alive == 3) {
                //System.out.println("Cell alive @ " + cellRow + " and " + cellCol);
                return 1;
            } else {
                return 0;
            }
        } else { //currently alive
            if (alive < 2) {
                return 0;
            } else if (alive > 3) {
                return 0;
            } else {
                //System.out.println("Cell alive @ " + cellRow + " and " + cellCol);
                return 1;
            }
        }
    }

    /**
     * Runs the logic of the game
     * pre: none
     * post: new grid made and displayed
     */
    @Override
    public void takeStep() {
        int[][] nextGrid = new int[grid.length][grid[0].length];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                nextGrid[row][col] = applyRules(row, col);
            }
        }
        for (int row = 0; row < grid.length; row++) {
            System.arraycopy(nextGrid[row], 0, grid[row], 0, grid[0].length);
        }
    }
    
    /**
     * Overloaded takeStep to run the logic of the game with a int[][] parameter
     * pre: none
     * post: new grid made and displayed
     * @param nG
     * @return 
     */
    public int[][] takeStep(int[][] nG) {
        int[][] nextGrid = new int[grid.length][grid[0].length];
        
        //set nG to nextGrid for modification
        for (int row = 0; row < grid.length; row++) {
            System.arraycopy(nG[row], 0, nextGrid[row], 0, grid[0].length);
//            for (int j = 0; j < grid[0].length; j++) {
//                nextGrid[i][j] = nG[i][j];
//            }
        }
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                nextGrid[row][col] = applyRules(row, col);
            }
        }
        for (int row = 0; row < grid.length; row++) {
            System.arraycopy(nextGrid[row], 0, grid[row], 0, grid[0].length);
        }
        
        return nextGrid;
    }

    /**
     * toString method to display the grid
     * pre: none
     * post: none
     * @return 
     */
    @Override
    public String toString() {
        String output = "";
        
        for (int[] grid1 : grid) {
            for (int col = 0; col < grid[0].length; col++) {
                output += grid1[col] + " ";
            }
            output += "\n";
        }
        
        return output;
    }   
}