package org.bmartins.games.common.cards.decks;

import java.util.ArrayList;
import java.util.List;

import org.bmartins.games.common.cards.cards.Card;

public class CombinableDeck<C extends Card<?,?>> implements Deck<C> {

	public List<C> cards = new ArrayList<>();
	
	public CombinableDeck(List<Deck<C>> decks) {
		addDecks(decks);
	}

	protected CombinableDeck() {}
	
	@Override
	public List<C> getCards() {
		return cards;
	}
	
	protected void addDecks(List<Deck<C>> decks) {
		for(Deck<C> deck: decks) {
			addDeck(deck);
		}
	}

	protected void addDeck(Deck<C> deck) {
		cards.addAll(deck.getCards());
	}	
}
