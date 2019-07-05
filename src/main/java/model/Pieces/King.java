package model.Pieces;

import model.Board;

import io.vavr.collection.List;
import io.vavr.Tuple2;

/**
 * King piece.
 */
public class King extends APiece {

  /**
   * King Constructor.
   * @param color - The color of the piece. Either BLACK or WHITE.
   * @param loc - The location of the piece.
   * @param timesMoved = The number of times the piece has moved.
   */
  public King(Color color, Tuple2<Integer, Integer> loc, int timesMoved) {
    super(color, loc, timesMoved);
  }

  public King(Color color, Tuple2<Integer, Integer> loc) {
    this(color, loc, 0);
  }

  @Override
  public IPiece move(Tuple2<Integer, Integer> to) {
    return new King(this.getColor(), to, this.getTimesMoved() + 1);
  }

  @Override
  public List<Tuple2<Integer, Integer>> getValidMoves(Board board) {
    // Base check.
    if (!board.inBounds(getLocation()) && board.getPiece(getLocation()).fold(()-> false, p -> p.equals(this))) return List.empty();

    int xLoc = getLocation()._1();
    int yLoc = getLocation()._2();
    List<Tuple2<Integer, Integer>> potentialValid = List.of(
        new Tuple2<>(xLoc + 1, yLoc),
        new Tuple2<>(xLoc + 1, yLoc + 1),
        new Tuple2<>(xLoc, yLoc + 1),
        new Tuple2<>(xLoc - 1, yLoc),
        new Tuple2<>(xLoc - 1, yLoc - 1),
        new Tuple2<>(xLoc, yLoc - 1),
        new Tuple2<>(xLoc + 1, yLoc - 1),
        new Tuple2<>(xLoc - 1, yLoc + 1));
    List<Tuple2<Integer, Integer>> invalidMoves = board.getPieces(getColor().equals(Color.WHITE) ? Color.BLACK : Color.WHITE).flatMap(p -> p.getValidMoves(board));
    return potentialValid.filter(board::inBounds).filter(move -> !invalidMoves.contains(move)).filter(move -> board.getPiece(move).fold(() -> true, p -> !p.getColor().equals(getColor())));
  }

  @Override
  public String toString() {
    return this.getColor() + "_KING";
  }
}
