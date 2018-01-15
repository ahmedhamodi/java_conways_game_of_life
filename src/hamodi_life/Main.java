package hamodi_life;

/**
 *
 * @author Ahmed Hamodi
 */
public class Main {

    public static void main(String[] args) {
        
//        System.out.println("                     === --- ===   **   Rules   **   === --- ===");
//        System.out.println("1. Any live cell with fewer than two live neighbours dies, as if caused by underpopulation.");
//        System.out.println("2. Any live cell with two or three live neighbours lives on to the next generation.");
//        System.out.println("3. Any live cell with more than three live neighbours dies, as if by overpopulation.");
//        System.out.println("4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.");
//        System.out.println("");
        
        //Creates a game of life with a specified grid
        int[][] grid = new int[20][20];
        Life life = new Life(grid); //overloaded
        //prints out a blank, 20 by 20 grid, 18 by 18 inner grid
        System.out.println(life);
        
        //test setPattern
        int[][] grid2 = new int[12][12];
        //creates a glider
        grid2[4][5] = 1;
        grid2[5][6] = 1;
        grid2[6][6] = 1;
        grid2[6][5] = 1;
        grid2[6][4] = 1;
        life.setPattern(grid2);
        //prints out glider
        System.out.println(life);
        //tests the takeStep() method, glider should move across screen
        for (int i = 0; i < 25; i++) {
            life.takeStep();
            System.out.println(life);
        }
        
        System.out.println("");
        System.out.println("-------------------- NEW GAME --------------------");
        System.out.println("");
        
        //test setPattern x2
        Life life2 = new Life(10);
        int[][] grid3 = new int[15][15];
        //creates a square, nothing will move/happen
        grid3[4][4] = 1;
        grid3[3][4] = 1;
        grid3[4][3] = 1;
        grid3[3][3] = 1;
        life2.setPattern(grid3);
        System.out.println(life2);
        //tests the takeStep to make sure no cells die in the square
        for (int i = 0; i < 5; i++) {
            life2.takeStep();
            System.out.println(life2);
        }
        
        System.out.println("");
        System.out.println("-------------------- NEW GAME --------------------");
        System.out.println("");
        
        //test setPattern x3
        Life life3 = new Life(15);
        int[][] grid4 = new int[10][10];
        //creates a blinker
        grid4[3][3] = 1;
        grid4[3][4] = 1;
        grid4[3][5] = 1;
        life3.setPattern(grid4);
        System.out.println(life3);
        //prints out 10 generations of blinker
        for (int i = 0; i < 10; i++) {
            life3.takeStep();
            System.out.println(life3);
        }
        
    }
    
}
