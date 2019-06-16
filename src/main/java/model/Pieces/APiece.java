package model.Pieces;

import model.Board;

import io.vavr.collection.List;
import io.vavr.Tuple2;

/**
 * Abstract class representing a chess piece.
 */
public abstract class APiece implements IPiece{

  /**
   * Color of the piece.
   */
  private final Color color;

  /**
   * Location of the piece.
   */
  private final Tuple2<Integer, Integer> location;

  /**
   * The number of times this piece has moved.
   */
  private final int timesMoved;

  /**
   * Piece Constructor.
   * @param color - The color of the piece. Either BLACK or WHITE.
   * @param location - The location of the piece.
   * @param timesMoved = The number of times this piece has moved.
   */
  public APiece(Color color, Tuple2<Integer, Integer> location, int timesMoved) {
    this.color = color;
    this.location = location;
    this.timesMoved = timesMoved;
  }

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public Tuple2<Integer, Integer> getLocation() {
    return location;
  }

  @Override
  public int getTimesMoved() {
    return timesMoved;
  }

  @Override
  public boolean getValidMove(Board board, Tuple2<Integer, Integer> potentialMove) {
    return this.getValidMoves(board).contains(potentialMove);
  }

  List<Tuple2<Integer, Integer>> getStraightMoves(Board board) {
    // TODO: Implement this method.
    return List.empty();
  }

  List<Tuple2<Integer, Integer>> getDiagonalMoves(Board board) {
    // TODO: Implement this method.
    return List.empty();
  }

//  @Override
//  public IPiece move(Tuple to) {
//    return new Piece(this.getColor(), to, this.getTimesMoved());
//  }

//  @Override
//  public boolean getValidMove(Board board, Tuple potentialMove) {
//    // TODO: Implement this method.
//    return true;
//  }
//
//  @Override
//  public List<Tuple> getValidMoves(Board board) {
//    // TODO: Implement this method.
//    return List.empty();
//  }


//  /**
//   * Gets the color of the piece.
//   * @return the color.
//   */
//  public Color getColor() {
//    return color;
//  }
//
//  /**
//   * Gets the location of the piece.
//   * @return the location.
//   */
//  public Tuple getLocation() {
//    return location;
//  }
//
//  public int getTimesMoved() {
//    return timesMoved;
//  }
//
//  /**
//   * Sets the location of the piece.
//   * @param to - The location the piece is moving to.
//   */
//  public APiece move(Tuple to) {
//    return new APiece(this.getColor(), to, this.getTimesMoved()) {
//
//    };
//  }
//
//
//
//  /**
//   * Gets all valid locations the piece could move to.
//   * @param board - The board the piece is on.
//   * @return A list of valid locations the piece could move to.
//   */
//  public abstract List<Tuple> getValidMoves(Board board);
//
//  /**
//   * Gets a list of all locations the opposing king cannot move.
//   * @param board - The board the piece is on.
//   * @return a list of all locations the opposing king cannot move.
//   */
//  public abstract List<Tuple> getCheckMoves(Board board);
//
//  @Override
//  public abstract String toString();
//
//  /**
//   * Gets all valid straight moves.
//   * Meaning all valid rook moves.
//   * @param board - The board to check.
//   * @return a list of horizontal and vertical moves.
//   */
//  List<Tuple> getValidStraightMoves(Board board, boolean includeOwnPiece) {
//    List<Tuple> valid = new ArrayList<>();
//
//    valid.addAll(validIterator(board, 1, 0, includeOwnPiece));
//    valid.addAll(validIterator(board, -1, 0, includeOwnPiece));
//    valid.addAll(validIterator(board, 0, 1, includeOwnPiece));
//    valid.addAll(validIterator(board, 0, -1, includeOwnPiece));
//
//    return valid;
//  }
//
//  /**
//   * Gets all valid diagonal moves.
//   * Meaning all valid bishop moves.
//   * @param board - The board to check.
//   * @return a list of valid diagonal moves.
//   */
//  List<Tuple> getValidDiagonalMoves(Board board, boolean includeOwnPiece) {
//    List<Tuple> valid = new ArrayList<>();
//
//    valid.addAll(validIterator(board, 1, 1, includeOwnPiece));
//    valid.addAll(validIterator(board, 1, -1, includeOwnPiece));
//    valid.addAll(validIterator(board, -1, 1, includeOwnPiece));
//    valid.addAll(validIterator(board, -1, -1, includeOwnPiece));
//
//    return valid;
//  }
//
//  /**
//   * Iterates starting at the piece's location and increments by
//   * horizIterate and vertIterate finding all valid moves.
//   * @param board - The board to iterate over.
//   * @param horizIterate - The horizontal iterator.
//   * @param vertIterate - The vertical iterator.
//   * @return a list of valid moves.
//   */
//  private List<Tuple> validIterator(Board board, int horizIterate, int vertIterate, boolean includeOwnPiece) {
//    List<Tuple> valid = new ArrayList<>();
//    int xLoc = getLocation().getX(); // The x location of the piece.
//    int yLoc = getLocation().getY(); // The y location of the piece.
//    Tuple currentLocation; // Tracks the current potentially valid location.
//
//    // Initial check to ensure the piece is in the bounds of the board. Checks to ensure it's the same piece.
//    if (board.inBounds(getLocation()) && this.equals(board.getPiece(getLocation()))) {
//      currentLocation = new Tuple(xLoc + horizIterate, yLoc + vertIterate); // The current location.
//      while (getValidMove(board, currentLocation, includeOwnPiece)) {
//        valid.add(currentLocation);
//        if (board.getTile(currentLocation).isOccupied()) {
//          break;
//        }
//        currentLocation = new Tuple(currentLocation.getX() + horizIterate, currentLocation.getY() + vertIterate);
//      }
//    }
//    return valid;
//  }
//
//  boolean getValidMove(Board board, Tuple location, boolean includeOwnPiece) {
//    if (!board.inBounds(location))  {
//      return false;
//    }
//    if (!board.getTile(location).isOccupied()) {
//      return true;
//    }
//
//    if (board.getTile(location).isOccupied() && includeOwnPiece){
//      return true;
//    }
//
//    return board.getTile(location).isOccupied() && board.getPiece(location).getColor() != this.getColor();
//  }
}
