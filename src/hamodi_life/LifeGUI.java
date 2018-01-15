package hamodi_life;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*; //.JFrame, .JLabel, .JPanel, etc
import java.awt.event.*;
import java.util.Random;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Ahmed Hamodi
 */
public class LifeGUI extends MouseAdapter implements ActionListener, ChangeListener {
    JFrame frame;
    JLabel speed, presets;
    JComboBox presetOptions;
    JPanel mainCP, contentPane1, contentPane2, contentPane3, contentPane4;
    JButton button, run, nextStep, reset, generateLife; //add functionality for reset/generateLife
    JSlider rateOfChange = new JSlider(0, 1000, 100);
    private final int LENGTH = 50;
    private int fps = 100;
    private int randomNum;
    private JButton[][] bGrid = new JButton[LENGTH][LENGTH];
    private int[][] iGrid = new int[LENGTH][LENGTH];
    private int[][] tempGrid = new int[LENGTH][LENGTH];
    Life life = new Life(LENGTH);
    private static Timer time;
    
    /**
     * Constructor
     * pre: none
     * post: none
     */
    public LifeGUI(){
        /* Create and set up the frame */
        frame = new JFrame("Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        /* Create the main content pane */
        mainCP = new JPanel();
        mainCP.setBorder(BorderFactory.createEmptyBorder
        (10, 10, 10, 10));
        mainCP.setBackground(Color.black);
        mainCP.setLayout(new BoxLayout(mainCP, BoxLayout.PAGE_AXIS));
        
        /* Create a content pane with a GridLayout and
        empty borders */
        contentPane1 = new JPanel();
        contentPane1.setBorder(BorderFactory.createEmptyBorder
        (10, 10, 10, 10));
        contentPane1.setBackground(Color.lightGray);
        contentPane1.setLayout(new GridLayout(LENGTH, 0, 0, 0));
        
        /* Create a content pane with a FlowLayout and
        empty borders*/
        contentPane2 = new JPanel();
        contentPane2.setBorder(BorderFactory.createEmptyBorder
        (10, 10, 10, 10));
        contentPane2.setBackground(Color.lightGray);
        contentPane2.setLayout(new FlowLayout());
        
        /* Create a content pane with a FlowLayout and
        empty borders*/
        contentPane3 = new JPanel();
        contentPane3.setBorder(BorderFactory.createEmptyBorder
        (10, 10, 10, 10));
        contentPane3.setBackground(Color.lightGray);
        contentPane3.setLayout(new FlowLayout());
        
        /* Create a content pane with a FlowLayout and
        empty borders*/
        contentPane4 = new JPanel();
        contentPane4.setBorder(BorderFactory.createEmptyBorder
        (10, 10, 10, 10));
        contentPane4.setBackground(Color.lightGray);
        contentPane4.setLayout(new FlowLayout());
        
        /* create button grid */
        makeGrid();
        
        /* create and add the buttons for user interactions */
        run = new JButton("Start Game");
        run.addActionListener(this);
        run.setActionCommand("Start");
        run.setPreferredSize(new Dimension(120, 20));
        run.setBackground(Color.yellow);
        run.setForeground(Color.red);
        contentPane2.add(run);

        nextStep = new JButton("Next Step");
        nextStep.addActionListener(this);
        nextStep.setActionCommand("Step");
        nextStep.setPreferredSize(new Dimension(120, 20));
        nextStep.setBackground(Color.yellow);
        nextStep.setForeground(Color.red);
        contentPane2.add(nextStep);
        
        //add reset game button (reset board, slider position, etc)
        reset = new JButton("Reset Board");
        reset.addActionListener(this);
        reset.setActionCommand("Reset");
        reset.setPreferredSize(new Dimension(120, 20));
        reset.setBackground(Color.yellow);
        reset.setForeground(Color.red);
        contentPane2.add(reset);
        
        //add generateLife functionality
        generateLife = new JButton("Generate Life");
        generateLife.addActionListener(this);
        generateLife.setActionCommand("Generate");
        generateLife.setPreferredSize(new Dimension(120, 20));
        generateLife.setBackground(Color.yellow);
        generateLife.setForeground(Color.red);
        contentPane2.add(generateLife);
        
        /* Create a JLabel to describe the speed functionality */
        speed = new JLabel();
        speed.setText("Set speed: ");
        speed.setForeground(Color.black);
        speed.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        speed.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        contentPane3.add(speed);
        
        /* Create slider for timer rate of change */
        rateOfChange.setMajorTickSpacing(500);
        rateOfChange.setMinorTickSpacing(100);
        rateOfChange.setPaintTicks(true);
        rateOfChange.setPaintLabels(true);
        rateOfChange.setSnapToTicks(true);
        rateOfChange.addChangeListener(this);
        rateOfChange.setBackground(Color.lightGray);
        rateOfChange.setForeground(Color.black);
        contentPane3.add(rateOfChange);
        
        /* Set up timer */
        time = new Timer(fps, new gameTimer());
        time.stop();
        
        /* Set up preset options label */
        presets = new JLabel();
        presets.setText("Preset Objects: ");
        presets.setForeground(Color.black);
        presets.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        presets.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        contentPane4.add(presets);
        
        /* Set up combo box with presets */
        String[] presetChoices = {"glider", "blinker", "toad", "beacon",
                                "pulsar", "10 cell row", "spaceship",
                                "small exploder", "exploder", "tumbler",
                                "square border"};
        presetOptions = new JComboBox(presetChoices);
        presetOptions.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
        presetOptions.setSelectedIndex(7);
        presetOptions.setBorder(BorderFactory.createEmptyBorder
        (10, 10, 10, 10));
        presetOptions.addActionListener(new comboBoxListener());
        presetOptions.setBackground(Color.lightGray);
        presetOptions.setForeground(Color.black);
        contentPane4.add(presetOptions);
        
        /* Add content panes to the main content pane */
        mainCP.add(contentPane1);
        mainCP.add(contentPane2);
        mainCP.add(contentPane3);
        mainCP.add(contentPane4);
        
        /* Add content pane to frame */
        frame.setContentPane(mainCP);
        
        /* Size and then display the frame. */
//        frame.setSize(900, 900);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * Helper method to make the button grid
     * pre: none
     * post: button grid made
     */
    private void makeGrid() {
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                button = new JButton();
                button.setPreferredSize(new Dimension(10, 10));
                button.setBackground(Color.darkGray);
                button.setBorderPainted(false);
                button.addMouseListener(this);
                bGrid[i][j] = button;
                contentPane1.add(bGrid[i][j]);
            }
        }
    }
    
    /**
     * Runs the GUI
     */
    public static void runGUI () {
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        LifeGUI project = new LifeGUI();
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(LifeGUI::runGUI);
    }

    /**
     * Deals with the mouse being pressed and changes the color
     * of the button based on it's previous state
     * pre: none
     * post: button changed color
     * @param e 
     */
    @Override
    public void mousePressed(MouseEvent e) {
        button = (JButton)e.getComponent();
        if (button.getBackground().equals(Color.darkGray)) {
            button.setBackground(Color.green);
        } else {
            button.setBackground(Color.darkGray);
        }
        //functionality that stops game when cell is modified by
        //the user. Allows user to thoughtfully place live cells.
//        time.stop();
//        run.setActionCommand("Start");
//        run.setText("Continue");
    }
    
    /**
     * Action Listener for the buttons. Deals with various actions the
     * user can perform. This includes starting the game, taking one
     * step of the game, stopping the game, resetting the board, and
     * generating randomized live cells on the board
     * pre: none
     * post: user action performed
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String eventName = e.getActionCommand();
        
        switch (eventName) {
            case "Start":
                time.start();
                run.setActionCommand("Stop");
                run.setText("Stop");
                break;
            case "Step":
                time.stop();
                run.setActionCommand("Start");
                run.setText("Continue");

                //make temp grid equal to button grid
                for (int row = 0; row < LENGTH; row++) {
                    for (int col = 0; col < LENGTH; col++) {
                        if (bGrid[row][col].getBackground().equals(Color.green)) {
                            tempGrid[row][col] = 1;
                        } else {
                            tempGrid[row][col] = 0;
                        }
                    }
                }

                //update int grid using temp grid
                life.setPattern(tempGrid);

                //take step
                tempGrid = life.takeStep(tempGrid);

                //set int grid equal to the tempGrid
                for (int row = 0; row < LENGTH; row++) {
                    System.arraycopy(tempGrid[row], 0, iGrid[row], 0, LENGTH);
                }

                //set button grid equal to int grid
                for (int row = 0; row < LENGTH; row++) {
                    for (int col = 0; col < LENGTH; col++) {
                        if (iGrid[row][col] == 1) {
                            bGrid[row][col].setBackground(Color.green);
                        } else {
                            bGrid[row][col].setBackground(Color.darkGray);
                        }
                    }
                }
                break;
            case "Stop":
                time.stop();
                run.setActionCommand("Start");
                run.setText("Continue");
                break;
            case "Reset":
                time.stop();
                run.setActionCommand("Start");
                run.setText("Start Game");
                for (int row = 0; row < LENGTH; row++) {
                    for (int col = 0; col < LENGTH; col++) {
                        bGrid[row][col].setBackground(Color.darkGray);
                    }
                }
                break;
            case "Generate":
                time.stop();
                run.setActionCommand("Start");
                run.setText("Start Game");
                for (int row = 0; row < LENGTH; row++) {
                    for (int col = 0; col < LENGTH; col++) {
                        Random rand = new Random();
                        randomNum = rand.nextInt(5);
                        if (randomNum == 0) { //20% chance of coming alive
                            bGrid[row][col].setBackground(Color.green);
                        } else {
                            bGrid[row][col].setBackground(Color.darkGray);
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * Slider listener that checks when the value has changed and updates
     * the timer to the new rate of change (frames per second) in milliseconds
     * pre: none
     * post: none
     * @param e 
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        fps = rateOfChange.getValue();
        time.setDelay(fps);
    }
    
    /**
     * Adds a class to deal with the comboBox listener
     */
    class comboBoxListener implements ActionListener {

        /**
         * Deals with finding which item has been selected by the user in the
         * combo box drop down menu. It then calls a method to form the
         * object that the user has selected.
         * pre: none
         * post: none
         * @param e 
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox comboBox = (JComboBox)e.getSource();
            String eventName = (String)comboBox.getSelectedItem();

            //make int grid blank
            for (int row = 0; row < LENGTH; row++) {
                for (int col = 0; col < LENGTH; col++) {
                    iGrid[row][col] = 0;
                }
            }
            
            //switch statement controller which presets to make
            switch (eventName) {
                case "glider":
                    makeGlider();
                    time.stop();
                    run.setActionCommand("Start");
                    run.setText("Continue");
                    break;
                case "blinker":
                    makeBlinker();
                    time.stop();
                    run.setActionCommand("Start");
                    run.setText("Continue");
                    break;
                case "toad":
                    makeToad();
                    time.stop();
                    run.setActionCommand("Start");
                    run.setText("Continue");
                    break;
                case "beacon":
                    makeBeacon();
                    time.stop();
                    run.setActionCommand("Start");
                    run.setText("Continue");
                    break;
                case "pulsar":
                    makePulsar();
                    time.stop();
                    run.setActionCommand("Start");
                    run.setText("Continue");
                    break;
                case "10 cell row":
                    makePentadecathlon();
                    time.stop();
                    run.setActionCommand("Start");
                    run.setText("Continue");
                    break;
                case "spaceship":
                    makeSpaceship();
                    time.stop();
                    run.setActionCommand("Start");
                    run.setText("Continue");
                    break;
                case "small exploder":
                    makeSmallExploder();
                    time.stop();
                    run.setActionCommand("Start");
                    run.setText("Continue");
                    break;
                case "exploder":
                    makeExploder();
                    time.stop();
                    run.setActionCommand("Start");
                    run.setText("Continue");
                    break;
                case "tumbler":
                    makeTumbler();
                    time.stop();
                    run.setActionCommand("Start");
                    run.setText("Continue");
                    break;
                case "square border":
                    makeSquareBorder();
                    time.stop();
                    run.setActionCommand("Start");
                    run.setText("Continue");
                default:
                    break;
            }
        }
        
        /**
         * Makes a glider
         */
        public void makeGlider() {
            // 0, 1, 0
            // 0, 0, 1
            // 1, 1, 1
            iGrid[5][5] = 1;
            iGrid[5][6] = 1;
            iGrid[5][7] = 1;
            iGrid[4][7] = 1;
            iGrid[3][6] = 1;
            
            //set button grid equal to int grid
            for (int row = 0; row < LENGTH; row++) {
                for (int col = 0; col < LENGTH; col++) {
                    if (iGrid[row][col] == 1) {
                        bGrid[row][col].setBackground(Color.green);
                    } else {
                        bGrid[row][col].setBackground(Color.darkGray);
                    }
                }
            }
        }
        
        /**
         * Makes a blinker
         */
        public void makeBlinker() {
            // 0, 1, 1, 1, 0
            iGrid[25][24] = 1;
            iGrid[25][25] = 1;
            iGrid[25][26] = 1;
            
            //set button grid equal to int grid
            for (int row = 0; row < LENGTH; row++) {
                for (int col = 0; col < LENGTH; col++) {
                    if (iGrid[row][col] == 1) {
                        bGrid[row][col].setBackground(Color.green);
                    } else {
                        bGrid[row][col].setBackground(Color.darkGray);
                    }
                }
            }
        }
        
        /**
         * Makes a toad
         */
        public void makeToad() {
            // 0, 1, 1, 1
            // 1, 1, 1, 0
            iGrid[20][21] = 1;
            iGrid[20][22] = 1;
            iGrid[20][23] = 1;
            iGrid[21][20] = 1;
            iGrid[21][21] = 1;
            iGrid[21][22] = 1;
            
            //set button grid equal to int grid
            for (int row = 0; row < LENGTH; row++) {
                for (int col = 0; col < LENGTH; col++) {
                    if (iGrid[row][col] == 1) {
                        bGrid[row][col].setBackground(Color.green);
                    } else {
                        bGrid[row][col].setBackground(Color.darkGray);
                    }
                }
            }
        }
        
        /**
         * Makes a beacon
         */
        public void makeBeacon() {
            // 1, 1, 0, 0
            // 1, 0, 0, 0
            // 0, 0, 0, 1
            // 0, 0, 1, 1
            
            iGrid[20][20] = 1;
            iGrid[20][21] = 1;
            iGrid[21][20] = 1;
            iGrid[22][23] = 1;
            iGrid[23][22] = 1;
            iGrid[23][23] = 1;
            
            //set button grid equal to int grid
            for (int row = 0; row < LENGTH; row++) {
                for (int col = 0; col < LENGTH; col++) {
                    if (iGrid[row][col] == 1) {
                        bGrid[row][col].setBackground(Color.green);
                    } else {
                        bGrid[row][col].setBackground(Color.darkGray);
                    }
                }
            }
        }
        
        /**
         * Makes a pulsar
         */
        public void makePulsar() {
            // 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0
            // empty line
            // 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1
            // 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1
            // 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1
            // 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0 
            // empty line
            // 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0
            // 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1
            // 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1
            // 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1
            // empty line
            // 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0
            
            int rowNum = 15;
            for (int i = 0; i < 2; i++) {
                iGrid[rowNum][17] = 1;
                iGrid[rowNum][18] = 1;
                iGrid[rowNum][19] = 1;
                iGrid[rowNum][23] = 1;
                iGrid[rowNum][24] = 1;
                iGrid[rowNum][25] = 1;
                rowNum += 5;
            }
            
            rowNum = 17;
            for (int i = 0; i < 3; i++) {
                iGrid[rowNum][15] = 1;
                iGrid[rowNum][20] = 1;
                iGrid[rowNum][22] = 1;
                iGrid[rowNum][27] = 1;
                rowNum += 1;
            }
            
            rowNum = 22;
            for (int i = 0; i < 2; i++) {
                iGrid[rowNum][17] = 1;
                iGrid[rowNum][18] = 1;
                iGrid[rowNum][19] = 1;
                iGrid[rowNum][23] = 1;
                iGrid[rowNum][24] = 1;
                iGrid[rowNum][25] = 1;
                rowNum += 5;
            }
            
            rowNum = 23;
            for (int i = 0; i < 3; i++) {
                iGrid[rowNum][15] = 1;
                iGrid[rowNum][20] = 1;
                iGrid[rowNum][22] = 1;
                iGrid[rowNum][27] = 1;
                rowNum += 1;
            }
            
            //set button grid equal to int grid
            for (int row = 0; row < LENGTH; row++) {
                for (int col = 0; col < LENGTH; col++) {
                    if (iGrid[row][col] == 1) {
                        bGrid[row][col].setBackground(Color.green);
                    } else {
                        bGrid[row][col].setBackground(Color.darkGray);
                    }
                }
            }
        }
        
        /**
         * Makes a pentadecathlon, also known as a 10 cell row.
         */
        public void makePentadecathlon() {
            // 10 cell line
            int colNum = 20;
            for (int i = 0; i < 10; i++) {
                iGrid[20][colNum] = 1;
                colNum += 1;
            }
            
            //set button grid equal to int grid
            for (int row = 0; row < LENGTH; row++) {
                for (int col = 0; col < LENGTH; col++) {
                    if (iGrid[row][col] == 1) {
                        bGrid[row][col].setBackground(Color.green);
                    } else {
                        bGrid[row][col].setBackground(Color.darkGray);
                    }
                }
            }
        }
        
        /**
         * Makes a spaceship
         */
        public void makeSpaceship() {
            // 0, 1, 1, 1, 1
            // 1, 0, 0, 0, 1
            // 0, 0, 0, 0, 1
            // 1, 0, 0, 1, 0
            
            iGrid[20][21] = 1;
            iGrid[20][22] = 1;
            iGrid[20][23] = 1;
            iGrid[20][24] = 1;
            iGrid[21][20] = 1;
            iGrid[21][24] = 1;
            iGrid[22][24] = 1;
            iGrid[23][20] = 1;
            iGrid[23][23] = 1;
            
            //set button grid equal to int grid
            for (int row = 0; row < LENGTH; row++) {
                for (int col = 0; col < LENGTH; col++) {
                    if (iGrid[row][col] == 1) {
                        bGrid[row][col].setBackground(Color.green);
                    } else {
                        bGrid[row][col].setBackground(Color.darkGray);
                    }
                }
            }
        }
        
        /**
         * Makes a small exploder
         */
        public void makeSmallExploder() {
            // 0, 1, 0
            // 1, 1, 1
            // 1, 0, 1
            // 0, 1, 0
            
            iGrid[23][25] = 1;
            iGrid[24][24] = 1;
            iGrid[24][25] = 1;
            iGrid[24][26] = 1;
            iGrid[25][24] = 1;
            iGrid[25][26] = 1;
            iGrid[26][25] = 1;
            
            //set button grid equal to int grid
            for (int row = 0; row < LENGTH; row++) {
                for (int col = 0; col < LENGTH; col++) {
                    if (iGrid[row][col] == 1) {
                        bGrid[row][col].setBackground(Color.green);
                    } else {
                        bGrid[row][col].setBackground(Color.darkGray);
                    }
                }
            }
        }
        
        /**
         * Makes an exploder
         */
        public void makeExploder() {
            // 1, 0, 1, 0, 1
            // 1, 0, 0, 0, 1
            // 1, 0, 0, 0, 1
            // 1, 0, 0, 0, 1
            // 1, 0, 1, 0, 1
            
            iGrid[23][23] = 1;
            iGrid[23][25] = 1;
            iGrid[23][27] = 1;
            iGrid[24][23] = 1;
            iGrid[24][27] = 1;
            iGrid[25][23] = 1;
            iGrid[25][27] = 1;
            iGrid[26][23] = 1;
            iGrid[26][27] = 1;            
            iGrid[27][23] = 1;
            iGrid[27][25] = 1;
            iGrid[27][27] = 1;
            
            //set button grid equal to int grid
            for (int row = 0; row < LENGTH; row++) {
                for (int col = 0; col < LENGTH; col++) {
                    if (iGrid[row][col] == 1) {
                        bGrid[row][col].setBackground(Color.green);
                    } else {
                        bGrid[row][col].setBackground(Color.darkGray);
                    }
                }
            }
        }
        
        /**
         * Makes a tumbler
         */
        public void makeTumbler() {
            // 0, 1, 1, 0, 1, 1, 0
            // 0, 1, 1, 0, 1, 1, 0
            // 0, 0, 1, 0, 1, 0, 0
            // 1, 0, 1, 0, 1, 0, 1
            // 1, 0, 1, 0, 1, 0, 1
            // 1, 1, 0, 0, 0, 1, 1
            
            iGrid[22][23] = 1;
            iGrid[22][24] = 1;
            iGrid[22][26] = 1;
            iGrid[22][27] = 1;
            iGrid[23][23] = 1;
            iGrid[23][24] = 1;
            iGrid[23][26] = 1;
            iGrid[23][27] = 1;
            iGrid[24][24] = 1;
            iGrid[24][26] = 1;
            iGrid[25][22] = 1;
            iGrid[25][24] = 1;
            iGrid[25][26] = 1;
            iGrid[25][28] = 1;
            iGrid[26][22] = 1;
            iGrid[26][24] = 1;
            iGrid[26][26] = 1;
            iGrid[26][28] = 1;
            iGrid[27][22] = 1;
            iGrid[27][23] = 1;
            iGrid[27][27] = 1;
            iGrid[27][28] = 1;
            
            //set button grid equal to int grid
            for (int row = 0; row < LENGTH; row++) {
                for (int col = 0; col < LENGTH; col++) {
                    if (iGrid[row][col] == 1) {
                        bGrid[row][col].setBackground(Color.green);
                    } else {
                        bGrid[row][col].setBackground(Color.darkGray);
                    }
                }
            }
        }
        
        /**
         * Makes a square border
         */
        public void makeSquareBorder() {
            
            for (int row = 0; row < 50; row += 49) {
                for (int col = 0; col < 50; col++) {
                    iGrid[row][col] = 1;
                }
            }

            for (int row = 0; row < 50; row++) {
                for (int col = 0; col < 50; col += 49) {
                    iGrid[row][col] = 1;
                }
            }

            //set button grid equal to int grid
            for (int row = 0; row < LENGTH; row++) {
                for (int col = 0; col < LENGTH; col++) {
                    if (iGrid[row][col] == 1) {
                        bGrid[row][col].setBackground(Color.green);
                    } else {
                        bGrid[row][col].setBackground(Color.darkGray);
                    }
                }
            }
        }
    }
    
    /**
     * Deals with the timer and the logic of the game being applied
     */
    class gameTimer implements ActionListener {

        /**
         * Performs the logic of the game of life
         * pre: none
         * post: next iteration of board is shown on gui
         * @param e 
         */
        @Override
        public void actionPerformed(ActionEvent e) {
                        
            //make temp grid equal to button grid
            for (int row = 0; row < LENGTH; row++) {
                for (int col = 0; col < LENGTH; col++) {
                    if (bGrid[row][col].getBackground().equals(Color.green)) {
                        tempGrid[row][col] = 1;
                    } else {
                        tempGrid[row][col] = 0;
                    }
                }
            }
            
            //update life grid using temp grid
            life.setPattern(tempGrid);
            
            //take step
            tempGrid = life.takeStep(tempGrid);
            
            //set int grid equal to the tempGrid
            for (int row = 0; row < LENGTH; row++) {
                System.arraycopy(tempGrid[row], 0, iGrid[row], 0, LENGTH);
            }
            
            //set button grid equal to int grid
            for (int row = 0; row < LENGTH; row++) {
                for (int col = 0; col < LENGTH; col++) {
                    if (iGrid[row][col] == 1) {
                        bGrid[row][col].setBackground(Color.green);
                    } else {
                        bGrid[row][col].setBackground(Color.darkGray);
                    }
                }
            }
        }
    }
}