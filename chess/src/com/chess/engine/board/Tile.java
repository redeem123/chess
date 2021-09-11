package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile {

	protected final int tileCoordinate;
	private static final Map<Integer, EmptyTile> EMPTY_TILE_CACHE = createAllPossibleEmptyTiles();
	
	public int getTileCoordinate() {
		return this.tileCoordinate;
	}
	private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
		final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
		for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
			emptyTileMap.put(i, new EmptyTile(i));
		}
//		return Collections.unmodifiableMap(new LinkedHashMap<Integer, EmptyTile>(emptyTileMap));
		return ImmutableMap.copyOf(emptyTileMap);
	}
	public static Tile createTile(final int tileCoordinate, final Piece piece) {
		return piece!=null? new OccupiedTile(tileCoordinate,piece):EMPTY_TILE_CACHE.get(tileCoordinate);
	}
	public Tile(int tileCoordinate) {
		this.tileCoordinate = tileCoordinate;
	}

	public abstract boolean isTileOccupied();

	public abstract Piece getPiece();

	public static final class EmptyTile extends Tile {
		public EmptyTile(final int tileCoordinate) {
			super(tileCoordinate);

		}

		@Override
		public String toString() {
			return "-";
		}

		@Override
		public boolean isTileOccupied() {
			return false;
		}

		@Override
		public Piece getPiece() {
			return null;
		}
	}

	public static final class OccupiedTile extends Tile {
		private final Piece pieceOnTile;

		public OccupiedTile(int tileCoordinate, final Piece pieceOnTile) {
			super(tileCoordinate);
			this.pieceOnTile = pieceOnTile;
		}

		@Override
		public String toString() {
			return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase()
					: getPiece().toString();
		}

		@Override
		public boolean isTileOccupied() {
			return true;
		}

		@Override
		public Piece getPiece() {

			return this.pieceOnTile;
		}

		
	}

	

}