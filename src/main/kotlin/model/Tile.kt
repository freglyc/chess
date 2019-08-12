package model

import util.Option
import model.pieces.IPiece

/**
 * Tile in the model.
 */
class Tile(val piece: Option<IPiece>, val location: Pair<Int, Int>) {

    /**
     * Empty tile constructor.
     */
    constructor(loc: Pair<Int, Int>) : this(Option.none(), loc)

    /**
     * True if occupied. False otherwise.
     * @return whether or not the tile is occupied.
     */
    val isOccupied: Boolean = piece.isSome()

    /**
     * Adds a piece to the tile.
     * @param p - The piece to add.
     * @return a new tile with the updated piece if it could be added.
     */
    fun addPiece(p: IPiece): Tile {
        return if (piece.isNone() || piece.get().getColor() != p.getColor()) {
            Tile(Option.some(p), location)
        } else Tile(piece, location)
    }

    /**
     * Removes a piece from the tile.
     * @return a new tile with the piece removed.
     */
    fun removePiece(): Tile {
        return Tile(Option.none(), location)
    }
}
