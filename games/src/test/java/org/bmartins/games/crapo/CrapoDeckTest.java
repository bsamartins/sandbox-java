package org.bmartins.games.crapo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bmartins.games.common.cards.cards.StandardCard;
import org.junit.Test;

public class CrapoDeckTest {

	private static StandardCard DECK_CARDS[] = { 
		StandardCard.ACE_CLUBS,	
		StandardCard.TWO_CLUBS,
		StandardCard.THREE_CLUBS,
		StandardCard.FOUR_CLUBS,
		StandardCard.FIVE_CLUBS,
		StandardCard.SIX_CLUBS,
		StandardCard.SEVEN_CLUBS,
		StandardCard.EIGHT_CLUBS,
		StandardCard.NINE_CLUBS,
		StandardCard.TEN_CLUBS,			
		StandardCard.JACK_CLUBS,
		StandardCard.QUEEN_CLUBS,
		StandardCard.KING_CLUBS,

		StandardCard.ACE_DIAMONDS,
		StandardCard.TWO_DIAMONDS,
		StandardCard.THREE_DIAMONDS,
		StandardCard.FOUR_DIAMONDS,
		StandardCard.FIVE_DIAMONDS,
		StandardCard.SIX_DIAMONDS,
		StandardCard.SEVEN_DIAMONDS,
		StandardCard.EIGHT_DIAMONDS,
		StandardCard.NINE_DIAMONDS,
		StandardCard.TEN_DIAMONDS,			
		StandardCard.JACK_DIAMONDS,
		StandardCard.QUEEN_DIAMONDS,
		StandardCard.KING_DIAMONDS,

		StandardCard.ACE_HEARTS,
		StandardCard.TWO_HEARTS,
		StandardCard.THREE_HEARTS,
		StandardCard.FOUR_HEARTS,
		StandardCard.FIVE_HEARTS,
		StandardCard.SIX_HEARTS,
		StandardCard.SEVEN_HEARTS,
		StandardCard.EIGHT_HEARTS,
		StandardCard.NINE_HEARTS,
		StandardCard.TEN_HEARTS,			
		StandardCard.JACK_HEARTS,
		StandardCard.QUEEN_HEARTS,
		StandardCard.KING_HEARTS,

		StandardCard.ACE_SPADES,
		StandardCard.TWO_SPADES,
		StandardCard.THREE_SPADES,
		StandardCard.FOUR_SPADES,
		StandardCard.FIVE_SPADES,
		StandardCard.SIX_SPADES,
		StandardCard.SEVEN_SPADES,
		StandardCard.EIGHT_SPADES,
		StandardCard.NINE_SPADES,
		StandardCard.TEN_SPADES,			
		StandardCard.JACK_SPADES,
		StandardCard.QUEEN_SPADES,
		StandardCard.KING_SPADES,
	};
	
	private CrapoDeck testSubject = new CrapoDeck();
	
	@Test
	public void hasAllCards() {
		List<StandardCard> expectedCards = Arrays.asList(DECK_CARDS); 		

		for(StandardCard card: expectedCards) {
			if(!testSubject.getCards().contains(card)) {
				fail("Deck does not contain card: " + card);
			}
		}
	}

	@Test
	public void hasUnexpectedCards() {
		List<StandardCard> expectedCards = Arrays.asList(DECK_CARDS); 		

		for(StandardCard card: testSubject.getCards()) {
			if(!expectedCards.contains(card)) {
				fail("Unexpected card: " + card);
			}
		}
	}
	
	@Test
	public void hasDuplicateCards() {
		Map<StandardCard, Integer> cardCounter = new HashMap<>();
		
		testSubject.getCards().forEach(c -> {
			Integer counter = cardCounter.get(c);
			if(counter == null) {
				counter = 1;
			} else {
				counter += 1;
			}	
			cardCounter.put(c, counter);
		});		
		
		cardCounter.forEach((card, counter) -> {
			assertEquals(card.toString(), 2, counter.intValue());
		});
	}	
}
