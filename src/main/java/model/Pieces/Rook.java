package model.Pieces;

import io.vavr.Tuple;
import io.vavr.control.Option;
import model.Board;

import io.vavr.collection.List;
import io.vavr.Tuple2;
import model.Tile;

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
    // Base check.
    if (!board.inBounds(getLocation()) && board.getPiece(getLocation()).fold(()-> false, p -> p.equals(this))) return List.empty();

    List<Tuple2<Integer, Integer>> valid = getStraightMoves(board);
    if (!canCastle(board, getLocation()).isEmpty()) valid = valid.append(canCastle(board, getLocation()).get());
    return valid;
  }

  /**
   * Determines whether a rook can castle.
   * @param board - The board the rook lies on.
   * @param location - The location of the rook.
   * @return an optional location of the castle move (the king location).
   */
  public Option<Tuple2<Integer, Integer>> canCastle(Board board, Tuple2<Integer, Integer> location) {

    int row = this.getColor() == Color.BLACK ? 0 : 7;
    Tuple2<Integer, Integer> kingLoc = new Tuple2<>(row, 4);

    if (this.getTimesMoved() == 0 && board.getPiece(kingLoc).fold(() -> false,
        p -> p instanceof King && p.getTimesMoved() == 0 && p.getColor().equals(getColor()))) {
      if (location.equals(kingLoc)) return Option.of(kingLoc);
      else if (board.getTile(location).fold(() -> true, t -> t.getPiece().fold(() -> false, p -> !p.equals(this)))) return Option.none();
      else return canCastle(board, new Tuple2<>(row, kingLoc._2() > location._2() ? location._2() + 1 : location._2() - 1));
    }
    return Option.none();
  }

  @Override
  public String toString() {
    return this.getColor() + "_ROOK";
  }
}
