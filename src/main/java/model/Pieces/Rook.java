package model.Pieces;

import model.Board;

import io.vavr.collection.List;
import io.vavr.Tuple2;

/**
 * Rook piece.
 */
public class Rook extends APiece {

  /**
   * Rook Constructor.
   * @param color - The color of the piece. Either BLACK or WHITE.
   * @param loc - The location of the piece.
   */
  public Rook(Color color, Tuple2<Integer, Integer> loc, int timesMoved) {
    super(color, loc, timesMoved);
  }

  public Rook(Color color, Tuple2<Integer, Integer> loc) {
    this(color, loc, 0);
  }

  @Override
  public IPiece move(Tuple2<Integer, Integer> to) {
    return new Rook(getColor(), to, getTimesMoved() + 1);
  }

  @Override
  public List<Tuple2<Integer, Integer>> getValidMoves(Board board) {
    List<Tuple2<Integer, Integer>> valid = getStraightMoves(board);

    // TODO: implement logic.

//    //Check for potential castle.
//    int row;
//    if (this.getColor() == Color.BLACK) {
//      row = 0;
//    } else {
//      row = 7;
//    }
//    Tuple kingLocation = new Tuple(row, 4);
//    if (canCastle(board, kingLocation)) {
//      valid.add(kingLocation);
//    }

    return valid;
  }

//  public boolean canCastle(Board board, Tuple kingLocation) {
//
//    boolean valid = false;
//
//    if (this.getNumberTimeMoved() == 0
//            && board.getTile(kingLocation).isOccupied()
//            && board.getPiece(kingLocation) instanceof King) {
//      APiece king = board.getPiece(kingLocation); // Gets the piece.
//      if (king.getNumberTimeMoved() == 0
//              && king.getColor() == getColor()) {
//
//        // Gets the spaces between the king and rook.
//        int numSpaces = Math.abs(kingLocation.getY() - this.getLocation().getY()) - 1;
//        List<Tuple> spaces = new ArrayList<>();
//        for (int i = 0; i < numSpaces; i++) {
//          if (numSpaces == 2) {
//            spaces.add(new Tuple(kingLocation.getX(), kingLocation.getY() + i + 1));
//          } else {
//            spaces.add(new Tuple(kingLocation.getX(), kingLocation.getY() - i - 1));
//          }
//        }
//
//        // Checks to ensure spaces in between are valid
//        Set<Tuple> check = board.checked(this.getColor()); // Set of locations the king cannot move.
//        int count = 0;
//        // Checks each necessary space to ensure they are not in a check position.
//        // Checks to ensure all spaces are in between are not occupied.
//        // Checks to ensure the king is not in check.
//        top:
//        for (Tuple space : spaces) {
//          if (board.getTile(space).isOccupied() && count < 2) {
//            valid = false;
//            break;
//          }
//          for (Tuple loc : check) {
//            if (loc.equals(space) && count < 2) {
//              valid = false;
//              break top;
//            }
//            if (loc.equals(kingLocation)) {
//              valid = false;
//              break top;
//            }
//          }
//          count ++;
//          valid = true;
//        }
//      }
//    }
//    return valid;
//  }

//  @Override
//  public List<Tuple> getCheckMoves(Board board) {
//    return getValidStraightMoves(board, true);
//  }

  @Override
  public String toString() {
    return this.getColor() + "_ROOK";
  }
}
