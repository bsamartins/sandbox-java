package org.bmartins.games.crapo;

import static org.junit.Assert.*;

import org.bmartins.games.common.cards.suits.FrenchSuit;
import org.junit.Test;

public class CrapoGameTest {

	private CrapoGame game = new CrapoGame();
	
	@Test
	public void start() {
		game.start();
		
		assertEquals(12, game.getCrapoStack(Player.PLAYER_1).size());
		assertEquals(12, game.getCrapoStack(Player.PLAYER_2).size());

		assertEquals(1, game.getCrapoTurnedStack(Player.PLAYER_1).size());
		assertEquals(1, game.getCrapoTurnedStack(Player.PLAYER_2).size());

		assertEquals(35, game.getMainStack(Player.PLAYER_1).size());
		assertEquals(35, game.getMainStack(Player.PLAYER_2).size());

		assertEquals(0, game.getMainTurnedStack(Player.PLAYER_1).size());
		assertEquals(0, game.getMainTurnedStack(Player.PLAYER_2).size());

		assertEquals(0, game.getMainTurnedStack(Player.PLAYER_1).size());
		assertEquals(0, game.getMainTurnedStack(Player.PLAYER_2).size());

		assertEquals(0, game.getStackingHouse(1, FrenchSuit.CLUBS).count());
		assertEquals(0, game.getStackingHouse(2, FrenchSuit.CLUBS).count());
		
		assertEquals(0, game.getStackingHouse(1, FrenchSuit.DIAMONDS).count());
		assertEquals(0, game.getStackingHouse(2, FrenchSuit.DIAMONDS).count());

		assertEquals(0, game.getStackingHouse(1, FrenchSuit.HEARTS).count());
		assertEquals(0, game.getStackingHouse(2, FrenchSuit.HEARTS).count());

		assertEquals(0, game.getStackingHouse(1, FrenchSuit.SPADES).count());
		assertEquals(0, game.getStackingHouse(2, FrenchSuit.SPADES).count());

		for(int house = 0; house < 8; house++) {
			assertEquals("House " + house, 1, game.getPlayingHouse(house).count());
		}

	}

}
