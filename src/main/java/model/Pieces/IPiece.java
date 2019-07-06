package model.Pieces;

import model.Board;

import io.vavr.Tuple2;
import io.vavr.collection.List;

/**
 * Piece interface representing a chess piece.
 */
public interface IPiece {

  /**
   * The choice of colors for each piece.
   */
  enum Color {
    WHITE, BLACK
  }

  /**
   * Gets the color of the piece.
   * @return the color of the piece.
   */
  Color getColor();

  /**
   * Gets the location of the piece.
   * @return the location of the piece.
   */
  Tuple2<Integer, Integer> getLocation();

  /**
   * Gets the number of times the piece has moved.
   * @return the number of times the piece has moved.
   */
  int getTimesMoved();

  /**
   * Returns a new piece with the updated location.
   * NOTE: Does not check if the to location is a valid place to move.
   * @param to - The location to move to.
   * @return a piece with the new location.
   */
  IPiece move(Tuple2<Integer, Integer> to);

  /**
   * Determines whether or not the potentialMove is valid.
   * @param board - The board the piece sits on.
   * @param potentialMove - The potential move the piece would make.
   * @return true if a valid move, false otherwise.
   */
  boolean getValidMove(Board board, Tuple2<Integer, Integer> potentialMove);

  /**
   * Gets a list of valid moves.
   * @param board - The board the piece sits on.
   * @return a list of valid moves.
   */
  List<Tuple2<Integer, Integer>> getValidMoves(Board board);

  /**
   * Gets a list of locations the opposing king cannot move.
   * @param board - The board the piece sits on.
   * @return a list of locations.
   */
  List<Tuple2<Integer, Integer>> getCheckMoves(Board board);
}
