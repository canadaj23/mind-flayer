package com.chess.gui;

import com.chess.engine.board.Board;
import com.chess.engine.moves.Move;
import com.chess.gui.Table.MoveLog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.chess.gui.Table.SCALE;

/**
 * This class creates a game history panel to be displayed.
 */
public class GameHistoryPanel extends JPanel {
    private final DataModel model;
    private final JScrollPane scrollPane;
    private static final Dimension HISTORY_PANEL_DIMENSION = new Dimension((int) (100 * SCALE), (int) (400 * SCALE));
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a GameHistoryPanel object.
     */
    protected GameHistoryPanel() {
        this.setLayout(new BorderLayout());
        this.model = new DataModel();
        final JTable table = new JTable(model);
        table.setRowHeight((int) (15 * SCALE));
        this.scrollPane = new JScrollPane(table);
        scrollPane.setColumnHeaderView(table.getTableHeader());
        scrollPane.setPreferredSize(HISTORY_PANEL_DIMENSION);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    protected void redo(final Board board, final MoveLog moveHistory) {
        int currentRow = 0;
        this.model.clear();
        // Iterate through all the moves made
        for (final Move move : moveHistory.getMoves()) {
            // String version of the move
            final String moveText = move.toString();
            if (move.getMovedPiece().getPieceAlliance().isWhite()) {
                // Display the move in White's column
                this.model.setValueAt(moveText, currentRow, 0);
            } else if (move.getMovedPiece().getPieceAlliance().isBlack()) {
                // Display the move in Black's column
                this.model.setValueAt(moveText.toLowerCase(), currentRow, 1);
                currentRow++;
            }
        }

        if (!moveHistory.getMoves().isEmpty()) {
            final Move lastMove = moveHistory.getMoves().get(moveHistory.getSize() - 1);
            final String moveText = lastMove.toString();

            if (lastMove.getMovedPiece().getPieceAlliance().isWhite()) {
                this.model.setValueAt(moveText + calculateCheckCheckMateHash(board),
                                      currentRow,
                                      0);
            } else if (lastMove.getMovedPiece().getPieceAlliance().isBlack()) {
                this.model.setValueAt(moveText.toLowerCase() + calculateCheckCheckMateHash(board),
                        currentRow - 1,
                        1);
            }
        }
        // Auto scroll when moves start to leave the panel
        final JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    /**
     * @param board what the move takes place on
     * @return the indication for a check/checkmate
     */
    private String calculateCheckCheckMateHash(Board board) {
        if (board.getCurrentPlayer().isInCheckmate()) {
            return "#";
        } else if (board.getCurrentPlayer().isInCheck()) {
            return "+";
        }

        return "";
    }

    /**
     * This class allows for data to be managed in a tabular format.
     */
    private static class DataModel extends DefaultTableModel {
        private final List<Row> values;
        private static final String[] NAMES = { "White", "Black" };
    //------------------------------------------------------------------------------------------------------------------
    //-------------------------------------------------- Constructor ---------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
        protected DataModel() {
            this.values = new ArrayList<>();
        }
    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------- Main Methods ---------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
        /**
         * Clears the game history.
         */
        public void clear() {
            this.values.clear();
            setRowCount(0);
        }

        /**
         * @return how many rows the game history has
         */
        @Override
        public int getRowCount() {
            return this.values == null ? 0 : this.values.size();
        }

        /**
         * @return how many columns there are
         */
        @Override
        public int getColumnCount() {
            return NAMES.length;
        }

        /**
         * @param row    the row the move should be displayed on
         * @param column White/Black
         * @return the move based on the (row, column) pair
         */
        @Override
        public Object getValueAt(final int row, final int column) {
            final Row currentRow = this.values.get(row);
            if (column == 0) {
                return currentRow.getWhiteMove();
            } else if (column == 1) {
                return currentRow.getBlackMove();
            }

            return null;
        }

        /**
         * Sets the String version of the move at a certain (row, column) pair.
         *
         * @param aValue the String version of the move to be displayed
         * @param row    the row of the table
         * @param column the column of the table
         */
        @Override
        public void setValueAt(final Object aValue, final int row, final int column) {
            final Row currentRow;
            if (this.values.size() <= row) {
                currentRow = new Row();
                this.values.add(currentRow);
            } else {
                currentRow = this.values.get(row);
            }
            if (column == 0) {
                currentRow.setWhiteMove((String) aValue);
                fireTableRowsInserted(row, row);
            } else if (column == 1) {
                currentRow.setBlackMove((String) aValue);
                fireTableCellUpdated(row, column);
            }
        }

        /**
         *
         * @param column White/Black
         * @return the most specific superclass for all the cell values in the column
         */
        @Override
        public Class<?> getColumnClass(final int column) {
            return Move.class;
        }

        /**
         * @param column first or second column
         * @return White or Black
         */
        @Override
        public String getColumnName(final int column) {
            return NAMES[column];
        }
    }

    /**
     * This class represents the row in a table (or JTable).
     */
    private static class Row {
        private String whiteMove, blackMove;
    //------------------------------------------------------------------------------------------------------------------
    //-------------------------------------------------- Constructor ---------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
        protected Row() {
        }
    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------- Main Methods ---------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
        /**
         * @return White's move in String format
         */
        public String getWhiteMove() {
            return this.whiteMove;
        }

        /**
         * @return Black's move in String format
         */
        public String getBlackMove() {
            return this.blackMove;
        }

        /**
         * Sets the White move to move.
         *
         * @param move what to set whiteMove to
         */
        public void setWhiteMove(final String move) {
            this.whiteMove = move;
        }

        /**
         * Sets the Black move to move.
         *
         * @param move what to set blackMove to
         */
        public void setBlackMove(final String move) {
            this.blackMove = move;
        }
    }
}
