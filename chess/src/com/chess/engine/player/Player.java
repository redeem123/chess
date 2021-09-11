package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.*;

public abstract class Player {
	protected final Board board;
	protected final King playerKing;
	protected final Collection<Move> legalMoves;
	private final boolean isInCheck;

	public King getPlayerKing() {
		return playerKing;
	}

	public Collection<Move> getLegalMoves() {
		return legalMoves;
	}

	public Player(Board board, Collection<Move> legalMoves, final Collection<Move> opponentMoves) {
		this.board = board;
		this.playerKing = establishKing();
		this.legalMoves = ImmutableList
				.copyOf(Iterables.concat(legalMoves, calculateKingCastles(legalMoves, opponentMoves)));
		this.isInCheck = !Player.calculateAttackOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();

	}

	public static Collection<Move> calculateAttackOnTile(int piecePosition, Collection<Move> moves) {
		final List<Move> attackMoves = new ArrayList<>();
		for (final Move move : moves) {
			// nếu trùng vị trí thì tấn công
			if (piecePosition == move.getDestinationCoordinate()) {
				attackMoves.add(move);
			}
		}
		return ImmutableList.copyOf(attackMoves);
	}

	private King establishKing() {
		for (final Piece piece : getActivePieces()) {
			if (((Piece) piece).getPieceType().isKing()) {
				return (King) piece;
			}
		}
		throw new RuntimeException("should not reach here ! not a valid board ");
	}

	public boolean isMoveLegal(final Move move) {
		return this.legalMoves.contains(move);
	}

	public boolean isInCheck() {
		return this.isInCheck;
	}

	public boolean isInCheckMate() {
		return this.isInCheck && !hasEscapeMove();
	}

	public boolean isInStaleMate() {
		return !this.isInCheck && !hasEscapeMove();
	}

	// escape move for King
	protected boolean hasEscapeMove() {
		// duyệt qua legalmoves
		for (Move move : this.legalMoves) {
			final MoveTransition transition = makeMove(move);
			if (transition.getMoveStatus().isDone()) {
				// nếu trạng thái của nước đi có thì trả về true
				return true;
			}
		}
		return false;
	}

	public boolean isCastled() {
		return false;
	}

	public MoveTransition makeMove(final Move move) {
		if (!isMoveLegal(move)) {
			return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
		}
		final Board transitionBoard = move.execute();
		final Collection<Move> kingAttacks = Player.calculateAttackOnTile(
				transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
				transitionBoard.currentPlayer().getLegalMoves());
		if (!kingAttacks.isEmpty()) {
			return new MoveTransition(this.board, move, MoveStatus.LEAVE_PLAYER_IN_CHECK);
		}
		return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
	}

	public abstract Collection<Piece> getActivePieces();

	public abstract Alliance getAlliance();

	public abstract Player getOpponent();

	protected abstract Collection<Move> calculateKingCastles(Collection<Move> playLegals,
			Collection<Move> opponentLegals);
}