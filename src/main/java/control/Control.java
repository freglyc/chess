package control;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import model.IModel2ViewAdapter;
import model.Model;
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
   * Control Constructor
   */
  private Control() {
    model = new Model(new IModel2ViewAdapter() {

      @Override
      public void createBoard(Tuple2<Integer, Integer> size) {
        view.createBoard(size);
      }

      @Override
      public void addPiece(String piece, Tuple2<Integer, Integer> location) {
        view.addPiece(piece, location);
      }

      @Override
      public void removePiece(Tuple2<Integer, Integer> location) {
        view.removePiece(location);
      }

      @Override
      public void move(String name, Tuple2<Integer, Integer> from, Tuple2<Integer, Integer> to) {
        view.move(name, from, to);
      }

      @Override
      public void displayPawnChange(Tuple2<Integer, Integer> location) {
        view.displayPawnChange(location);
      }
    });

    view = new View(new IView2ModelAdapter() {

      @Override
      public List<Tuple2<Integer, Integer>> getValidMoves(Tuple2<Integer, Integer> location) {
        return model.getBoard().getPiece(location).get().getValidMoves(model.getBoard());
      }

      @Override
      public void move(Tuple2<Integer, Integer> from, Tuple2<Integer, Integer> to) {
        if (model.getBoard().getPiece(from) != null) {
          model.getBoard().move(model.getBoard().getPiece(from).get(), to);
        }
      }

      @Override
      public void changePiece(Tuple2<Integer, Integer> location, String oldPiece, String newPiece) {
        model.getBoard().changePiece(location, oldPiece, newPiece);
      }
    });
  }

  /**
   * Starts the game.
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
