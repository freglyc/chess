package model;

import model.Pieces.*;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Option;

/**
 * Model.
 */
public class Model {

  /**
   * Model to view adapter used to connect the model to the view.
   */
  private IModel2ViewAdapter m2v;

  /**
   * The board.
   */
  private Board board;

  /**
   * Model constructor
   * @param m2v - Model to view adapter used to connect the model to the view.
   */
  public Model(IModel2ViewAdapter m2v) {
    this.m2v = m2v;
  }

  /**
   * Starts the model.
   */
  public void start() {
    createBoard(new Tuple2<>(8, 8)); // Creates the board.
    // Adds the pieces.
    for (int a = 0; a < board.getSize()._2(); a++) {
      // Black pawns.
      APiece bPawn = new Pawn(IPiece.Color.BLACK, new Tuple2<>(1, a));
      addPiece(bPawn);
      // White pawns.
      APiece wPawn = new Pawn(IPiece.Color.WHITE, new Tuple2<>(6, a));
      addPiece(wPawn);
    }
    // Black Rooks.
    addPiece(new Rook(APiece.Color.BLACK, new Tuple2<>(0, 0)));
    addPiece(new Rook(APiece.Color.BLACK, new Tuple2<>(0, 7)));
    // White Rooks.
    addPiece(new Rook(APiece.Color.WHITE, new Tuple2<>(7, 0)));
    addPiece(new Rook(APiece.Color.WHITE, new Tuple2<>(7, 7)));
    // Black Knights.
    addPiece(new Knight(APiece.Color.BLACK, new Tuple2<>(0, 1)));
    addPiece(new Knight(APiece.Color.BLACK, new Tuple2<>(0, 6)));
    // White Knights.
    addPiece(new Knight(APiece.Color.WHITE, new Tuple2<>(7, 1)));
    addPiece(new Knight(APiece.Color.WHITE, new Tuple2<>(7, 6)));
    // Black Bishops.
    addPiece(new Bishop(APiece.Color.BLACK, new Tuple2<>(0, 2)));
    addPiece(new Bishop(APiece.Color.BLACK, new Tuple2<>(0, 5)));
    // White Bishops.
    addPiece(new Bishop(APiece.Color.WHITE, new Tuple2<>(7, 2)));
    addPiece(new Bishop(APiece.Color.WHITE, new Tuple2<>(7, 5)));
    // Black Queen.
    addPiece(new Queen(APiece.Color.BLACK, new Tuple2<>(0, 3)));
    // White Queen.
    addPiece(new Queen(APiece.Color.WHITE, new Tuple2<>(7, 3)));
    // Black King.
    addPiece(new King(APiece.Color.BLACK, new Tuple2<>(0, 4)));
    // White King.
    addPiece(new King(APiece.Color.WHITE, new Tuple2<>(7, 4)));
  }

  /**
   * Creates the board in the model and view.
   * @param size - The size of the board.
   */
  private void createBoard(Tuple2<Integer, Integer> size) {
    board = new Board(size, m2v); // Creates a base board in the model.
    m2v.createBoard(board.getSize()); // Creates the board in the view.
  }

  /**
   * Adds a piece to the board in the model and view.
   * @param piece - The piece to add.
   */
  private void addPiece(IPiece piece) {
    board = board.addPiece(Option.of(piece)); // Adds to model.
    m2v.addPiece(piece.toString(), piece.getLocation()); // Adds to the view.
  }

  /**
   * Moves a piece from one location to another.
   * @param from - Where the piece is moving from.
   * @param to - Where the piece is moving to.
   */
  public void move(Tuple2<Integer, Integer> from, Tuple2<Integer, Integer> to) {
    board = board.moveLogic(from, to);
    m2v.drawBoard(board);
  }

  /**
   * Gets a piece at a given location.
   * @param location - The location of the piece to get.
   * @return optional piece.
   */
  public Option<IPiece> getPiece(Tuple2<Integer, Integer> location) {
    return board.getPiece(location);
  }

  /**
   * Changes the piece.
   * @param location - The location of the piece.
   * @param newPiece - The new piece.
   */
  public void changePiece(Tuple2<Integer, Integer> location, Option<IPiece> newPiece) {
    board = board.changePiece(location, newPiece);
  }

  /**
   * Gets a list of valid moves for the piece at the given location.
   * @param location - The location of the piece.
   * @return a list of valid moves.
   */
  public List<Tuple2<Integer, Integer>> getValidMoves(Tuple2<Integer, Integer> location) {
    return getPiece(location).fold(List::empty, p -> p.getValidMoves(board));
  }
}
