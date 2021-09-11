package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.*;
import com.google.common.collect.ImmutableList;
import com.chess.engine.board.Tile;

public class Queen extends Piece {
	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = { -9, -8, -7, -1, 1, 7, 8, 9 };

	public Queen(final Alliance pieceAllience,final int piecePosition) {
		super(PieceType.QUEEN,piecePosition, pieceAllience);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<Move> calculateLegalMove(Board board) {

		final List<Move> legalMoves = new ArrayList<>();

		for (final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
			int candidateDestinationCoordinate = this.piecePosition;
			while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				if (isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)
						|| isEighthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset))
					candidateDestinationCoordinate += candidateCoordinateOffset;
				if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
					final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
					if (!candidateDestinationTile.isTileOccupied()) {
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
					} else {
						final Piece pieceAtDestination = candidateDestinationTile.getPiece();
						final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
						if (this.pieceAllience != pieceAlliance) {
							legalMoves.add(
									new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
						}
						break;
					}
				}
			}
		}
		return ImmutableList.copyOf(legalMoves);
	}
	@Override
	public Queen movePiece(Move move) {
		return new Queen(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}
	@Override
	public String toString() {
		return PieceType.QUEEN.toString();
	}
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1||candidateOffset == -9 || candidateOffset == 7);
	}

	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 ||candidateOffset == 1|| candidateOffset == 9);
	}

}