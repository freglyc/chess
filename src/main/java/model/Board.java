package model;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import model.Pieces.IPiece;
import model.Pieces.King;
import model.Pieces.Pawn;
import model.Pieces.Rook;

public class Board {

  /**
   * Whose turn it is.
   */
  private IPiece.Color turn;
  /**
   * The number of turns that have occurred.
   */
  private int turnNum;
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
  public Board(IPiece.Color turn, Map<Integer, Map<Integer, Tile>> tiles, IModel2ViewAdapter m2v, int turnNum) {
    this.turn = turn;
    this.tiles = tiles;
    this.m2v = m2v;
    this.size = new Tuple2<>(tiles.size(), tiles.get(0).get().size());
    this.turnNum = turnNum;
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
    this.turnNum = 0;
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
    return tiles.get(location._1()).fold(Option::none, row -> row.get(location._2()));
  }

  /**
   * Gets a piece at a specific location.
   * @param location - The location of the piece.
   * @return a piece at the given location.
   */
  public Option<IPiece> getPiece(Tuple2<Integer, Integer> location) {
    return getTile(location).fold(Option::none, Tile::getPiece);
  }

  /**
   * Gets a list of pieces of a specific color.
   * @param color - The color of the pieces to get.
   * @return a list of pieces of a specfic color on the board.
   */
  public List<IPiece> getPieces(IPiece.Color color) {
    List<IPiece> pieces = List.empty();
    for (Tuple2<Integer, Map<Integer, Tile>> row : tiles) {
      for (Tuple2<Integer, Tile> col : row._2()) {
        if (col._2().getPiece().fold(() -> false, p -> p.getColor().equals(color))) {
          pieces = pieces.append(col._2().getPiece().get());
        }
      }
    }
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
              if (v2.getLocation().equals(p.get().getLocation())) {
//                m2v.addPiece(x.toString(), v2.getLocation());
                newTile = newTile.addPiece(p.get());
              }
              return new Tuple2<>(k2, newTile);
        }))), m2v, turnNum));
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
          if (v2.getLocation().equals(location)) {
//            m2v.removePiece(location);
            newTile = newTile.removePiece();
          }
          return new Tuple2<>(k2, newTile);
        }))), m2v, turnNum);
  }

  /**
   * Moves a piece from one location to another.
   * @param from - Where the piece is moving from.
   * @param to - Where the piece is moving to.
   * @return a new board with the updated state.
   */
  private Board move(Tuple2<Integer, Integer> from, Tuple2<Integer, Integer> to) {
    return this.getPiece(from).fold(() -> this, p -> this.removePiece(from).addPiece(Option.of(p.move(to))));
  }

  /**
   * Moves a piece while checking chess logic. i.e. castle, en passant, etc.
   * @param from - Where the piece is moving from.
   * @param to - Where the piece is moving to.
   * @return a new board with the updated state.
   */
  Board moveLogic(Tuple2<Integer, Integer> from, Tuple2<Integer, Integer> to) {

    // No piece at 'from' or 'to' is not a valid move or not player's turn.
    if (getPiece(from).fold(() -> true, p -> !p.getValidMoves(this).contains(to) || p.getColor() != turn)) return this;

    IPiece.Color color = getPiece(from).get().getColor();
    List<IPiece> pieces = getPieces(color);

    // Check for draw.
    if (pieces.flatMap(p -> p.getValidMoves(this)).isEmpty() && !kingInCheck(this, color)) {
      // Draw logic
      System.out.println("DRAW");
    }
    // Check for mate.
    if (pieces.flatMap(p -> p.getValidMoves(this).map(move -> kingInCheck(moveLogicHelper(p.getLocation(), move), color))).filter(bool -> bool.equals(false)).isEmpty()) {
      // Checkmate logic
      System.out.println("CHECKMATE");
    }
    Board newBoard = moveLogicHelper(from, to);

    if (!kingInCheck(newBoard, color) && !newBoard.equals(this)) return newBoard.toggleTurn();
    return this;
  }

  private Board moveLogicHelper(Tuple2<Integer, Integer> from, Tuple2<Integer, Integer> to) {
    // No piece at from or to is not a valid move.
    if (getPiece(from).fold(() -> true, p -> !p.getValidMoves(this).contains(to) || p.getColor() != turn)) return this;

    IPiece piece = getPiece(from).get();

    // Check castle.
    if (piece instanceof Rook && ((Rook) piece).canCastle(this, piece.getLocation()).fold(() -> false, move -> move.equals(to))) {
      IPiece king = getPiece(to).get();
      Tuple2<Integer, Integer> kingMove = new Tuple2<>(piece.getLocation()._1(), king.getLocation()._2() > piece.getLocation()._2() ? 1 : 6);
      Tuple2<Integer, Integer> rookMove = new Tuple2<>(piece.getLocation()._1(), king.getLocation()._2() > piece.getLocation()._2() ? 2 : 5);
      return this.removePiece(from).removePiece(to).addPiece(Option.of(piece.move(rookMove))).addPiece(Option.of(king.move(kingMove)));
    }

    // Check en passant.
    if (piece instanceof Pawn && ((Pawn) piece).canPassant(this).fold(() -> false, moves -> moves.contains(to))) {
      Tuple2<Integer, Integer> pawnToRemove = new Tuple2<>(piece.getLocation()._1(), piece.getLocation()._2() > to._2() ? piece.getLocation()._2() - 1 : piece.getLocation()._2() + 1);
      return this.removePiece(pawnToRemove).move(from, to);
    }

    // Check pawn at end of board.
    int end = piece.getColor() == IPiece.Color.BLACK ? 7 : 0;
    if (piece instanceof Pawn && to._1() == end) {
      m2v.displayPawnPromotion(to, piece.getColor());
    }

    // Check capture.
    if (this.getPiece(to).fold(() -> false, p -> !p.getColor().equals(piece.getColor()))) return this.removePiece(to).move(from, to);

    // Normal move.
    return this.move(from, to);
  }

  /**
   * Determines whether the king of a specific color is in check.
   * @param b - The board the king lies on.
   * @param color - The color of the king.
   * @return whether or not the king is in check.
   */
  private boolean kingInCheck(Board b, IPiece.Color color) {
    return ((King) b.getPieces(color).filter(p -> p instanceof King).get(0)).inCheck(b);
  }

  /**
   * Modifies a piece on the board.
   * @param location - The location of the piece.
   * @param newPiece - The new piece.
   * @return a new board with the updated piece.
   */
  public Board changePiece(Tuple2<Integer, Integer> location, Option<IPiece> newPiece) {
    return newPiece.fold(() -> this, np -> this.removePiece(location).addPiece(newPiece));
  }

  /**
   * Changes the turn.
   * @return a new board with the updated turn.
   */
  public Board toggleTurn() {
    return turn == IPiece.Color.WHITE ? new Board(IPiece.Color.BLACK, tiles, m2v, turnNum++) : new Board(IPiece.Color.WHITE, tiles, m2v, turnNum++);
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
