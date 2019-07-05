package model.Pieces;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;

import java.util.function.Function;

/**
 * Contains all pieces that can be added to the board.
 */
public class Pieces {

  /**
   * Singleton object.
   */
  private static final Pieces p = new Pieces();

  /**
   * The mapping of names to piece creation functions.
   */
  private final Map<String, Function<Tuple2<Integer, Integer>, IPiece>> temp = HashMap.empty();
  private final Map<String, Function<Tuple2<Integer, Integer>, IPiece>> pieces =
      temp.put("WHITE_KNIGHT", (Tuple2<Integer, Integer> location) -> new Knight(APiece.Color.WHITE, location))
          .put("BLACK_KNIGHT", (Tuple2<Integer, Integer> location) -> new Knight(APiece.Color.BLACK, location))
          .put("WHITE_ROOK", (Tuple2<Integer, Integer> location) -> new Rook(APiece.Color.WHITE, location))
          .put("BLACK_ROOK", (Tuple2<Integer, Integer> location) -> new Rook(APiece.Color.BLACK, location))
          .put("WHITE_BISHOP", (Tuple2<Integer, Integer> location) -> new Bishop(APiece.Color.WHITE, location))
          .put("BLACK_BISHOP", (Tuple2<Integer, Integer> location) -> new Bishop(APiece.Color.BLACK, location))
          .put("WHITE_QUEEN", (Tuple2<Integer, Integer> location) -> new Queen(APiece.Color.WHITE, location))
          .put("BLACK_QUEEN", (Tuple2<Integer, Integer> location) -> new Queen(APiece.Color.BLACK, location))
          .put("WHITE_KING", (Tuple2<Integer, Integer> location) -> new King(APiece.Color.WHITE, location))
          .put("BLACK_KING", (Tuple2<Integer, Integer> location) -> new King(APiece.Color.BLACK, location))
          .put("WHITE_PAWN", (Tuple2<Integer, Integer> location) -> new Pawn(APiece.Color.WHITE, location))
          .put("BLACK_PAWN", (Tuple2<Integer, Integer> location) -> new Pawn(APiece.Color.BLACK, location));

  /**
   * Gets the only instance of Pieces.
   * @return the singleton instance.
   */
  public static Pieces getInstance() { return p; }

  /**
   * Creates a new piece.
   * @param name - The name of the piece to create.
   * @param location - The location of the new piece.
   * @return a newly created piece.
   */
  public Option<IPiece> createPiece(String name, Tuple2<Integer, Integer> location) {
    if (pieces.get(name).isEmpty()) return Option.none();
    return Option.of(pieces.get(name).get().apply(location));
  }
}
