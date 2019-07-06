package model.Pieces;

import model.Board;

import io.vavr.collection.List;
import io.vavr.Tuple2;

/**
 * Queen piece.
 */
public class Queen extends APiece {

  /**
   * Queen constructor.
   * @param color - The color of the piece. Either BLACK or WHITE.
   * @param loc - The location of the piece.
   * @param timesMoved - The number of times the piece has moved.
   */
  public Queen(Color color, Tuple2<Integer, Integer> loc, int timesMoved) {
    super(color, loc, timesMoved);
  }

  /**
   * New queen constructor.
   * @param color - The color of the piece. Either BLACK or WHITE.
   * @param loc - The location of the piece.
   */
  public Queen(Color color, Tuple2<Integer, Integer> loc) {
    this(color, loc, 0);
  }

  @Override
  public IPiece move(Tuple2<Integer, Integer> to) {
    return new Queen(getColor(), to, getTimesMoved() + 1);
  }

  @Override
  public List<Tuple2<Integer, Integer>> getValidMoves(Board board) {
    // Base check.
    if (!board.inBounds(getLocation()) && board.getPiece(getLocation()).fold(()-> false, p -> p.equals(this))) return List.empty();

    List<Tuple2<Integer, Integer>> valid = List.empty();
    return valid.appendAll(getStraightMoves(board)).appendAll(getDiagonalMoves(board));
  }

  @Override
  public List<Tuple2<Integer, Integer>> getCheckMoves(Board board) {
    return getValidMoves(board);
  }

  @Override
  public String toString() {
    return this.getColor() + "_QUEEN";
  }
}
