package model;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import model.Pieces.IPiece;

public class Board {

  /**
   * Whose turn it is.
   */
  private IPiece.Color turn;
  /**
   * Mapping of row, col to tiles.
   */
  private Map<Integer, Map<Integer, Tile>> tiles;
  /**
   * The model to view adapter.
   */
  private IModel2ViewAdapter m2v;
  /**
   * the size of the board.
   */
  private Tuple2<Integer, Integer> size;

  /**
   * Board Constructor.
   * @param turn - The current player's turn.
   * @param tiles - The tiles on the board.
   * @param m2v - Model 2 view adapter.
   */
  public Board(IPiece.Color turn, Map<Integer, Map<Integer, Tile>> tiles, IModel2ViewAdapter m2v) {
    this.turn = turn;
    this.tiles = tiles;
    this.m2v = m2v;
    this.size = new Tuple2<>(tiles.size(), tiles.get(0).get().size());
  }

  /**
   * Empty Board Constructor.
   * @param size - The size of the board.
   * @param m2v - Model 2 view adapter.
   */
  public Board(Tuple2<Integer, Integer> size, IModel2ViewAdapter m2v) {
    this.turn = IPiece.Color.WHITE;
    this.tiles = HashMap.empty();
    for (int x = 0; x < size._1(); x++) {
      Map<Integer, Tile> m = HashMap.empty();
      for (int y = 0; y < size._2(); y++) {
        m = m.put(y, new Tile(Option.none(), new Tuple2<>(x, y)));
      }
      tiles = tiles.put(x, m);
    }
    this.m2v = m2v;
    this.size = size;
  }

  /**
   * Turn getter.
   * @return whose turn it is.
   */
  public IPiece.Color getTurn() {
    return turn;
  }

  /**
   * Tiles getter.
   * @return the tiles.
   */
  public Map<Integer, Map<Integer, Tile>> getTiles() {
    return tiles;
  }

  /**
   * Model 2 view getter.
   * @return the m2v.
   */
  public IModel2ViewAdapter getM2v() {
    return m2v;
  }

  /**
   * Size getter,
   * @return the size of the board.
   */
  public Tuple2<Integer, Integer> getSize() {
    return size;
  }

  /**
   * Gets a tile at a specific location
   * @param location - The location of the tile.
   * @return a tile at the given location.
   */
  public Option<Tile> getTile(Tuple2<Integer, Integer> location) {
    if (!inBounds(location)) return Option.none();
    return tiles.get(location._1()).get().get(location._2());
  }

  /**
   * Gets a piece at a specific location.
   * @param location - The location of the piece.
   * @return a piece at the given location.
   */
  public Option<IPiece> getPiece(Tuple2<Integer, Integer> location) {
    if (!inBounds(location)) return Option.none();
    return getTile(location).get().getPiece();
  }

  /**
   * Gets a list of pieces of a specific color.
   * @param color - The color of the pieces to get.
   * @return a list of pieces of a specfic color on the board.
   */
  public List<IPiece> getPieces(IPiece.Color color) {
    List<IPiece> pieces = List.empty();
    tiles.forEach((k1, v1) -> v1.forEach((k2, v2) -> {
      Option<IPiece> piece = v2.getPiece();
      if (piece.fold(() -> false, p -> p.getColor() == color)) pieces.append(piece.get());
    }));
    return pieces;
  }

  /**
   * Adds a piece to the board.
   * @param p - the piece to add.
   * @return a new board with the added piece.
   */
  public Board addPiece(Option<IPiece> p) {
    return p.fold(() -> this,
        x -> new Board(turn, tiles.map((k1, v1) -> new Tuple2<>(k1,
            v1.map((k2, v2) -> {
              Tile newTile = v2;
              if (v2.getLocation().equals(p.get().getLocation())) newTile = newTile.addPiece(p.get());
              return new Tuple2<>(k2, newTile);
        }))), m2v));
  }

  /**
   * Removes a piece from the board.
   * @param location - The location of the piece to remove.
   * @return a new board with the piece removed.
   */
  public Board removePiece(Tuple2<Integer, Integer> location) {
    return new Board(turn, tiles.map((k1, v1) -> new Tuple2<>(k1,
        v1.map((k2, v2) -> {
          Tile newTile = v2;
          if (v2.getLocation().equals(location)) newTile = newTile.removePiece();
          return new Tuple2<>(k2, newTile);
        }))), m2v);
  }

  /**
   * Moves a piece from one location to another.
   * @param from - Where the piece is moving from.
   * @param to - Where the piece is moving to.
   * @return a new board with the updated state.
   */
  private Board move(Tuple2<Integer, Integer> from, Tuple2<Integer, Integer> to) {
    return this.getPiece(from).fold(() -> this, p -> this.addPiece(Option.of(p.move(to))));
  }

  /**
   * Moves a piece while checking chess logic. i.e. castle, en pessant, etc.
   * @param from - Where the piece is moving from.
   * @param to - Where the piece is moving to.
   * @return a new board with the updated state.
   */
  Board moveLogic(Tuple2<Integer, Integer> from, Tuple2<Integer, Integer> to) {
    // Check king mated.

    // Check draw.

    // Check king in check.

    // Check castle.

    // Check en pessant.

    // Check pawn at end of board.

    toggleTurn();
    return move(from, to);
  }

  /**
   * Modifies a piece on the board.
   * @param location - The location of the piece.
   * @param oldPiece - The old piece.
   * @param newPiece - The new piece.
   * @return a new board with the updated piece.
   */
  public Board changePiece(Tuple2<Integer, Integer> location, Option<IPiece> oldPiece, Option<IPiece> newPiece) {
    return oldPiece.fold(() -> this, op -> newPiece.fold(() -> this, np -> this.removePiece(location).addPiece(newPiece)));
  }

  /**
   * Changes the turn.
   * @return a new board with the updated turn.
   */
  public Board toggleTurn() {
    return turn == IPiece.Color.WHITE ? new Board(IPiece.Color.BLACK, tiles, m2v) : new Board(IPiece.Color.WHITE, tiles, m2v);
  }

  /**
   * Checks that a location is in the board bounds.
   * @param location - A given location.
   * @return true if in bounds, false otherwise.
   */
  public boolean inBounds(Tuple2<Integer, Integer> location) {
    return location._1() < size._1() && location._2() < size._2() && location._1() >= 0 && location._2() >= 0;
  }
}
