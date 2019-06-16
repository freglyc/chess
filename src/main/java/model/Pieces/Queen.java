package model.Pieces;

import model.Board;

import io.vavr.collection.List;
import io.vavr.Tuple2;

/**
 * Queen piece.
 */
public class Queen extends APiece {

  /**
   * Queen Constructor.
   * @param color - The color of the piece. Either BLACK or WHITE.
   * @param loc - The location of the piece.
   */
  public Queen(Color color, Tuple2<Integer, Integer> loc, int timesMoved) {
    super(color, loc, timesMoved);
  }

  public Queen(Color color, Tuple2<Integer, Integer> loc) {
    this(color, loc, 0);
  }

  @Override
  public IPiece move(Tuple2<Integer, Integer> to) {
    return new Queen(getColor(), to, getTimesMoved() + 1);
  }

  @Override
  public List<Tuple2<Integer, Integer>> getValidMoves(Board board) {
    List<Tuple2<Integer, Integer>> valid = List.empty();
    valid = valid.appendAll(getStraightMoves(board));
    valid = valid.appendAll(getDiagonalMoves(board));

    return valid;
  }

//  @Override
//  public List<Tuple> getCheckMoves(Board board) {
//    List<Tuple> valid = new ArrayList<>();
//    valid.addAll(getValidStraightMoves(board, true));
//    valid.addAll(getValidDiagonalMoves(board, true));
//    return valid;
//  }

  @Override
  public String toString() {
    return this.getColor() + "_QUEEN";
  }
}
