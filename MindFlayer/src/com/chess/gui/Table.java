package com.chess.gui;

import com.chess.engine.board.Board;
import com.chess.engine.moves.Move;
import com.chess.engine.moves.misc.MoveTransition;
import com.chess.engine.pieces.Piece;
import com.chess.engine.tiles.Tile;
import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.chess.engine.board.Board.*;
import static com.chess.engine.moves.Move.MoveFactory.CreateMove;
import static com.chess.engine.utils.Constants.BoardConstants.TOTAL_TILES;
import static javax.swing.SwingUtilities.*;

/**
 * This class will bring together other GUI-related classes to produce the GUI for the chess game.
 */
public class Table {
    private final JFrame gameFrame;
    private final GameHistoryPanel gameHistoryPanel;
    private final TakenPiecesPanel takenPiecesPanel;
    private final BoardPanel boardPanel;
    private Board chessBoard;
    private final MoveLog moveLog;

    // Used for TilePanel
    private Tile sourceTile, destinationTile;
    private Piece humanMovedPiece;
    private BoardDirection boardDirection;

    private boolean highlightLegalMoves;

    // Frame/Panel dimensions
    protected final static float SCALE = 1.5f;
    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension((int) (600 * SCALE), (int) (600 * SCALE));
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension((int) (400 * SCALE), (int) (350 * SCALE));
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension((int) (10 * SCALE), (int) (10 * SCALE));

    // Tile colors
    private final Color lightTileColor = Color.decode("#eeeed2");
    private final Color darkTileColor = Color.decode("#769656");

    // Images location
    protected final static String DEFAULT_IMAGE_PATH = "res/pieces/chess_com/";
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

        this.gameHistoryPanel = new GameHistoryPanel();
        this.takenPiecesPanel = new TakenPiecesPanel();
        this.boardPanel = new BoardPanel();
        this.moveLog = new MoveLog();
        this.boardDirection = BoardDirection.NORMAL;
        this.highlightLegalMoves = false;
        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);

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
        tableMenuBar.add(createPreferencesMenu());

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

        fileMenu.addSeparator();

        // Create and add an exit option
        final JMenuItem exitGame = new JMenuItem("Exit");
        exitGame.addActionListener(actionEvent -> System.exit(0));
        fileMenu.add(exitGame);

        return fileMenu;
    }

    /**
     * @return the file menu with its options
     */
    private JMenu createPreferencesMenu() {
        final JMenu preferencesMenu = new JMenu("Preferences");

        // Create and add a flip board option
        final JMenuItem flipBoardMenuItem = new JMenuItem("Flip Board");
        flipBoardMenuItem.addActionListener(actionEvent -> {
            boardDirection = boardDirection.opposite();
            boardPanel.drawBoard(chessBoard);
        });
        preferencesMenu.add(flipBoardMenuItem);

        preferencesMenu.addSeparator();

        // Create and add a highlight option
        final JCheckBoxMenuItem highlightCheckBox = new JCheckBoxMenuItem("Highlight Legal Moves",
                false);
        highlightCheckBox.addActionListener(actionEvent -> highlightLegalMoves =
                highlightCheckBox.isSelected());
        preferencesMenu.add(highlightCheckBox);

        return preferencesMenu;
    }
//######################################################################################################################
//#################################################### BoardDirection ##################################################
//######################################################################################################################
    /**
     * This enum represents the directionality of the board.
     */
    public enum BoardDirection {
        NORMAL {
            /**
             * @param boardTiles the tiles on the chess board
             * @return either boardTiles in the normal direction or flipped direction
             */
            @Override
            protected List<TilePanel> traverse(List<TilePanel> boardTiles) {
                return boardTiles;
            }

            /**
             * @return the direction of the board
             */
            @Override
            protected BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED {
            /**
             * @param boardTiles the tiles on the chess board
             * @return either boardTiles in the normal direction or flipped direction
             */
            @Override
            protected List<TilePanel> traverse(List<TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }

            /**
             * @return the direction of the board
             */
            @Override
            protected BoardDirection opposite() {
                return NORMAL;
            }
        };
        //--------------------------------------------------------------------------------------------------------------
        //---------------------------------------------- Abstract Methods ----------------------------------------------
        //--------------------------------------------------------------------------------------------------------------

        /**
         * @param boardTiles the tiles on the chess board
         * @return how to traverse boardTiles (either in the normal or flipped direction)
         */
        protected abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);

        /**
         * @return the direction of the board
         */
        protected abstract BoardDirection opposite();
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

        /**
         * Draws the next chess board in succession.
         *
         * @param board the new board to draw
         */
        public void drawBoard(final Board board) {
            removeAll();
            for (final TilePanel tilePanel : boardDirection.traverse(boardTiles)) {
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
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
            highlightLegals(chessBoard);

            addMouseListener(new MouseListener() {
                /**
                 * Determines what to do based on a mouse clicked event.
                 *
                 * @param e the mouse event performed
                 */
                @Override
                public void mouseClicked(final MouseEvent e) {
                    if (isRightMouseButton(e)) {
                        // Undo the selection
                        sourceTile = null;
                        destinationTile = null;
                        humanMovedPiece = null;
                    } else if (isLeftMouseButton(e)) {
                        // Determine if a source tile has been clicked already
                        if (sourceTile == null) {
                            // Select the source tile (first click)
                            sourceTile = chessBoard.getTile(tileID);
                            // Determine the piece on the tile
                            humanMovedPiece = sourceTile.getPiece();
                            // Determine whether there is a piece on a tile
                            if (humanMovedPiece == null) {
                                // No piece = empty tile
                                sourceTile = null;
                            }
                        } else {
                            // Select the destination tile (second click)
                            destinationTile = chessBoard.getTile(tileID);
                            // Determine the move to be made
                            final Move move = CreateMove(chessBoard,
                                    sourceTile.getTilePosition(),
                                    destinationTile.getTilePosition());
                            // Determine the move transition
                            final MoveTransition transition = chessBoard.getCurrentPlayer().makeMove(move);
                            // Determine whether the move is done
                            if (transition.getMoveStatus().isDone()) {
                                // Proceed to the updated chess board
                                chessBoard = transition.getTransitionBoard();
                                moveLog.addMove(move);
                            }
                            sourceTile = null;
                            destinationTile = null;
                            humanMovedPiece = null;
                            System.out.println("-------------------- UPDATED BOARD --------------------");
                            System.out.println(chessBoard);
                            System.out.println("-------------------------------------------------------");
                        }
                        invokeLater(() -> {
                            gameHistoryPanel.redo(chessBoard, moveLog);
                            takenPiecesPanel.redo(moveLog);
                            boardPanel.drawBoard(chessBoard);
                        });
                    }
                }

                @Override
                public void mousePressed(final MouseEvent e) {
                }

                @Override
                public void mouseReleased(final MouseEvent e) {
                }

                @Override
                public void mouseEntered(final MouseEvent e) {
                }

                @Override
                public void mouseExited(final MouseEvent e) {
                }
            });

            validate();
        }
        //--------------------------------------------------------------------------------------------------------------
        //------------------------------------------------ Main Methods ------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------

        /**
         * Assigns an image for each piece.
         *
         * @param board what the pieces are on
         */
        private void assignTilePieceIcon(final Board board) {
            this.removeAll();

            // Associate an image with an active piece for White/Black
            if (board.getTile(this.tileID).isTileOccupied()) {
                try {
                    final BufferedImage image = ImageIO.read(new File(DEFAULT_IMAGE_PATH +
                            board.getTile(this.tileID).getPiece().getPieceAlliance().toString().charAt(0) +
                            board.getTile(this.tileID).getPiece().toString() + ".png"));
//                    final BufferedImage rescaledImage = Thumbnails.of(image)
//                                                                            .size((int) (image.getWidth() * SCALE),
//                                                                                  (int) (image.getHeight() * SCALE))
//                                                                            .asBufferedImage();
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

        /**
         * Draws a new tile for the corresponding chess board.
         *
         * @param board where the tile will be
         */
        public void drawTile(final Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            highlightLegals(board);
            validate();
            repaint();
        }

        private void highlightLegals(final Board board) {
            // Determine if highlight is on/off
            if (highlightLegalMoves) {
                for (final Move move : pieceLegalMoves(board)) {
                    if (move.getDestinationPosition() == this.tileID) {
                        try {
                            add(new JLabel(new ImageIcon(ImageIO.read(new File("res/misc/green_dot.png")))));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        private Collection<Move> pieceLegalMoves(final Board board) {
            if (humanMovedPiece != null &&
                    humanMovedPiece.getPieceAlliance() == board.getCurrentPlayer().getAlliance()) {
                return humanMovedPiece.calculateLegalMoves(board);
            }

            return Collections.emptyList();
        }
    }
//######################################################################################################################
//######################################################## MoveLog #####################################################
//######################################################################################################################
    public static class MoveLog {
        private final List<Move> moves;
        //--------------------------------------------------------------------------------------------------------------
        //------------------------------------------------ Constructor -------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------
        /**
         * Constructor for a MoveLog object.
         */
        protected MoveLog() {
            this.moves = new ArrayList<>();
        }
        //--------------------------------------------------------------------------------------------------------------
        //------------------------------------------------ Main Methods ------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------
        /**
         * @return the list of moves
         */
        public List<Move> getMoves() {
            return this.moves;
        }

        /**
         * Adds a move to the move log.
         *
         * @param move what will be added to the move log
         */
        public void addMove(final Move move) {
            this.moves.add(move);
        }

        /**
         * @return the size of the move log
         */
        public int getSize() {
            return this.moves.size();
        }

        /**
         * Clears the move log.
         */
        public void clear() {
            this.moves.clear();
        }

        /**
         * Removes and returns the move from the move log based on the index.
         *
         * @param index where the move is in the move log
         * @return the removed move
         */
        public Move removeMove(final int index) {
            return this.moves.remove(index);
        }

        /**
         * Determines whether a move could be removed from the move log.
         *
         * @param move the move to remove from the move log
         * @return whether a move could be removed from the move log
         */
        public boolean removeMove(final Move move) {
            return this.moves.remove(move);
        }
    }
}
