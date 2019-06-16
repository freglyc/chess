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
    int xLoc = getLocation()._1();
    int yLoc = getLocation()._2();
    List<Tuple2<Integer, Integer>> valid = List.empty();
    List<Tuple2<Integer, Integer>> potentialValid = List.of(
        new Tuple2<>(xLoc + 1, yLoc),
        new Tuple2<>(xLoc + 1, yLoc + 1),
        new Tuple2<>(xLoc, yLoc + 1),
        new Tuple2<>(xLoc - 1, yLoc),
        new Tuple2<>(xLoc - 1, yLoc - 1),
        new Tuple2<>(xLoc, yLoc - 1),
        new Tuple2<>(xLoc + 1, yLoc - 1),
        new Tuple2<>(xLoc - 1, yLoc + 1));

    // TODO: implement logic.


//    Set<Tuple2<Integer, Integer>> placesCantMove = board.checked(this.getColor());
//    main:
//    for (Tuple loc : potentialValid) {
//      for (Tuple cantMove : placesCantMove) {
//        if (cantMove.equals(loc)) {
//          continue main;
//        }
//      }
//
//      if (this.getValidMove(board, loc)) {
//        valid.add(loc);
//      }
//    }
    return valid;
  }

//  @Override
//  public Set<Tuple2<Integer, Integer>> getCheckMoves(Board board) {
//    List<Tuple> check = new ArrayList<>();
//    int xLoc = this.getLocation().getX();
//    int yLoc = this.getLocation().getY();
//    check.add(new Tuple(xLoc + 1, yLoc));
//    check.add(new Tuple(xLoc + 1, yLoc + 1));
//    check.add(new Tuple(xLoc, yLoc + 1));
//    check.add(new Tuple(xLoc - 1, yLoc));
//    check.add(new Tuple(xLoc - 1, yLoc - 1));
//    check.add(new Tuple(xLoc, yLoc - 1));
//    check.add(new Tuple(xLoc + 1, yLoc - 1));
//    check.add(new Tuple(xLoc - 1, yLoc + 1));
//    return check;
//  }

  @Override
  public String toString() {
    return this.getColor() + "_KING";
  }
}
