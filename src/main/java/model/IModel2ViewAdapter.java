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

  void removePiece(Tuple2<Integer, Integer> location);

  void move(String name, Tuple2<Integer, Integer> from, Tuple2<Integer, Integer> to);

  void displayPawnChange(Tuple2<Integer, Integer> location);
}
