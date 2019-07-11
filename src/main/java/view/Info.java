package view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;

public class Info extends JPanel {

  /**
   * Displays the current player's turn.
   */
  private JTextPane turnTxt;
  /**
   * Displays the played moves.
   */
  private JTextArea movesTxt;
  /**
   * Holds movesText.
   */
  private JScrollPane movesPane;
  /**
   * Pawn promotion panel.
   */
  private JPanel pawnPromoPane;
  /**
   * Restart button to reset the game.
   */
  private JButton restartBtn;
  /**
   * Restart pane that holds the restart button.
   */
  private JPanel restartPane;

  public Info() {
    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    initTurnTxt();
    initMovePane();
    initPawnPromoPane();
    initRestartPane();
    repaint();
  }

  /**
   * Sets up the turn text and adds it to the info panel.
   */
  private void initTurnTxt() {
    turnTxt = new JTextPane();
//    turnTxt.setContentType("text/html");
    turnTxt.setFont(new Font("Helvetica", Font.BOLD, 24));
    turnTxt.setText("\nwhite turn");
//    turnTxt.setText("<html><b>Turn white</b></html>");
    turnTxt.setPreferredSize(new Dimension(200, 50));
    // Can no longer edit or copy
    turnTxt.setEditable(false);
    turnTxt.getCaret().deinstall(turnTxt);
    // Centers the text.
    SimpleAttributeSet center = new SimpleAttributeSet();
    StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
    turnTxt.getStyledDocument().setParagraphAttributes(0,  turnTxt.getStyledDocument().getLength(), center, false);
    this.add(turnTxt);
  }

  private void initMovePane() {
    String test = "awkjfbnawkjbfkwajbfkjqwbflwajbwfjbawlfjbawjbflajwbfwajlbfjlwabflajwbflajbwlfjblawdfwafwafwafawfawfawfawfawfawfawfkrnhlaegjka b,jgn.aneafnawlfawjlb wlajfbaw, fawjkfbawjbfawjfwa";
    movesTxt = new JTextArea(test);
    movesTxt.setLineWrap(true);
    movesTxt.setEditable(false);
    movesPane = new JScrollPane(movesTxt, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    movesPane.setBorder(null);
    movesPane.setPreferredSize(new Dimension(200, 300));
//    movesPane
    this.add(movesPane);
  }

  private void initPawnPromoPane() {
    pawnPromoPane = new JPanel();
    pawnPromoPane.setPreferredSize(new Dimension(200, 200));
    this.add(pawnPromoPane);
  }

  private void initRestartPane() {
    restartBtn = new JButton("Restart");
    restartPane = new JPanel();
    restartPane.add(restartBtn);
    restartPane.setPreferredSize(new Dimension(200, 100));
    this.add(restartPane);
  }

//  public void addMove(String move) {
//    textArea.append(move);
//  }
//
//  public void displayMate(String color) {
//    textArea.append(color + " wins");
//  }
}
