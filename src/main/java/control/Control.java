package control;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import model.Board;
import model.IModel2ViewAdapter;
import model.Model;
import model.Pieces.IPiece;
import model.Pieces.Pieces;
import view.IView2ModelAdapter;
import view.View;

import java.awt.*;

/**
 * Controller for this chess application.
 */
public class Control {

  /**
   * The model.
   */
  private Model model;

  /**
   * The view.
   */
  private View view;

  /**
   * Control constructor
   */
  private Control() {

    model = new Model(new IModel2ViewAdapter() {

      @Override
      public void createBoard(Tuple2<Integer, Integer> size) {
        view.createBoard(size);
      }

      @Override
      public void drawBoard(Board board) { view.drawBoard(board); }

      @Override
      public void addPiece(String piece, Tuple2<Integer, Integer> location) {
        view.addPiece(piece, location);
      }

      @Override
      public void removePiece(Tuple2<Integer, Integer> location) {
        view.removePiece(location);
      }

      @Override
      public void displayPawnPromotion(Tuple2<Integer, Integer> location, IPiece.Color color) {
        view.displayPawnPromotion(location, color.equals(IPiece.Color.WHITE) ? "WHITE" : "BLACK");
      }
    });

    view = new View(new IView2ModelAdapter() {

      @Override
      public List<Tuple2<Integer, Integer>> getValidMoves(Tuple2<Integer, Integer> location) {
        return model.getValidMoves(location);
      }

      @Override
      public void move(Tuple2<Integer, Integer> from, Tuple2<Integer, Integer> to) {
        model.move(from, to);
      }

      @Override
      public void changePiece(Tuple2<Integer, Integer> location, String newPiece) {
        model.changePiece(location, Pieces.getInstance().createPiece(newPiece, location));
      }
    });
  }

  /**
   * Starts the chess application.
   */
  private void start() {
    model.start();
    view.start();
  }

  /**
   * Runs the application.
   * @param args - application arguments.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(() -> {
      try {
        (new Control()).start();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }
}
