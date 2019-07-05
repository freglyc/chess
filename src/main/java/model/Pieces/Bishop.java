package model.Pieces;

import model.Board;

import io.vavr.collection.List;
import io.vavr.Tuple2;

/**
 * Bishop piece.
 */
public class Bishop extends APiece {

  /**
   * Bishop Constructor.
   * @param color - The color of the piece. Either BLACK or WHITE.
   * @param loc - The location of the piece.
   * @param timesMoved = The number of times the piece has moved.
   */
  public Bishop(Color color, Tuple2<Integer, Integer> loc, int timesMoved) {
    super(color, loc, timesMoved);
  }

  public Bishop(Color color, Tuple2<Integer, Integer> loc) {
   this(color, loc, 0);
  }

  @Override
  public IPiece move(Tuple2<Integer, Integer> to) {
    return new Bishop(this.getColor(), to, this.getTimesMoved() + 1);
  }

  @Override
  public List<Tuple2<Integer, Integer>> getValidMoves(Board board) {
    // Base check.
    if (!board.inBounds(getLocation()) && board.getPiece(getLocation()).fold(()-> false, p -> p.equals(this))) return List.empty();
    return getDiagonalMoves(board);
  }

  @Override
  public List<Tuple2<Integer, Integer>> getCheckMoves(Board board) {
    return getValidMoves(board);
  }

  @Override
  public String toString() {
    return this.getColor() + "_BISHOP";
  }
}
