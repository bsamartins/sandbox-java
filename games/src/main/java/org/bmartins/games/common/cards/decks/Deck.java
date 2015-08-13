package org.bmartins.games.common.cards.decks;

import java.util.List;

import org.bmartins.games.common.cards.cards.Card;

public interface Deck<C extends Card<?,?>> {
	List<C> getCards();
}
