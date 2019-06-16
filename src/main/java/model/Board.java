package model;

import io.vavr.Tuple2;
import io.vavr.collection.*;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import model.Pieces.*;

import java.util.function.Function;

import static io.vavr.API.*;
import static io.vavr.Patterns.$None;
import static io.vavr.Patterns.$Some;

/**
 * Board used in the model.
 */
public class Board {
  /**
   * Black pieces on the board.
   */
  private List<IPiece> blackPieces;
  /**
   * White pieces on the board.
   */
  private List<IPiece> whitePieces;
  /**
   * Whose turn it is.
   */
  private IPiece.Color turn;
  /**
   * Pieces that can be added to the board.
   */
  private Map<String, Function<Tuple2<Integer, Integer>, IPiece>> validPieces = HashMap.empty();
  /**
   * The tiles that make up the board.
   */
  private Tile[][] board;
  /**
   * The board size.
   */
  private Tuple2<Integer, Integer> size;
  /**
   * The model to view adapter.
   */
  private IModel2ViewAdapter m2v;

  /**
   * Board Constructor.
   */
  public Board(Tuple2<Integer, Integer> size, IModel2ViewAdapter m2v) {
    this.size = size;
    this.m2v = m2v;
    this.turn = IPiece.Color.WHITE;
    blackPieces = List.empty();
    whitePieces = List.empty();
    board = new Tile[size._1()][size._2()];
    this.initBoard();
  }

  /**
   * Setup the board.
   */
  private void initBoard() {
    // Creates the tiles.
    for (int i = 0; i < size._1(); i++) {
      for (int j = 0; j < size._2(); j++) {
        board[i][j] = new Tile(new Tuple2<>(i, j));
      }
    }
    validPieces.put("WHITE_KNIGHT", (Tuple2<Integer, Integer> location) -> new Knight(APiece.Color.WHITE, location));
    validPieces.put("BLACK_KNIGHT", (Tuple2<Integer, Integer> location) -> new Knight(APiece.Color.BLACK, location));
    validPieces.put("WHITE_ROOK", (Tuple2<Integer, Integer> location) -> new Rook(APiece.Color.WHITE, location));
    validPieces.put("BLACK_ROOK", (Tuple2<Integer, Integer> location) -> new Rook(APiece.Color.BLACK, location));
    validPieces.put("WHITE_BISHOP", (Tuple2<Integer, Integer> location) -> new Bishop(APiece.Color.WHITE, location));
    validPieces.put("BLACK_BISHOP", (Tuple2<Integer, Integer> location) -> new Bishop(APiece.Color.BLACK, location));
    validPieces.put("WHITE_QUEEN", (Tuple2<Integer, Integer> location) -> new Queen(APiece.Color.WHITE, location));
    validPieces.put("BLACK_QUEEN", (Tuple2<Integer, Integer> location) -> new Queen(APiece.Color.BLACK, location));
    validPieces.put("WHITE_KING", (Tuple2<Integer, Integer> location) -> new King(APiece.Color.WHITE, location));
    validPieces.put("BLACK_KING", (Tuple2<Integer, Integer> location) -> new King(APiece.Color.BLACK, location));
    validPieces.put("WHITE_PAWN", (Tuple2<Integer, Integer> location) -> new Pawn(APiece.Color.WHITE, location));
    validPieces.put("BLACK_PAWN", (Tuple2<Integer, Integer> location) -> new Pawn(APiece.Color.BLACK, location));
  }

  /**
   * Gets the list of black pieces on the board.
   * @return the black pieces on the board.
   */
  private List<IPiece> getBlackPieces() {
    return blackPieces;
  }

  /**
   * Gets a list of white pieces on the board.
   * @return the white pieces on the board.
   */
  private List<IPiece> getWhitePieces() {
    return whitePieces;
  }

  /**
   * Adds a piece to the board if it is not occupied.
   * @param piece - the piece to add.
   */
  public Board addPiece(IPiece piece) {

    int x = piece.getLocation()._1();
    int y = piece.getLocation()._2();

    board[x][y] = board[x][y].addPiece(piece);
    return new Board(null, null);
  }

  /**
   * Removes a piece from the board.
   * @param location - The location of the piece.
   */
  public Board removePiece(Tuple2<Integer, Integer> location) {

    // TODO: Implement logic.
//    if (getTile(location).isOccupied()) {
//      APiece piece = getPiece(location);
//      if (piece.getColor() == APiece.Color.BLACK) {
//        blackPieces.remove(piece);
//      } else {
//        whitePieces.remove(piece);
//      }
//    }
//
//    board[location.getX()][location.getY()].setPiece(null);
////    m2v.removePiece(location);
    return new Board(null, null);
  }

  /**
   * size getter.
   * @return the size of the board.
   */
  public Tuple2<Integer, Integer> getSize() {
    return size;
  }

  /**
   * Gets a tile at a specific location.
   * @param location - The location of the tile.
   * @return the tile.
   */
  public Option<Tile> getTile(Tuple2<Integer, Integer> location) {
    if (inBounds(location)) {
      return Option.of(board[location._1()][location._2()]);
    }
    return Option.none();
  }

  /**
   * Checks if the given location is in the bounds of the board.
   * @param location - The given location to check.
   * @return whether or not the location is in the bounds of the board.
   */
  public boolean inBounds(Tuple2<Integer, Integer> location) {
    return location._1() < getSize()._1() && location._2() < getSize()._2()
               && location._1() >= 0 && location._2() >= 0;
  }

  /**
   * Returns a new board with the piece moved.
   * @param piece - The piece to move.
   * @param to - The location for the piece to move to.
   * @return An updated board.
   */
  public Board move(IPiece piece, Tuple2<Integer, Integer> to) {

    // TODO: Implement logic.

//    if (turn != piece.getColor()) {
//      return;
//    }
//
//    // Gets the king and checks if it is in check.
//    APiece k;
//    boolean inCheck = false;
//    if (piece.getColor() == APiece.Color.BLACK) {
//      for (APiece p : blackPieces) {
//        if (p instanceof King) {
//          k = p;
//          // Checks for check.
//          for (Tuple location : checked(piece.getColor())) {
//            if (location.equals(k.getLocation())) {
//              inCheck = true;
//              break;
//            }
//          }
//          break;
//        }
//      }
//    }
//
//    // Get list of all moves that prevent check.
//    // If to not in this list return. If no moves in this list then game over.
//
//
//
//    // Check for castle.
//    Tuple kingLocation;
//    // Gets required king location
//    if (piece.getColor() == APiece.Color.BLACK) {
//      kingLocation = new Tuple(0, 4);
//    } else {
//      kingLocation = new Tuple(7, 4);
//    }
//    // Checks for actual castle.
//    if (piece instanceof Rook
//            && ((Rook) piece).canCastle(this, kingLocation)) {
//      boolean isRight;
//      isRight = piece.getLocation().getY() != 0; // Determines which rook.
//
//      APiece king = this.getPiece(kingLocation);
//
//      removePiece(piece.getLocation());
//      removePiece(king.getLocation());
//      if (isRight) {
//        m2v.move(piece.toString(), piece.getLocation(), new Tuple(piece.getLocation().getX(), 5));
//        m2v.move(king.toString(), king.getLocation(), new Tuple(king.getLocation().getX(), 6));
//        piece.move(piece.getLocation(), new Tuple(piece.getLocation().getX(), 5));
//        king.move(king.getLocation(), new Tuple(king.getLocation().getX(), 6));
//      } else {
//        m2v.move(piece.toString(), piece.getLocation(), new Tuple(piece.getLocation().getX(), 3));
//        m2v.move(king.toString(), king.getLocation(), new Tuple(king.getLocation().getX(), 2));
//        piece.move(piece.getLocation(), new Tuple(piece.getLocation().getX(), 3));
//        king.move(king.getLocation(), new Tuple(king.getLocation().getX(), 2));
//      }
//      addPiece(piece);
//      addPiece(king);
//
////      checkMate();
//
//      toggleTurn();
//      return;
//    }
//
//    // Check for capture.
//    if (getPiece(to) != null && getPiece(to).getColor() != piece.getColor()) {
//      removePiece(to);
//      m2v.removePiece(to);
//    }
//
//    // Check for en passant.
//    if (piece instanceof Pawn
//            && Math.abs(piece.getLocation().getX() - to.getX()) == 1
//            && Math.abs(piece.getLocation().getY() - to.getY()) == 1
//            && getPiece(to) == null
//            && ((piece.getColor() == APiece.Color.BLACK && piece.getLocation().getX() == 4) || (piece.getColor() == APiece.Color.WHITE && piece.getLocation().getX() == 3))) {
//      Tuple toRemove = new Tuple(to.getX() - ((Pawn) piece).getForward(), to.getY());
//      removePiece(toRemove);
//      m2v.removePiece(toRemove);
//    }
//
//    m2v.move(piece.toString(), piece.getLocation(), to); // updates in view.
//    removePiece(piece.getLocation());
//    piece.move(piece.getLocation(), to); // Updates the location of the piece.
//    addPiece(piece);
//
//    // Check for pawn at end of board after move.
//    if (piece instanceof Pawn) {
//      if ((piece.getColor() == APiece.Color.BLACK && piece.getLocation().getX() == 7)
//              || (piece.getColor() == APiece.Color.WHITE && piece.getLocation().getX() == 0)) {
//        m2v.displayPawnChange(piece.getLocation());
//      }
//    }
//
////    checkMate();
//    toggleTurn();
    return new Board(null, null);
  }

  public Board changePiece(Tuple2<Integer, Integer> location, String oldName, String newName) {

    // TODO: Implement logic.

//    if (getPiece(location).toString().equals(oldName)) {
//      if (validPieces.keySet().contains(newName)) {
//        removePiece(location);
//        m2v.removePiece(location);
//        m2v.addPiece(newName, location);
//        addPiece(validPieces.get(newName).apply(location));
//      }
//    }
    return new Board(null, null);
  }

  /**
   * Gets a list of places a king cannot move.
   * @param color - The color of the king you are checking for.
   * @return A set of places the king cannot move.
   */
  public Set<Tuple2> checked(IPiece.Color color) {
    List<IPiece> enemyPieces;
    Set<Tuple2> placesCantMove = HashSet.empty();
    if (color == IPiece.Color.BLACK) {
      enemyPieces = getWhitePieces();
    } else {
      enemyPieces = getBlackPieces();
    }

    // TODO: Implement logic.

//    for (IPiece piece : enemyPieces) {
//      placesCantMove.addAll(piece.getCheckMoves(this));
//    }

    return placesCantMove;
  }

  private void toggleTurn() {
    if (turn == APiece.Color.WHITE) {
      turn = APiece.Color.BLACK;
    } else {
      turn = APiece.Color.WHITE;
    }
  }

  /**
   * True if the current color is in check, false otherwise.
   * @return whether or not the current color is in check.
   */
  private boolean check() {
    // TODO
    return false;
  }

  /**
   * True if the current color is in check mate, false otherwise.
   * @return whether or not the current color is in check mate.
   */
  private boolean checkMate() {
    // TODO
    return false;
  }

  /**
   * Gets a piece a specific location.
   * Returns option none if invalid location or there is no piece.
   * @param location - The location of the piece.
   * @return the piece.
   */
  public Option<IPiece> getPiece(Tuple2<Integer, Integer> location) {
    return Match(getTile(location)).of(
        Case($None(), Option.none()),
        Case($Some($()), getTile(location).get().getPiece())
    );
  }
}
