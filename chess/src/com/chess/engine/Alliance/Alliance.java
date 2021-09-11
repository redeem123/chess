package com.chess.engine.Alliance;

import java.util.Collection;

import com.chess.engine.pieces.Piece;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;

public enum Alliance {
	WHITE() {
		@Override
		public int getDirection() {
			return -1;
		}

		@Override
		public boolean isBlack() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isWhite() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
			// TODO Auto-generated method stub
			return whitePlayer;
		}

	

	},
	BLACK {
		@Override
		public int getDirection() {
			return 1;
		}

		@Override
		public boolean isBlack() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isWhite() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
			// TODO Auto-generated method stub
			return blackPlayer;
		}

		
	};

	public abstract int getDirection();
	public abstract boolean isBlack();
	public abstract boolean isWhite();
	public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);
}
