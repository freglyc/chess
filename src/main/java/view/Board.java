package view;

import io.vavr.Tuple2;
import io.vavr.collection.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Board used in the view.
 */
public class Board extends JPanel implements MouseListener {

  /**
   * Mapping from String names to Images.
   */
  private Map<String, Image> Images = new HashMap<>();
  /**
   * The array of tiles that makes up the board.
   */
  private Tile board[][];
  /**
   * The size of the board.
   */
  private Tuple2<Integer, Integer> size;
  /**
   * The view to model adapter.
   */
  private IView2ModelAdapter v2m;
  /**
   * Determines whether or not a piece is selected.
   */
  private boolean pieceSelected;
  /**
   * If a piece is selected, the tile that piece sits on.
   */
  private Tile selectedTile;
  /**
   * List of valid locations to move from for the selected piece.
   */
  private List<Tuple2<Integer, Integer>> valid;

  /**
   * Board constructor.
   * @param v2m - The view to model adapter.
   * @param size - the size of the board.
   */
  public Board(IView2ModelAdapter v2m, Tuple2<Integer, Integer> size) {
    this.v2m = v2m;
    this.size = size;
    this.pieceSelected = false;
    this.selectedTile = null;
    this.valid = List.empty();

    this.addMouseListener(this);

    this.setLayout(new GridLayout(size._1(), size._2()));
    this.setBackground(new Color(236, 237, 209)); // temp

    initBoard();
    readResources();
  }

  /**
   * Builds the board.
   */
  private void initBoard() {
    // Builds the board.
    board = new Tile[size._1()][size._2()];
    for (int i = 0; i < size._1(); i++) { // Row
      for (int j = 0; j < size._2(); j++) { // Col
        Tile tile;
        if (i % 2 == 0) {
          if (j % 2 == 0) {
            tile = new Tile(new Tuple2<>(i, j), new Color(236, 237, 209));
            tile.setSize(75, 75);
          } else {
            tile = new Tile(new Tuple2<>(i, j), new Color(149, 192, 104));
            tile.setSize(75, 75);
          }
        } else {
          if (j % 2 == 0) {
            tile = new Tile(new Tuple2<>(i, j), new Color(149, 192, 104));
            tile.setSize(75, 75);
          } else {
            tile = new Tile(new Tuple2<>(i, j), new Color(236, 237, 209));
            tile.setSize(75, 75);
          }
        }
        board[i][j] = tile;
        this.add(board[i][j]);
      }
    }
  }

  /**
   * Reads in the required resources.
   */
  private void readResources() {
    try {
      // Read in images.
      Images.put("BLACK_PAWN", ImageIO.read(new File("src/resources/bpawn.png")));
      Images.put("WHITE_PAWN", ImageIO.read(new File("src/resources/wpawn.png")));
      Images.put("BLACK_ROOK", ImageIO.read(new File("src/resources/brook.png")));
      Images.put("WHITE_ROOK", ImageIO.read(new File("src/resources/wrook.png")));
      Images.put("BLACK_KNIGHT", ImageIO.read(new File("src/resources/bknight.png")));
      Images.put("WHITE_KNIGHT", ImageIO.read(new File("src/resources/wknight.png")));
      Images.put("BLACK_BISHOP", ImageIO.read(new File("src/resources/bbishop.png")));
      Images.put("WHITE_BISHOP", ImageIO.read(new File("src/resources/wbishop.png")));
      Images.put("BLACK_QUEEN", ImageIO.read(new File("src/resources/bqueen.png")));
      Images.put("WHITE_QUEEN", ImageIO.read(new File("src/resources/wqueen.png")));
      Images.put("BLACK_KING", ImageIO.read(new File("src/resources/bking.png")));
      Images.put("WHITE_KING", ImageIO.read(new File("src/resources/wking.png")));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds a piece to the board.
   * @param name - The name of the piece.
   * @param location - The location of the piece.
   */
  void addPiece(String name, Tuple2<Integer, Integer> location) {
    if (Images.containsKey(name)) {
      Piece piece = new Piece(name, location, new ImageIcon(Images.get(name)));
      board[piece.getPieceLocation()._1()][piece.getPieceLocation()._2()].addPiece(piece);
      piece.setSize(75, 75);
      repaint();
    }
  }

  /**
   * Adds a piece to the board.
   * @param piece - The piece to add.
   */
  private void addPiece(Piece piece) {
    board[piece.getPieceLocation()._1()][piece.getPieceLocation()._2()].addPiece(piece);
    repaint();
  }

  /**
   * Removes a piece from the board.
   * If there is nothing there then does nothing.
   * @param location - the location of where to remove the piece from.
   */
  void removePiece(Tuple2<Integer, Integer> location) {
      board[location._1()][location._2()].removePiece();
      repaint();
  }

//  /**
//   * Moves a piece from its current location to a new location in the model.
//   * @param piece - The piece being moved.
//   * @param to - The location being moved to.
//   */
//  private void move(Piece piece, Tuple to) {
//    // Moves in the model.
//
//
//    // Moves in the view.
////    removePiece(piece.getPieceLocation()); // removes the piece from it's location.
////    piece.move(to); // updates the piece's location.
////    addPiece(piece); // adds the piece back in a different location.
//  }

  public void move(String name, Tuple2<Integer, Integer> from, Tuple2<Integer, Integer> to) {
    if (board[from._1()][from._2()].getPiece().getName().equals(name)) {
      Piece piece = board[from._1()][from._2()].getPiece();
      removePiece(piece.getPieceLocation()); // removes the piece from it's location.
      piece.move(to); // updates the piece's location.
      addPiece(piece); // adds the piece back in a different location.
      repaint();
    }
  }


  public String getPiece(Tuple2<Integer, Integer> location) {
    return board[location._1()][location._2()].getPiece().getName();
  }

  @Override
  public void mouseClicked(MouseEvent e) {

    Tile tile = (Tile) this.getComponentAt(new Point(e.getX(), e.getY()));

    // Selection handling.
    if (!pieceSelected && tile.isOccupied()) {
      // If a piece is not currently selected and the tile is occupied then get valid moves.
      selectedTile = tile;
      valid = v2m.getValidMoves(tile.getTileLocation()); // Gets valid moves.
      // Updates the valid tiles.
      for (Tuple2<Integer, Integer> loc : valid) {
        board[loc._1()][loc._2()].toggleSelected();
      }
      pieceSelected = true;
      repaint();
    } else if (valid.contains(tile.getTileLocation())) {
      // If a piece is currently selected and a valid location was clicked then move the piece.
      v2m.move(selectedTile.getPiece().getPieceLocation(), tile.getTileLocation());
      // Updates the valid tiles.
      for (Tuple2<Integer, Integer> loc : valid) {
        board[loc._1()][loc._2()].toggleSelected();
      }
      valid = List.empty();
      pieceSelected = false;
      repaint();
    } else {
      // Updates the valid tiles.
      for (Tuple2<Integer, Integer> loc : valid) {
        board[loc._1()][loc._2()].toggleSelected();
      }
      valid = List.empty();
      pieceSelected = false;
      repaint();
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }
}
