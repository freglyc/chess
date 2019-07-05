package model.Pieces;

import model.Board;

import io.vavr.collection.List;
import io.vavr.Tuple2;

/**
 * Knight piece.
 */
public class Knight extends APiece {

  /**
   * Knight Constructor.
   * @param color - The color of the piece. Either BLACK or WHITE.
   * @param loc - The location of the piece.
   */
  public Knight(Color color, Tuple2<Integer, Integer> loc, int timesMoved) {
    super(color, loc, timesMoved);
  }

  public Knight(Color color, Tuple2<Integer, Integer> loc) {
    this(color, loc, 0);
  }

  @Override
  public IPiece move(Tuple2<Integer, Integer> to) {
    return new Knight(getColor(), to, getTimesMoved() + 1);
  }

  @Override
  public List<Tuple2<Integer, Integer>> getValidMoves(Board board) {
    // Base check.
    if (!board.inBounds(getLocation()) && board.getPiece(getLocation()).fold(()-> false, p -> p.equals(this))) return List.empty();

    int xLoc = this.getLocation()._1();
    int yLoc = this.getLocation()._2();
    List<Tuple2<Integer, Integer>> potentialValid = List.of(
        new Tuple2<>(xLoc + 1, yLoc + 2),
        new Tuple2<>(xLoc + 2, yLoc + 1),
        new Tuple2<>(xLoc - 1, yLoc - 2),
        new Tuple2<>(xLoc - 2, yLoc - 1),
        new Tuple2<>(xLoc - 1, yLoc + 2),
        new Tuple2<>(xLoc + 1, yLoc - 2),
        new Tuple2<>(xLoc - 2, yLoc + 1),
        new Tuple2<>(xLoc + 2, yLoc - 1)
    );
    return potentialValid
               .filter(board::inBounds)
               .filter(move -> !board.getTile(move).get().isOccupied() ||
                                   (board.getTile(move).get().isOccupied() &&
                                        !board.getPiece(move).get().getColor().equals(getColor())));
  }

  @Override
  public String toString() {
    return this.getColor() + "_KNIGHT";
  }
}
