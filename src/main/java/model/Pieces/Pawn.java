package model.Pieces;

import model.Board;

import io.vavr.collection.List;
import io.vavr.Tuple2;

/**
 * Pawn piece.
 */
public class Pawn extends APiece {

  private int forward;

  /**
   * Pawn Constructor.
   * @param color - The color of the piece. Either BLACK or WHITE.
   * @param loc - The location of the piece.
   */
  public Pawn(Color color, Tuple2<Integer, Integer> loc, int timesMoved) {
    super(color, loc, timesMoved);
    if (this.getColor() == Color.BLACK) {
      forward = 1;
    } else {
      forward = -1;
    }
  }

  public Pawn(Color color, Tuple2<Integer, Integer> loc) {
    this(color, loc, 0);
  }

  @Override
  public IPiece move(Tuple2<Integer, Integer> to) {
    return new Pawn(getColor(), to, getTimesMoved() + 1);
  }

  @Override
  public List<Tuple2<Integer, Integer>> getValidMoves(Board board) {
    int xLoc = getLocation()._1(); // The x location of the piece.
    int yLoc = getLocation()._2(); // The y location of the piece.
    List<Tuple2<Integer, Integer>> valid = List.empty();

    // TODO: implement logic.

//    // Initial check to ensure the piece is in the bounds of the board. Checks to ensure it's the same piece.
//    if (board.inBounds(getLocation()) && this.equals(board.getPiece(getLocation()))) {
//      // Checks if the forward move is valid.
//      Tuple forwardMove = new Tuple(xLoc + forward, yLoc);
//      if (board.inBounds(forwardMove) && !board.getTile(forwardMove).isOccupied()) {
//        valid.add(forwardMove);
//      }
//
//      // Checks for the two move case if the pawn has not been moved.
//      Tuple forwardMove2Spaces = new Tuple(xLoc + (forward*2), yLoc);
//      if (board.inBounds(forwardMove2Spaces)
//              && getNumberTimeMoved() == 0
//              && !board.getTile(forwardMove2Spaces).isOccupied()
//              && !board.getTile(new Tuple(forwardMove2Spaces.getX() - forward, yLoc)).isOccupied()) {
//        valid.add(forwardMove2Spaces);
//      }
//
//      // Check for en pessant. .
//      if ((getColor() == Color.BLACK && xLoc == 4) || (getColor() == Color.WHITE && xLoc == 3)) {
//        Tuple enPassant1 = new Tuple(xLoc + forward, yLoc + 1);
//        Tuple otherLocation1 = new Tuple(xLoc, yLoc + 1);
//        System.out.println("HERE 1");
//        if (board.getTile(otherLocation1).isOccupied()
//                && board.getPiece(otherLocation1) instanceof Pawn
//                && board.getPiece(otherLocation1).getNumberTimeMoved() == 1
//                && board.getPiece(otherLocation1).getColor() != getColor()) {
//          valid.add(enPassant1);
//        }
//
//        Tuple enPassant2 = new Tuple(xLoc + forward, yLoc - 1);
//        Tuple otherLocation2 = new Tuple(xLoc, yLoc - 1);
//        if (board.getTile(otherLocation2).isOccupied()
//                && board.getPiece(otherLocation2) instanceof Pawn
//                && board.getPiece(otherLocation2).getNumberTimeMoved() == 1
//                && board.getPiece(otherLocation2).getColor() != getColor()) {
//          valid.add(enPassant2);
//        }
//      }
//
//      // Checks the two diagonal moves.
//      Tuple forwardDiagMove1 = new Tuple(xLoc + forward, yLoc + 1);
//      if (board.inBounds(forwardDiagMove1) && board.getTile(forwardDiagMove1).isOccupied() && board.getPiece(forwardDiagMove1).getColor() != this.getColor()) {
//        valid.add(forwardDiagMove1);
//      }
//      Tuple forwardDiagMove2 = new Tuple(xLoc + forward, yLoc - 1);
//      if (board.inBounds(forwardDiagMove2) && board.getTile(forwardDiagMove2).isOccupied() && board.getPiece(forwardDiagMove2).getColor() != this.getColor()) {
//        valid.add(forwardDiagMove2);
//      }
//    }

    return valid;
  }

//  @Override
//  public List<Tuple> getCheckMoves(Board board) {
//    List<Tuple> valid = new ArrayList<>();
//    int xLoc = getLocation().getX(); // The x location of the piece.
//    int yLoc = getLocation().getY(); // The y location of the piece.
//    System.out.println(forward);
//    valid.add(new Tuple(xLoc + forward, yLoc + 1));
//    valid.add(new Tuple(xLoc + forward, yLoc - 1));
//
//    System.out.println("PAWN: " + valid.get(0).toString());
//    System.out.println("PAWN: " + valid.get(1).toString());
//
//    return valid;
//  }

  public int getForward() {
    return forward;
  }

  @Override
  public String toString() {
    return this.getColor() + "_PAWN";
  }
}
