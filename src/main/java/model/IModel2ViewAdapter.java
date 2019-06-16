package model;

import io.vavr.Tuple2;

/**
 * Model to view adapter.
 * Connects the model to the view.
 */
public interface IModel2ViewAdapter {

  /**
   * Creates a board in the view.
   * @param size - the size of the board.
   */
  void createBoard(Tuple2<Integer, Integer> size);

  /**
   * Adds pieces to the board in the view.
   * @param piece - The piece to add.
   * @param location - The location of where to add the piece.
   */
  void addPiece(String piece, Tuple2<Integer, Integer> location);

  /**
   * Removes pieces from the board in the view.
   * @param location - The location of the piece to remove.
   */
  void removePiece(Tuple2<Integer, Integer> location);

  /**
   * Displays popup where you can upgrade pawns.
   * @param location - The location of the pawn to upgrade.
   */
  void displayPawnChange(Tuple2<Integer, Integer> location);
}
