package com.chess.gui;

import com.chess.engine.moves.Move;
import com.chess.engine.pieces.Piece;
import com.chess.gui.Table.MoveLog;
import com.google.common.primitives.Ints;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.chess.gui.Table.SCALE;

/**
 * This class creates a taken pieces panel to be displayed.
 */
public class TakenPiecesPanel extends JPanel {
    private JPanel northPanel;
    private JPanel southPanel;

    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
    private static final Dimension TAKEN_PIECES_DIMENSION = new Dimension((int) (40 * SCALE), (int) (80 * SCALE));
    private static final Color PANEL_COLOR = Color.decode("0xFDF5E6");
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a TakenPiecesPanel object.
     */
    protected TakenPiecesPanel() {
        super(new BorderLayout());
        this.setBackground(PANEL_COLOR);
        this.setBorder(PANEL_BORDER);
        setUpPanels();
        setPreferredSize(TAKEN_PIECES_DIMENSION);
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Sets up the north and south taken pieces panels.
     */
    private void setUpPanels() {
        this.northPanel = new JPanel(new GridLayout(8, 2));
        this.northPanel.setBackground(PANEL_COLOR);
        this.add(northPanel, BorderLayout.NORTH);

        this.southPanel = new JPanel(new GridLayout(8, 2));
        this.southPanel.setBackground(PANEL_COLOR);
        this.add(southPanel, BorderLayout.SOUTH);
    }

    public void redo(final MoveLog moveLog) {
        this.northPanel.removeAll();
        this.southPanel.removeAll();

        final List<Piece> whiteTakenPieces = new ArrayList<>();
        final List<Piece> blackTakenPieces = new ArrayList<>();
        // Iterate through all the moves in the move log
        for (final Move move : moveLog.getMoves()) {
            // Determine whether the move is an attack
            if (move.isAttack()) {
                // Retrieve the attacked piece
                final Piece pieceTaken = move.getAttackedPiece();
                // Determine whether the piece is White's or Black's
                if (pieceTaken.getPieceAlliance().isWhite()) {
                    // Add the captured piece to whiteTakenPieces
                    whiteTakenPieces.add(pieceTaken);
                } else if (pieceTaken.getPieceAlliance().isBlack()) {
                    // Add the captured piece to blackTakenPieces
                    blackTakenPieces.add(pieceTaken);
                } else {
                    throw new RuntimeException("Something is wrong with the game!");
                }
            }
        }
        // Sort the pieces in terms of value
        whiteTakenPieces.sort((o1, o2) -> Ints.compare(o1.getPieceValue(), o2.getPieceValue()));
        blackTakenPieces.sort((o1, o2) -> Ints.compare(o1.getPieceValue(), o2.getPieceValue()));

        // Display the pieces Black captured
        displayTakenPieces(whiteTakenPieces, this.northPanel);
        // Display the pieces White captured
        displayTakenPieces(blackTakenPieces, this.southPanel);

        validate();
    }

    /**
     * Displays the taken pieces based on the alliance
     *
     * @param takenPieces    the list of taken pieces
     * @param directionPanel which panel to display the taken pieces on
     */
    private void displayTakenPieces(final List<Piece> takenPieces, final JPanel directionPanel) {
        for (final Piece takenPiece : takenPieces) {
            try {
                final BufferedImage image =
                        ImageIO.read(new File(
                                "res/pieces/chess_com" +
                                        takenPiece.getPieceAlliance().toString().charAt(0) +
                                        takenPiece.toString()));
                final ImageIcon icon = new ImageIcon(image);
                final JLabel imageLabel = new JLabel();
                directionPanel.add(imageLabel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
