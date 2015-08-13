package org.bmartins.games.common.cards.cards;

import org.bmartins.games.common.cards.ranks.Rank;
import org.bmartins.games.common.cards.suits.Suit;


public interface Card<S extends Suit,R extends Rank> {
	
	S getSuit();
	R getRank();
	
}
