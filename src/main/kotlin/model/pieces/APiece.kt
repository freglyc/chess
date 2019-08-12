package model.pieces

import model.Board

/**
 * Piece constructor.
 * @param color - The color of the piece. Either BLACK or WHITE.
 * @param location - The location of the piece.
 * @param timesMoved = The number of times this piece has moved.
 */
abstract class APiece(val color: IPiece.Color, val location: Pair<Int, Int>, private val timesMoved: Int) : IPiece {

    override fun getValidMove(board: Board, potentialMove: Pair<Int, Int>): Boolean = this.getValidMoves(board).contains(potentialMove)

    /**
     * Gets a list of valid moves.
     * @param location - The location of the piece.
     * @param iter - The way to iterate over the board.
     * @param board - The board the piece lies on.
     * @return a list of valid moves.
     */
    private fun validTterator(location: Pair<Int, Int>, iter: Pair<Int, Int>, board: Board): List<Pair<Int, Int>> {
        val currentLocation = Pair(location.first + iter.first, location.second + iter.second)
        if (!board.inBounds(location) || !board.inBounds(currentLocation) || board.getTile(currentLocation).get().isOccupied() && board.getPiece(currentLocation).get().getColor() == color)
            return emptyList()
        else if (board.getTile(currentLocation).get().isOccupied() && board.getPiece(currentLocation).get().getColor() != color)
            return listOf(currentLocation)
        return validTterator(currentLocation, iter, board).plus(currentLocation)
    }

    /**
     * Gets valid moves in the straight directions.
     * @param board - The board the piece lies on.
     * @return a list of valid straight moves.
     */
    internal fun getStraightMoves(board: Board): List<Pair<Int, Int>> {
        return emptyList<Pair<Int, Int>>().plus(validTterator(location, Pair(0, 1), board))
                .plus(validTterator(location, Pair(0, -1), board))
                .plus(validTterator(location, Pair(1, 0), board))
                .plus(validTterator(location, Pair(-1, 0), board))
    }

    /**
     * Gets valid moves in the diagonal directions.
     * @param board - The board the piece lies on.
     * @return a list of valid diagonal moves.
     */
    internal fun getDiagonalMoves(board: Board): List<Pair<Int, Int>> {
        return emptyList<Pair<Int, Int>>().plus(validTterator(location, Pair(1, 1), board))
                .plus(validTterator(location, Pair(1, -1), board))
                .plus(validTterator(location, Pair(-1, 1), board))
                .plus(validTterator(location, Pair(-1, -1), board))
    }
}
