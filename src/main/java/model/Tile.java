package model;

import model.Pieces.IPiece;
import io.vavr.Tuple2;
import io.vavr.control.Option;

/**
 * Tile in the model.
 */
public class Tile {

  /**
   * The piece that sits on the tile.
   */
  private Option<IPiece> piece;
  /**
   * The location of the tile.
   */
  private Tuple2<Integer, Integer> location;

  /**
   * Tile constructor.
   * @param piece - The piece on the tile.
   * @param loc - The location of the tile.
   */
  public Tile(Option<IPiece> piece, Tuple2<Integer, Integer> loc) {
    this.piece = piece;
    this.location = loc;
  }

  /**
   * Other tile constructor.
   * Used when tile is empty.
   * @param loc - The location of the tile.
   */
  public Tile(Tuple2<Integer, Integer> loc) {
    this(Option.none(), loc);
  }

  /**
   * Gets the piece on the tile.
   * @return the piece on the tile.
   */
  public Option<IPiece> getPiece() {
    return piece;
  }

  /**
   * True if occupied. False otherwise.
   * @return whether or not the tile is occupied.
   */
  public boolean isOccupied() {
    return !piece.isEmpty();
  }

  /**
   * Adds a piece to the tile.
   * @param p - The piece to add.
   * @return a new tile with the updated piece if it could be added.
   */
  public Tile addPiece(IPiece p) {
    if (piece.isEmpty() || piece.get().getColor() != p.getColor()) {
      return new Tile(Option.of(p), location);
    }
    return new Tile(piece, location);
  }

  /**
   * Removes a piece from the tile.
   * @return a new tile with the piece removed.
   */
  public Tile removePiece() {
    return new Tile(Option.none(), location);
  }

  /**
   * Gets the location of the tile.
   * @return the location of the tile.
   */
  public Tuple2<Integer, Integer> getLocation() {
    return location;
  }
}
