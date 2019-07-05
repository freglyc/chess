package view;

import io.vavr.Tuple2;

import javax.swing.*;
import java.awt.*;

/**
 * Tile used in the view.
 */
public class Tile extends JPanel {

  /**
   * The piece the tile holds. Null if not occupied.
   */
  private Piece piece;
  /**
   * The location of the tile.
   */
  private Tuple2<Integer, Integer> location;
  /**
   * The color of the tile.
   */
  private Color color;
  /**
   * Whether or not this tile is currently selected by a user.
   */
  private boolean isSelected = false;

  /**
   * Tile Constructor.
   * @param location - The location of the tile.
   * @param color - The color of the tile.
   */
  Tile (Tuple2<Integer, Integer> location, Color color) {
    this.location = location;
    this.color = color;
    this.piece = null;
    this.setBackground(color);
  }

  /**
   * isOccupied getter.
   * @return whether or not this tile is occupied
   */
  boolean isOccupied() {
    return piece != null;
  }

  /**
   * location getter.
   * @return the location of the tile.
   */
  Tuple2<Integer, Integer> getTileLocation() {
    return location;
  }

  /**
   * Adds a piece to the tile.
   * The tile must not have a piece to add a new one.
   * @param piece - The piece to add.
   */
  void addPiece(Piece piece) {
    if (this.piece == null && piece != null) {
//      System.out.println("PIECE ADDED TO TILE");
      this.piece = piece;

      this.add(piece);
//      if (this.getComponent(0).equals(piece)) {
//        System.out.println("ADDED SUCCESSFULLY");
//      }

      piece.setVisible(true);
      piece.repaint();
    }
  }

  /**
   * Removes a piece from the tile.
   */
  void removePiece() {
    if (piece != null) {
      piece = null;
      this.remove(0);
    }
  }

  /**
   * piece getter.
   * @return the piece on the tile.
   */
  public Piece getPiece() {
    return piece;
  }

  /**
   * Toggles whether or not this tile is selected by the user.
   */
  void toggleSelected() {
    if (isSelected) {
      this.setBackground(color);
    } else {
      this.setBackground(new Color(179, 179, 179));
    }
    isSelected = !isSelected;
  }
}
