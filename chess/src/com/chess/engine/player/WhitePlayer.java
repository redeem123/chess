package com.chess.engine.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

import com.chess.engine.Alliance.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.KingSideCastleMove;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

public class WhitePlayer extends Player {

	public WhitePlayer(final Board board, final Collection<Move> whiteStandardLegalMoves,
			final Collection<Move> blackStandardLegalMoves) {
		super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		// TODO Auto-generated method stub
		return this.board.getWhitePieces();
	}

	@Override
	public Alliance getAlliance() {
		// TODO Auto-generated method stub
		return Alliance.WHITE;
	}

	@Override
	public Player getOpponent() {
		return this.board.blackPlayer();
	}

	@Override
	public Collection<Move> calculateKingCastles(final Collection<Move> playLegals,
			final Collection<Move> opponentLegals) {

		final List<Move> kingCastles = new ArrayList<>();
		// nếu ko bị chiếu và là nước đầu thì đc nhập thành
		if (this.playerKing.isFirstMove() && !this.isInCheck()) {
			// white king side castle move
			if (!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()) {
				final Tile rookTile = this.board.getTile(63);

				if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					if (Player.calculateAttackOnTile(61, opponentLegals).isEmpty()
							&& Player.calculateAttackOnTile(62, opponentLegals).isEmpty()
							&& rookTile.getPiece().getPieceType().isRook()) {
						kingCastles.add(new Move.KingSideCastleMove(board, this.playerKing, 62,
								(Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 61));
					}

				}
			}

			if (this.board.getTile(59).isTileOccupied() && !this.board.getTile(58).isTileOccupied()
					&& this.board.getTile(57).isTileOccupied()) {
				final Tile rookTile = this.board.getTile(56);
				if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()
						&& Player.calculateAttackOnTile(58, opponentLegals).isEmpty()
						&& Player.calculateAttackOnTile(59, opponentLegals).isEmpty()
						&& rookTile.getPiece().getPieceType().isRook()) {
					kingCastles.add(new Move.QueenSideCastleMove(board, this.playerKing, 58, (Rook) rookTile.getPiece(),
							rookTile.getTileCoordinate(), 59));
				}
			}

		}
		return ImmutableList.copyOf(kingCastles);
	}

}
