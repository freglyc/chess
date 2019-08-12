package model

import util.Option
import model.pieces.IPiece
import model.pieces.King
import model.pieces.Pawn
import model.pieces.Rook

/**
 * Board model.
 */
class Board(val turn: IPiece.Color, val tiles: Map<Int, Map<Int, Tile>>, val m2v: IModel2ViewAdapter, val turnNum: Int) {

    /**
     * Empty board constructor.
     */
    constructor(m2v: IModel2ViewAdapter) : this(
            IPiece.Color.WHITE,
            (1..8).toList().map { row -> row to (1..8).toList().map { col -> col to Tile(Option.none(), Pair(row, col)) }.toMap() }.toMap(),
            m2v, 0
    )

    /**
     * Gets a tile at a specific location
     * @param location - The location of the tile.
     * @return a tile at the given location.
     */
    fun getTile(location: Pair<Int, Int>): Option<Tile> {
        return tiles!!.get(location._1()).fold(Supplier<Option<Tile>> { Option.none() }, { row -> row.get(location._2()) })
    }

    /**
     * Gets a piece at a specific location.
     * @param location - The location of the piece.
     * @return a piece at the given location.
     */
    fun getPiece(location: Pair<Int, Int>): Option<IPiece> {
        return getTile(location).fold(Supplier<Option<IPiece>> { Option.none() }, Function<Tile, Option<IPiece>> { it.getPiece() })
    }

    /**
     * Gets a list of pieces of a specific color.
     * @param color - The color of the pieces to get.
     * @return a list of pieces of a specfic color on the board.
     */
    fun getPieces(color: IPiece.Color): List<IPiece> {
        var pieces = List.empty<IPiece>()
        for (row in tiles!!) {
            for (col in row._2()) {
                if (col._2().getPiece().fold({ false }, { p -> p.getColor() == color })) {
                    pieces = pieces.append(col._2().getPiece().get())
                }
            }
        }
        return pieces
    }

    /**
     * Adds a piece to the board.
     * @param p - the piece to add.
     * @return a new board with the added piece.
     */
    fun addPiece(p: Option<IPiece>): Board {
        return p.fold({ this },
                { x ->
                    Board(turn, tiles!!.map { k1, v1 ->
                        Pair(k1,
                                v1.map { k2, v2 ->
                                    var newTile = v2
                                    if (v2.getLocation() == p.get().location) newTile = newTile.addPiece(p.get())
                                    Pair(k2, newTile)
                                })
                    }, m2v, turnNum)
                })
    }

    /**
     * Removes a piece from the board.
     * @param location - The location of the piece to remove.
     * @return a new board with the piece removed.
     */
    fun removePiece(location: Pair<Int, Int>): Board {
        return this.getPiece(location).fold({ this },
                { x ->
                    Board(turn, tiles!!.map { k1, v1 ->
                        Pair(k1,
                                v1.map { k2, v2 ->
                                    var newTile = v2
                                    if (v2.getLocation() == location) newTile = newTile.removePiece()
                                    Pair(k2, newTile)
                                })
                    }, m2v, turnNum)
                })
    }

    /**
     * Moves a piece from one location to another.
     * @param from - Where the piece is moving from.
     * @param to - Where the piece is moving to.
     * @return a new board with the updated state.
     */
    private fun move(from: Pair<Int, Int>, to: Pair<Int, Int>): Board {
        return this.getPiece(from).fold({ this }, { p -> this.removePiece(from).addPiece(Option.of(p.move(to))) })
    }

    /**
     * Moves a piece while checking for draw or checkmate.
     * @param from - Where the piece is moving from.
     * @param to - Where the piece is moving to.
     * @return a new board with the updated state.
     */
    fun moveLogic(from: Pair<Int, Int>, to: Pair<Int, Int>): Board {

        // No piece at 'from' or 'to' is not a valid move or not player's turn.
        if (getPiece(from).fold({ true }, { p -> !p.getValidMoves(this).contains(to) || p.color != turn })) return this

        val color = getPiece(from).get().color
        val pieces = getPieces(color)

        // Check for draw.
        if (pieces.flatMap { p -> p.getValidMoves(this) }.isEmpty && !kingInCheck(this, color)) {
            // Draw logic
            println("DRAW")
        }
        // Check for mate.
        if (pieces.flatMap { p -> p.getValidMoves(this).map { move -> kingInCheck(moveLogicHelper(p.location, move), color) } }.filter { bool -> bool == false }.isEmpty) {
            // Checkmate logic
            println("CHECKMATE")
        }
        val newBoard = moveLogicHelper(from, to)
        // If the move was valid then accept and toggle turn.
        return if (!kingInCheck(newBoard, color) && newBoard != this) newBoard.toggleTurn() else this
    }

    /**
     * Helper for moveLogic that checks for en passant, castle, etc.
     * @param from - Where the piece is moving from.
     * @param to - Where the piece is moving to.
     * @return a new board with the updated state.
     */
    private fun moveLogicHelper(from: Pair<Int, Int>, to: Pair<Int, Int>): Board {
        // No piece at from or to is not a valid move.
        if (getPiece(from).fold({ true }, { p -> !p.getValidMoves(this).contains(to) || p.color != turn })) return this

        val piece = getPiece(from).get()
        // Check castle.
        if (piece is Rook && piece.canCastle(this, piece.getLocation()).fold({ false }, { move -> move == to })) {
            val king = getPiece(to).get()
            val kingMove = Pair(piece.getLocation()._1(), if (king.location._2() > piece.getLocation()._2()) 1 else 6)
            val rookMove = Pair(piece.getLocation()._1(), if (king.location._2() > piece.getLocation()._2()) 2 else 5)
            return this.removePiece(from).removePiece(to).addPiece(Option.of(piece.move(rookMove))).addPiece(Option.of(king.move(kingMove)))
        }
        // Check en passant.
        if (piece is Pawn && piece.canPassant(this).fold({ false }, { moves -> moves.contains(to) })) {
            val pawnToRemove = Pair(piece.getLocation()._1(), if (piece.getLocation()._2() > to._2()) piece.getLocation()._2() - 1 else piece.getLocation()._2() + 1)
            return this.removePiece(pawnToRemove).move(from, to)
        }
        // Check pawn at end of board.
        val end = if (piece.color == IPiece.Color.BLACK) 7 else 0
        if (piece is Pawn && to._1() == end) {
            m2v!!.displayPawnPromotion(to, piece.getColor())
        }
        // Check capture.
        return if (this.getPiece(to).fold({ false }, { p -> p.color != piece.color })) this.removePiece(to).move(from, to) else this.move(from, to)
        // Normal move.
    }

    /**
     * Determines whether the king of a specific color is in check.
     * @param b - The board the king lies on.
     * @param color - The color of the king.
     * @return whether or not the king is in check.
     */
    private fun kingInCheck(b: Board, color: IPiece.Color): Boolean {
        return (b.getPieces(color).filter { p -> p is King }.get(0) as King).inCheck(b)
    }

    /**
     * Modifies a piece on the board.
     * @param location - The location of the piece.
     * @param newPiece - The new piece.
     * @return a new board with the updated piece.
     */
    fun changePiece(location: Pair<Int, Int>, newPiece: Option<IPiece>): Board {
        return newPiece.fold({ this }, { np -> this.removePiece(location).addPiece(newPiece) })
    }

    /**
     * Changes the turn.
     * @return a new board with the updated turn.
     */
    fun toggleTurn(): Board {
        return if (turn == IPiece.Color.WHITE) Board(IPiece.Color.BLACK, tiles!!, m2v, turnNum++) else Board(IPiece.Color.WHITE, tiles!!, m2v, turnNum++)
    }

    /**
     * Checks that a location is in the board bounds.
     * @param location - A given location.
     * @return true if in bounds, false otherwise.
     */
    fun inBounds(location: Pair<Int, Int>): Boolean {
        return location._1() < size!!._1() && location._2() < size!!._2() && location._1() >= 0 && location._2() >= 0
    }
}
