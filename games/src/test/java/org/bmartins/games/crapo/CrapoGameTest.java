package org.bmartins.games.crapo;

import static org.junit.Assert.*;

import org.bmartins.games.common.cards.suits.FrenchSuit;
import org.bmartins.games.crapo.CrapoGame.StackingPile;
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

		assertEquals(0, game.getStackingHouse(FrenchSuit.CLUBS, StackingPile.PILE_1).count());
		assertEquals(0, game.getStackingHouse(FrenchSuit.CLUBS, StackingPile.PILE_2).count());
		
		assertEquals(0, game.getStackingHouse(FrenchSuit.DIAMONDS, StackingPile.PILE_1).count());
		assertEquals(0, game.getStackingHouse(FrenchSuit.DIAMONDS, StackingPile.PILE_2).count());

		assertEquals(0, game.getStackingHouse(FrenchSuit.HEARTS, StackingPile.PILE_1).count());
		assertEquals(0, game.getStackingHouse(FrenchSuit.HEARTS, StackingPile.PILE_2).count());

		assertEquals(0, game.getStackingHouse(FrenchSuit.SPADES, StackingPile.PILE_1).count());
		assertEquals(0, game.getStackingHouse(FrenchSuit.SPADES, StackingPile.PILE_2).count());

		for(int house = 0; house < 8; house++) {
			assertEquals("House " + house, 1, game.getPlayingHouse(house).count());
		}

	}

}
