package view;

import io.vavr.Tuple2;

import javax.swing.*;

/**
 * Piece used in the view.
 */
public class Piece extends JLabel {

  /**
   * The location of the piece.
   */
  private Tuple2<Integer, Integer> location;

  /**
   * The name of the piece.
   */
  private String name;

  /**
   * Piece constructor.
   * @param name - The name of the piece.
   * @param location - The location of the piece.
   * @param icon - The visual image icon.
   */
  public Piece(String name, Tuple2<Integer, Integer> location, ImageIcon icon) {
    super(icon);
    this.name = name;
    this.location = location;
  }

  /**
   * name getter.
   * @return the name.
   */
  public String getName() {
    return name;
  }

  /**
   * location getter.
   * @return the location.
   */
  Tuple2<Integer, Integer> getPieceLocation() {
    return location;
  }

  /**
   * Moves the piece.
   * @param to - where the piece is moved to.
   */
  void move(Tuple2<Integer, Integer> to) {
      location = to;
  }
}
