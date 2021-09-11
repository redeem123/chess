package com.chess.engine.pieces;

import java.util.Collection;
import java.util.List;
import com.chess.engine.board.Move;
import com.chess.engine.board.Board;
import com.chess.engine.Alliance.Alliance;

public abstract class Piece {
	protected final PieceType pieceType;
	protected final int piecePosition;
	protected final Alliance pieceAllience;
	protected final boolean isFirstMove;
	private final int cachedHashCode;

	public Piece(PieceType pieceType, int piecePosition, Alliance pieceAllience) {
		this.pieceType = pieceType;
		this.piecePosition = piecePosition;
		this.pieceAllience = pieceAllience;
		this.isFirstMove =false;
		this.cachedHashCode = computeHashCode();
	}

	private int computeHashCode() {
		int result = pieceType.hashCode();
		result = 31 * result + pieceAllience.hashCode();
		result = 31 * result + piecePosition;
		result = 31 * result + (isFirstMove ? 1 : 0);
		return result;
	}

	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Piece)) {
			return false;
		}
		final Piece otherPiece = (Piece) other;
		return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType()
				&& pieceAllience == otherPiece.getPieceAlliance() && isFirstMove == otherPiece.isFirstMove();
	}

	@Override
	public int hashCode() {

		return this.cachedHashCode;
	}

	public int getPiecePosition() {
		return this.piecePosition;
	}

	public Alliance getPieceAlliance() {
		return this.pieceAllience;
	}

	public boolean isFirstMove() {
		return this.isFirstMove;
	}

	public PieceType getPieceType() {
		return this.pieceType;
	}

	public abstract Collection<Move> calculateLegalMove(final Board board);

	public abstract Piece movePiece(Move move);

	public enum PieceType {
		PAWN("P") {
			@Override
			public boolean isKing() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRook() {
				// TODO Auto-generated method stub
				return false;
			}
		},
		KNIGHT("N") {
			@Override
			public boolean isKing() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRook() {
				// TODO Auto-generated method stub
				return false;
			}
		},
		BISHOP("B") {
			@Override
			public boolean isKing() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRook() {
				// TODO Auto-generated method stub
				return false;
			}
		},
		ROOK("R") {
			@Override
			public boolean isKing() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRook() {
				// TODO Auto-generated method stub
				return true;
			}
		},
		QUEEN("Q") {
			@Override
			public boolean isKing() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRook() {
				// TODO Auto-generated method stub
				return false;
			}
		},
		KING("K") {
			@Override
			public boolean isKing() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public boolean isRook() {
				// TODO Auto-generated method stub
				return false;
			}
		};

		private String pieceName;

		PieceType(final String pieceName) {
			this.pieceName = pieceName;
		}

		@Override
		public String toString() {
			return this.pieceName;
		}

		public abstract boolean isKing();

		public abstract boolean isRook();
	}

}
