package org.bmartins.games.crapo;

import org.bmartins.games.common.cards.cards.StandardCard;
import org.bmartins.games.common.cards.decks.CombinableDeck;
import org.bmartins.games.common.cards.decks.StandardDeck;

public class CrapoDeck extends CombinableDeck<StandardCard> {
		
	public CrapoDeck() {
		StandardDeck standardDeck = new StandardDeck();
		
		addDeck(standardDeck);
		addDeck(standardDeck);
	}
}
