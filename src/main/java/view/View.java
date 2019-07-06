package view;

import io.vavr.Tuple2;
import model.Pieces.IPiece;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * View.
 */
public class View extends JFrame {

  /**
   * View to model adapter used to connect the view to the model.
   */
  private IView2ModelAdapter v2m;
  /**
   * Main content pane for the view.
   */
  private JPanel contentPane;
  /**
   * Panel that holds the chess board.
   */
  private Board boardPanel;

  /**
   * View constructor.
   * @param v2m - View to model adapter that connects the view to the model.
   */
  public View(IView2ModelAdapter v2m) {
    super("Chess");
    this.v2m = v2m;
    initGUI();
  }

  /**
   * Initializes the GUI.
   */
  private void initGUI() {
    // Basic setup.
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 600, 600);
    // Main content pane.
    contentPane = new JPanel();
    contentPane.setBackground(Color.LIGHT_GRAY);
    contentPane.setToolTipText("Main Content Pane");
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    setContentPane(contentPane);
  }

  /**
   * Creates the board.
   * @param size - The size of the board.
   */
  public void createBoard(Tuple2<Integer, Integer> size) {
    boardPanel = new Board(v2m, size);
    contentPane.add(boardPanel);
  }

  /**
   * Draws the board.
   * @param board - The board from the model.
   */
  public void drawBoard(model.Board board) {
    contentPane.remove(boardPanel);
    boardPanel = new Board(v2m, board.getSize(), board.getPieces(IPiece.Color.WHITE).appendAll(board.getPieces(IPiece.Color.BLACK)));
    contentPane.add(boardPanel);
    contentPane.revalidate();
    boardPanel.repaint();
  }

  /**
   * Adds a piece to the board.
   * @param name - The name of the piece.
   * @param location - The location of the piece on the board.
   */
  public void addPiece(String name, Tuple2<Integer, Integer> location) {
    boardPanel.addPiece(name, location);
  }

  /**
   * Removes a piece from the board.
   * @param location - The location of the piece on the board.
   */
  public void removePiece(Tuple2<Integer, Integer> location) {
    boardPanel.removePiece(location);
  }

  /**
   * Moves a piece on the board.
   * @param name - The name of the piece.
   * @param from - Where the piece is moving from.
   * @param to - Where the piece is moving to.
   */
  public void move(String name, Tuple2<Integer, Integer> from, Tuple2<Integer, Integer> to) {
    boardPanel.move(name, from, to);
  }

  /**
   * Displays pawn change options.
   * @param location - The location of the piece.
   * @param color - The color of the pawn.
   */
  public void displayPawnPromotion(Tuple2<Integer, Integer> location, String color) {
    JFrame frame = new JFrame();
    JPanel pnl = new JPanel();
    JButton queenBtn = new JButton("QUEEN");
    queenBtn.addActionListener((arg) -> {
      v2m.changePiece(location, color + "_QUEEN");
      frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    });
    JButton bishopBtn = new JButton("BISHOP");
    bishopBtn.addActionListener((arg) -> {
      v2m.changePiece(location, color + "_BISHOP");
      frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    });;
    JButton knightBtn = new JButton("KNIGHT");
    knightBtn.addActionListener((arg) -> {
      v2m.changePiece(location, color + "_KNIGHT");
      frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    });
    JButton rookBtn = new JButton("ROOK");
    rookBtn.addActionListener((arg) -> {
      v2m.changePiece(location, color + "_ROOK");
      frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    });
    pnl.add(queenBtn);
    pnl.add(bishopBtn);
    pnl.add(knightBtn);
    pnl.add(rookBtn);
    frame.setContentPane(pnl);
    frame.setLayout(new GridLayout());
    frame.setTitle("Choose Piece");
    frame.setVisible(true);
    frame.setSize(300, 100);
  }

  /**
   * Starts the view.
   */
  public void start() {
    setVisible(true);
  }
}
