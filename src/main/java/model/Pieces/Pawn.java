package model.Pieces;

import io.vavr.control.Option;
import model.Board;

import io.vavr.collection.List;
import io.vavr.Tuple2;
import model.Tile;

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
    forward = this.getColor() == Color.BLACK ? 1 : -1;
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

    // Base check.
    if (!board.inBounds(getLocation()) && board.getPiece(getLocation()).fold(()-> false, p -> p.equals(this))) return List.empty();

    int xLoc = getLocation()._1(); // The x location of the piece.
    int yLoc = getLocation()._2(); // The y location of the piece.
    List<Tuple2<Integer, Integer>> valid = List.empty();

    // Checks for standard forward move.
    Tuple2<Integer, Integer> forwardMove1 = new Tuple2<>(xLoc + forward, yLoc);
    if (board.inBounds(forwardMove1) && !board.getTile(forwardMove1).fold(() -> false, Tile::isOccupied))
      valid = valid.append(forwardMove1);

    // Checks for first time two moves forward.
    Tuple2<Integer, Integer> forwardMove2 = new Tuple2<>(xLoc + (forward * 2), yLoc);
    if (getTimesMoved() == 0 && board.inBounds(forwardMove1) && board.inBounds(forwardMove2) &&
            !board.getTile(forwardMove1).fold(() -> false, Tile::isOccupied) &&
            !board.getTile(forwardMove2).fold(() -> false, Tile::isOccupied))
      valid = valid.append(forwardMove2);

    // Checks for take.
    Tuple2<Integer, Integer> take1 = new Tuple2<>(xLoc + forward, yLoc + 1);
    Tuple2<Integer, Integer> take2 = new Tuple2<>(xLoc + forward, yLoc - 1);
    if (board.inBounds(take1) && board.getTile(take1).fold(() -> false, Tile::isOccupied) &&
            board.getPiece(take1).fold(() -> false, p -> !p.getColor().equals(getColor())))
      valid = valid.append(take1);
    if (board.inBounds(take2) && board.getTile(take2).fold(() -> false, Tile::isOccupied) &&
            board.getPiece(take2).fold(() -> false, p -> !p.getColor().equals(getColor())))
      valid = valid.append(take2);

    // Checks for en passant.
    if (!canPassant(board).isEmpty()) valid = valid.appendAll(canPassant(board).get());

    return valid;
  }

  @Override
  public List<Tuple2<Integer, Integer>> getCheckMoves(Board board) {
    int xLoc = getLocation()._1(); // The x location of the piece.
    int yLoc = getLocation()._2(); // The y location of the piece.
    return List.of(new Tuple2<>(xLoc + forward, yLoc + 1), new Tuple2<>(xLoc + forward, yLoc - 1));
  }

  public Option<List<Tuple2<Integer, Integer>>> canPassant(Board board) {

    List<Tuple2<Integer, Integer>> passant = List.empty();

    int xLoc = getLocation()._1(); // The x location of the piece.
    int yLoc = getLocation()._2(); // The y location of the piece.

    if ((getColor() == Color.BLACK && xLoc == 4) || (getColor() == Color.WHITE && xLoc == 3)) {
      Tuple2<Integer, Integer> passant1 = new Tuple2<>(xLoc + forward, yLoc + 1);
      Tuple2<Integer, Integer> toCheck1 = new Tuple2<>(xLoc, yLoc + 1);
      Tuple2<Integer, Integer> passant2 = new Tuple2<>(xLoc + forward, yLoc - 1);
      Tuple2<Integer, Integer> toCheck2 = new Tuple2<>(xLoc, yLoc - 1);
      if (board.inBounds(passant1) && board.inBounds(toCheck1) &&
              board.getTile(toCheck1).fold(() -> false, Tile::isOccupied) &&
              board.getTile(passant1).fold(() -> false, t -> !t.isOccupied()) &&
              board.getPiece(toCheck1).fold(() -> false, p -> p instanceof Pawn && !p.getColor().equals(getColor()) && p.getTimesMoved() == 1)) {
        passant = passant.append(passant1);
      }
      if (board.inBounds(passant2) && board.inBounds(toCheck2) &&
              board.getTile(toCheck2).fold(() -> false, Tile::isOccupied) &&
              board.getTile(passant2).fold(() -> false, t -> !t.isOccupied()) &&
              board.getPiece(toCheck2).fold(() -> false, p -> p instanceof Pawn && !p.getColor().equals(getColor()) && p.getTimesMoved() == 1)) {
        passant = passant.append(passant2);
      }

    }
    if (passant.isEmpty()) return Option.none();
    return Option.of(passant);
  }

  private int getForward() {
    return forward;
  }

  @Override
  public String toString() {
    return this.getColor() + "_PAWN";
  }
}
