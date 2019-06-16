package view;

import io.vavr.Tuple2;
import io.vavr.collection.List;

/**
 * View to model adapter.
 * Used to connect the view to the model.
 */
public interface IView2ModelAdapter {

  /**
   * Gets a list of all valid moves given the location of a piece.
   * @param location - the location of a piece.
   * @return a list of valid locations to move to.
   */
  List<Tuple2<Integer, Integer>> getValidMoves(Tuple2<Integer, Integer> location);

  /**
   * Moves a piece from one location to another.
   * @param from - Where the piece is moving from.
   * @param to - Where the piece is moving to.
   */
  void move(Tuple2<Integer, Integer> from, Tuple2<Integer, Integer> to);

  void changePiece(Tuple2<Integer, Integer> location, String oldPiece, String newPiece);
}
