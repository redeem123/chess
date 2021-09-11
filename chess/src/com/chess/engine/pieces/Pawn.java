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

public class Pawn extends Piece {
	private final static int[] CANDIDATE_MOVE_COORDINATE = { 8, 16, 7, 9 };

	public Pawn(final Alliance pieceAllience, final int piecePosition) {
		super(PieceType.PAWN, piecePosition, pieceAllience);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<Move> calculateLegalMove(final Board board) {
		final List<Move> legalMove = new ArrayList<>();
		for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
			final int candidateDestinationCoordinate = this.piecePosition
					+ (this.getPieceAlliance().getDirection() * currentCandidateOffset);
			if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				continue;
			}
			if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
				legalMove.add(new MajorMove(board, this, candidateDestinationCoordinate));
			} else if (currentCandidateOffset == 16 && this.isFirstMove()
					&& (BoardUtils.SEVENTH_RANK[this.piecePosition] && this.getPieceAlliance().isBlack())
					|| (BoardUtils.SECOND_RANK[this.piecePosition] && (this.getPieceAlliance()).isWhite())) {
				final int behindCandidateDestinationCoordinate = this.piecePosition
						+ (this.pieceAllience.getDirection() * 8);
				if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied()
						&& !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					legalMove.add(new MajorMove(board, this, candidateDestinationCoordinate));
				}
			} else if (currentCandidateOffset == 7
					&& !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAllience.isWhite())
							|| (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAllience.isBlack()))) {
				if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if (this.pieceAllience != pieceOnCandidate.getPieceAlliance()) {
						// TODO more here
						legalMove.add(new MajorMove(board, this, candidateDestinationCoordinate));
					}
				}
			} else if (currentCandidateOffset == 9
					&& !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAllience.isWhite())
							|| (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAllience.isBlack()))) {
				if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if (this.pieceAllience != pieceOnCandidate.getPieceAlliance()) {
						// TODO more here
						legalMove.add(new MajorMove(board, this, candidateDestinationCoordinate));
					}
				}
			}
		}
		return ImmutableList.copyOf(legalMove);
	}

	@Override
	public Pawn movePiece(Move move) {
		return new Pawn(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}

	@Override
	public String toString() {
		return PieceType.PAWN.toString();
	}
}
