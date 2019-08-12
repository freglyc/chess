package model.pieces

import model.Board

/**
 * Piece interface representing a chess piece.
 */
interface IPiece {

    /**
     * The choice of colors for each piece.
     */
    enum class Color { WHITE, BLACK }

    /**
     * Returns the color of the piece.
     * @return the color.
     */
    fun getColor(): Color

    /**
     * Returns a new piece with the updated location.
     * NOTE: Does not check if the to location is a valid place to move.
     * @param to - The location to move to.
     * @return a piece with the new location.
     */
    fun move(to: Pair<Int, Int>): IPiece

    /**
     * Determines whether or not the potentialMove is valid.
     * @param board - The board the piece sits on.
     * @param potentialMove - The potential move the piece would make.
     * @return true if a valid move, false otherwise.
     */
    fun getValidMove(board: Board, potentialMove: Pair<Int, Int>): Boolean

    /**
     * Gets a list of valid moves.
     * @param board - The board the piece sits on.
     * @return a list of valid moves.
     */
    fun getValidMoves(board: Board): List<Pair<Int, Int>>

    /**
     * Gets a list of locations the opposing king cannot move.
     * @param board - The board the piece sits on.
     * @return a list of locations.
     */
    fun getCheckMoves(board: Board): List<Pair<Int, Int>>
}
