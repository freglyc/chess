package model

import model.pieces.*

/**
 * Model.
 */
class Model
/**
 * Model constructor
 * @param m2v - Model to view adapter used to connect the model to the view.
 */
(
        /**
         * Model to view adapter used to connect the model to the view.
         */
        private val m2v: IModel2ViewAdapter) {

    /**
     * The board.
     */
    private var board: Board? = null

    /**
     * Starts the model.
     */
    fun start() {
        createBoard(Pair(8, 8)) // Creates the board.
        // Adds the pieces.
        for (a in 0 until board!!.size._2()) {
            // Black pawns.
            val bPawn = Pawn(IPiece.Color.BLACK, Tuple2(1, a))
            addPiece(bPawn)
            // White pawns.
            val wPawn = Pawn(IPiece.Color.WHITE, Tuple2(6, a))
            addPiece(wPawn)
        }
        // Black Rooks.
        addPiece(Rook(APiece.Color.BLACK, Tuple2(0, 0)))
        addPiece(Rook(APiece.Color.BLACK, Tuple2(0, 7)))
        // White Rooks.
        addPiece(Rook(APiece.Color.WHITE, Tuple2(7, 0)))
        addPiece(Rook(APiece.Color.WHITE, Tuple2(7, 7)))
        // Black Knights.
        addPiece(Knight(APiece.Color.BLACK, Tuple2(0, 1)))
        addPiece(Knight(APiece.Color.BLACK, Tuple2(0, 6)))
        // White Knights.
        addPiece(Knight(APiece.Color.WHITE, Tuple2(7, 1)))
        addPiece(Knight(APiece.Color.WHITE, Tuple2(7, 6)))
        // Black Bishops.
        addPiece(Bishop(APiece.Color.BLACK, Tuple2(0, 2)))
        addPiece(Bishop(APiece.Color.BLACK, Tuple2(0, 5)))
        // White Bishops.
        addPiece(Bishop(APiece.Color.WHITE, Tuple2(7, 2)))
        addPiece(Bishop(APiece.Color.WHITE, Tuple2(7, 5)))
        // Black Queen.
        addPiece(Queen(APiece.Color.BLACK, Tuple2(0, 3)))
        // White Queen.
        addPiece(Queen(APiece.Color.WHITE, Tuple2(7, 3)))
        // Black King.
        addPiece(King(APiece.Color.BLACK, Tuple2(0, 4)))
        // White King.
        addPiece(King(APiece.Color.WHITE, Tuple2(7, 4)))

        m2v.drawBoard(board)
    }

    /**
     * Creates the board in the model and view.
     * @param size - The size of the board.
     */
    private fun createBoard(size: Tuple2<Int, Int>) {
        board = Board(size, m2v) // Creates a base board in the model.
        //    m2v.createBoard(board.getSize()); // Creates the board in the view.
    }

    /**
     * Adds a piece to the board in the model and view.
     * @param piece - The piece to add.
     */
    private fun addPiece(piece: IPiece) {
        board = board!!.addPiece(Option.of(piece)) // Adds to model.
        //    m2v.addPiece(piece.toString(), piece.getLocation()); // Adds to the view.
    }

    /**
     * Moves a piece from one location to another.
     * @param from - Where the piece is moving from.
     * @param to - Where the piece is moving to.
     */
    fun move(from: Tuple2<Int, Int>, to: Tuple2<Int, Int>) {
        board = board!!.moveLogic(from, to)
        m2v.drawBoard(board)
    }

    /**
     * Gets a piece at a given location.
     * @param location - The location of the piece to get.
     * @return optional piece.
     */
    fun getPiece(location: Tuple2<Int, Int>): Option<IPiece> {
        return board!!.getPiece(location)
    }

    /**
     * Changes the piece.
     * @param location - The location of the piece.
     * @param newPiece - The new piece.
     */
    fun changePiece(location: Tuple2<Int, Int>, newPiece: Option<IPiece>) {
        board = board!!.changePiece(location, newPiece)
        m2v.drawBoard(board)
    }

    /**
     * Gets a list of valid moves for the piece at the given location.
     * @param location - The location of the piece.
     * @return a list of valid moves.
     */
    fun getValidMoves(location: Tuple2<Int, Int>): List<Tuple2<Int, Int>> {
        return getPiece(location).fold(Supplier<List<Tuple2<Int, Int>>> { List.empty() }, { p -> p.getValidMoves(board) })
    }
}
