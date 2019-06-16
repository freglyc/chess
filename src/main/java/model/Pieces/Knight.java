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
    int xLoc = this.getLocation()._1();
    int yLoc = this.getLocation()._2();
    List<Tuple2<Integer, Integer>> valid = List.empty();
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

    // TODO: implement logic.

//    for (Tuple2 loc : potentialValid) {
//      if (this.getValidMove(board, loc, false)) {
//        valid.add(loc);
//      }
//    }

    return valid;
  }

//  @Override
//  public List<Tuple> getCheckMoves(Board board) {
//    List<Tuple> valid = new ArrayList<>();
//    List<Tuple> potentialValid = new ArrayList<>();
//    int xLoc = this.getLocation().getX();
//    int yLoc = this.getLocation().getY();
//
//    potentialValid.add(new Tuple(xLoc + 1, yLoc + 2));
//    potentialValid.add(new Tuple(xLoc + 2, yLoc + 1));
//    potentialValid.add(new Tuple(xLoc - 1, yLoc - 2));
//    potentialValid.add(new Tuple(xLoc - 2, yLoc - 1));
//    potentialValid.add(new Tuple(xLoc - 1, yLoc + 2));
//    potentialValid.add(new Tuple(xLoc + 1, yLoc - 2));
//    potentialValid.add(new Tuple(xLoc - 2, yLoc + 1));
//    potentialValid.add(new Tuple(xLoc + 2, yLoc - 1));
//
//    for (Tuple loc : potentialValid) {
//      if (this.getValidMove(board, loc, true)) {
//        valid.add(loc);
//      }
//    }
//
//    return valid;
//  }

  @Override
  public String toString() {
    return this.getColor() + "_KNIGHT";
  }
}
