package com.chess.gui;

import com.chess.engine.board.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.chess.engine.board.Board.*;
import static com.chess.engine.utils.Constants.BoardConstants.TOTAL_TILES;

/**
 * This class will bring together other GUI-related classes to produce the GUI for the chess game.
 */
public class Table {
    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private final Board chessBoard;

    // Frame/Panel dimensions
    private final static float SCALE = 1.5f;
    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension((int) (600 * SCALE), (int) (600 * SCALE));
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension((int) (400 * SCALE), (int) (350 * SCALE));
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension((int) (10 * SCALE), (int) (10 * SCALE));

    // Tile colors
    private final Color lightTileColor = Color.decode("#eeeed2");
    private final Color darkTileColor = Color.decode("#769656");

    // Images location
    private final String defaultImagePath = "res/chess_com/";
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

        this.chessBoard = createStandardBoard();

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
    private class BoardPanel extends JPanel {
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
    private class TilePanel extends JPanel {
        private final int tileID;
        //--------------------------------------------------------------------------------------------------------------
        //------------------------------------------------ Constructor -------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------
        protected TilePanel(final BoardPanel boardPanel, final int tileID) {
            super(new GridBagLayout());
            this.tileID = tileID;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);
            validate();
        }
        //--------------------------------------------------------------------------------------------------------------
        //------------------------------------------------ Main Methods ------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------
        private void assignTilePieceIcon(final Board board) {
            this.removeAll();

            if (board.getTile(this.tileID).isTileOccupied()) {
                try {
                    final BufferedImage image = ImageIO.read(new File(defaultImagePath +
                            board.getTile(this.tileID).getPiece().getPieceAlliance().toString().charAt(0) +
                            board.getTile(this.tileID).getPiece().toString() + ".png"));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * Assigns a tile to a color based on being even or odd.
         */
        private void assignTileColor() {
            boolean isLight = ((tileID + tileID / 8) % 2 == 0);
            setBackground(isLight ? lightTileColor : darkTileColor);
        }
    }
}
