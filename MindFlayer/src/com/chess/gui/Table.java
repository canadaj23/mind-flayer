package com.chess.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.chess.engine.utils.Constants.BoardConstants.TOTAL_TILES;

/**
 * This class will bring together other GUI-related classes to produce the GUI for the chess game.
 */
public class Table {
    private final JFrame gameFrame;
    private final BoardPanel boardPanel;

    private final static float SCALE = 2.0f;
    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension((int) (600 * SCALE), (int) (600 * SCALE));
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension((int) (400 * SCALE), (int) (350 * SCALE));
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension((int) (10 * SCALE), (int) (10 * SCALE));
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a Table object.
     */
    public Table() {
        this.gameFrame = new JFrame("The Mind Flayer");
        this.gameFrame.setLayout(new BorderLayout());

        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);

        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);

        this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameFrame.setResizable(false);
        this.gameFrame.pack();
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.gameFrame.setLocationRelativeTo(null);
        this.gameFrame.setVisible(true);
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return a JMenuBar object with all the JMenu elements
     */
    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());

        return tableMenuBar;
    }

    /**
     * @return the file menu with its options
     */
    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");

        // Create and add an open PGN option
        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(actionEvent -> System.out.println("Load the PGN file!"));
        fileMenu.add(openPGN);
        // Create and add an exit option
        final JMenuItem exitGame = new JMenuItem("Exit");
        exitGame.addActionListener(actionEvent -> System.exit(0));
        fileMenu.add(exitGame);

        return fileMenu;
    }
//######################################################################################################################
//###################################################### BoardPanel ####################################################
//######################################################################################################################
    /**
     * This class represents the visual board component.
     */
    private static class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;
    //------------------------------------------------------------------------------------------------------------------
    //-------------------------------------------------- Constructor ---------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
        protected BoardPanel() {
            super(new GridLayout(8, 8));
            this.boardTiles = createAllTiles();
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }
    //------------------------------------------------------------------------------------------------------------------
    //-------------------------------------------------- Main Methods --------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
        /**
         * @return a List of TilePanel objects
         */
        private List<TilePanel> createAllTiles() {
            final List<TilePanel> allTiles = new ArrayList<>();
            for (int i = 0; i < TOTAL_TILES; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                // Keep track of the created tiles
                allTiles.add(tilePanel);
                // Add that tilePanel to BoardPanel
                add(tilePanel);
            }

            return allTiles;
        }
    }
//######################################################################################################################
//###################################################### TilePanel #####################################################
//######################################################################################################################
    /**
     * This class represents each visual tile component to do added to the BoardPanel.
     */
    private static class TilePanel extends JPanel {
        private final int tileID;

        protected TilePanel(final BoardPanel boardPanel, final int tileID) {
            super(new GridBagLayout());
            this.tileID = tileID;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            validate();
        }

        /**
         * Assigns a tile to a color based on being even or odd.
         */
        private void assignTileColor() {
        }
    }
}
