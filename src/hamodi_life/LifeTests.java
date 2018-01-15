package hamodi_life;

/**
 *
 * @author Ahmed Hamodi
 */
public class LifeTests {
    
    public static int[][] makeGrid(){
        int[][] start = new int[10][10];
        // set all cells "dead" 
        for(int row = 0; row < start.length; row++){
            for(int column = 0; column < start[row].length; column ++){
                start [row][column] = 0;
            }
        }
        return start;
    }
    
    public static int[][] makeFilledGrid(){
        int[][] start = new int[10][10];
        // set all cells "dead" 
        for(int row = 0; row < start.length; row++){
            for(int column = 0; column < start[row].length; column ++){
                start [row][column] = 1;
            }
        }
        return start;
    }    
    
    public static int [][] makeTestGrid(){
        int [][] test = makeGrid();
        // make block
        test [1][1] = 1;
        test [1][2] = 1;
        test [1][3] = 1;
        test [2][1] = 1;
        test [2][2] = 1;
        test [2][3] = 1;
        test [3][1] = 1;
        test [3][2] = 1;
        test [3][3] = 1;
        
        // make blinker
        test [1][7] = 1;
        test [2][7] = 1;
        test [3][7] = 1;
        
        // make orphan
        test [5][3] = 1;
        
        return test;
    }
    
    public static int[][] makeCompareGrid(){
        int [][] test = makeGrid();
        // make block
        test [1][1] = 1;
        test [1][3] = 1;
        test [2][4] = 1;
        test [2][6] = 1;
        test [2][7] = 1;
        test [2][8] = 1;
        test [3][1] = 1;
        test [3][3] = 1;
        test [4][3] = 1;
        return test;
    }
    
    public static void main(String[] args){
        
        int [][] emptyGrid = makeGrid();
        int [][] filledGrid = makeFilledGrid();
        int [][] testGrid = makeTestGrid();
                
        //Test Constructor and toString()
        System.out.println("Test Constructor");
        Life game = new Life(emptyGrid); // create new game with blank grid
        System.out.println("expected  10x10 grid o's");
        System.out.println(game); // prints 10 x10 grid of 0's
        
        // Test setPattern() and toString()
        System.out.println("Test setPattern()");
        game.setPattern(filledGrid); // sets game to filled grid        
        System.out.println("expected  10x10 grid 1's");
        System.out.println(game);
        
        // Test killAllCells()
        System.out.println("Test killAllCells()");
        game.killAllCells(); //  should set all cells to 0        
        System.out.println("expected  10x10 grid o's");
        System.out.println(game); 
        
        // Test countNeighbours
        System.out.println("Test countNeighbours()");
        game.setPattern(testGrid); // sets game to test grid
        System.out.println("expected 8: actual " + game.countNeighbours(2, 2));        
        System.out.println("expected 3: actual " + game.countNeighbours(1, 1));        
        System.out.println("expected 1: actual " + game.countNeighbours(1, 7));        
        System.out.println("expected 0: actual " + game.countNeighbours(5, 3));        
        System.out.println("expected 0: actual " + game.countNeighbours(5, 5)); 
        
        //Test apply rules
        System.out.println("\n\nTest applyRules()");
        game.setPattern(testGrid); // sets game to test grid
        System.out.println("expected 0: actual " + game.applyRules(2, 2) + " death by overcrowding");        
        System.out.println("expected 1: actual " + game.applyRules(1, 1) + " stay alive");        
        System.out.println("expected 0: actual " + game.applyRules(1, 7) + " death by loneliness");
        System.out.println("expected 1: actual " + game.applyRules(2, 6) + " birth by neighbours");
        System.out.println("expected 0: actual " + game.applyRules(5, 3) + " death by loneliness");      
        System.out.println("expected 0: actual " + game.applyRules(5, 5) + " stay dead."); 
        
        //Test take step
        System.out.println("\n\nTest takeStep()");
        game.setPattern(testGrid); // sets game to test grid
        game.takeStep();
        
        Life expectedGame = new Life(makeCompareGrid());        
        String actual = game.toString();
        //System.out.println (actual + "\n");
        String expected = expectedGame.toString();
        //System.out.println (expected + "\n");
        System.out.println(" actual == expected : " + actual.equalsIgnoreCase(expected));
    }
    
}
