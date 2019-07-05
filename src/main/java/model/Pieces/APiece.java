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

  /**
   * Gets a list of valid moves.
   * @param location - The location of the piece.
   * @param iter - The way to iterate over the board.
   * @param board - The board the piece lies on.
   * @return A list of valid moves.
   */
  private List<Tuple2<Integer, Integer>> validTterator(Tuple2<Integer, Integer> location, Tuple2<Integer, Integer> iter, Board board) {
    Tuple2<Integer, Integer> currentLocation = new Tuple2<>(location._1() + iter._1(), location._2() + iter._2());
    if (!board.inBounds(location) || !board.inBounds(currentLocation) || (board.getTile(currentLocation).get().isOccupied() && board.getPiece(currentLocation).get().getColor().equals(color)))
      return List.empty();
    else if (board.getTile(currentLocation).get().isOccupied() && !board.getPiece(currentLocation).get().getColor().equals(color))
      return List.of(currentLocation);
    return validTterator(currentLocation, iter, board).append(currentLocation);
  }

  /**
   * Gets valid moves in the straight directions.
   * @param board - The board the piece lies on.
   * @return A list of valid straight moves.
   */
  List<Tuple2<Integer, Integer>> getStraightMoves(Board board) {
    List<Tuple2<Integer, Integer>> valid = List.empty();
    return valid.appendAll(validTterator(location, new Tuple2<>(0, 1), board).iterator())
                .appendAll(validTterator(location, new Tuple2<>(0, -1), board).iterator())
                .appendAll(validTterator(location, new Tuple2<>(1, 0), board).iterator())
                .appendAll(validTterator(location, new Tuple2<>(-1, 0), board).iterator());
  }

  /**
   * Gets valid moves in the diagonal directions.
   * @param board - The board the piece lies on.
   * @return A list of valid diagonal moves.
   */
  List<Tuple2<Integer, Integer>> getDiagonalMoves(Board board) {
    List<Tuple2<Integer, Integer>> valid = List.empty();
    return valid.appendAll(validTterator(location, new Tuple2<>(1, 1), board).iterator())
               .appendAll(validTterator(location, new Tuple2<>(1, -1), board).iterator())
               .appendAll(validTterator(location, new Tuple2<>(-1, 1), board).iterator())
               .appendAll(validTterator(location, new Tuple2<>(-1, -1), board).iterator());
  }
}
