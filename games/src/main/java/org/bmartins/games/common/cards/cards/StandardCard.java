package org.bmartins.games.common.cards.cards;

import org.bmartins.games.common.cards.ranks.StandardRanks;
import org.bmartins.games.common.cards.suits.FrenchSuit;

public enum StandardCard implements Card<FrenchSuit, StandardRanks> {

	ACE_SPADES(FrenchSuit.SPADES, StandardRanks.ACE),
	TWO_SPADES(FrenchSuit.SPADES, StandardRanks.TWO),
	THREE_SPADES(FrenchSuit.SPADES, StandardRanks.THREE),
	FOUR_SPADES(FrenchSuit.SPADES, StandardRanks.FOUR),
	FIVE_SPADES(FrenchSuit.SPADES, StandardRanks.FIVE),
	SIX_SPADES(FrenchSuit.SPADES, StandardRanks.SIX),
	SEVEN_SPADES(FrenchSuit.SPADES, StandardRanks.SEVEN),
	EIGHT_SPADES(FrenchSuit.SPADES, StandardRanks.EIGHT),
	NINE_SPADES(FrenchSuit.SPADES, StandardRanks.NINE),
	TEN_SPADES(FrenchSuit.SPADES, StandardRanks.TEN),
	JACK_SPADES(FrenchSuit.SPADES, StandardRanks.JACK),
	QUEEN_SPADES(FrenchSuit.SPADES, StandardRanks.QUEEN),
	KING_SPADES(FrenchSuit.SPADES, StandardRanks.KING),
	
	ACE_CLUBS(FrenchSuit.CLUBS, StandardRanks.ACE),
	TWO_CLUBS(FrenchSuit.CLUBS, StandardRanks.TWO),
	THREE_CLUBS(FrenchSuit.CLUBS, StandardRanks.THREE),
	FOUR_CLUBS(FrenchSuit.CLUBS, StandardRanks.FOUR),
	FIVE_CLUBS(FrenchSuit.CLUBS, StandardRanks.FIVE),
	SIX_CLUBS(FrenchSuit.CLUBS, StandardRanks.SIX),
	SEVEN_CLUBS(FrenchSuit.CLUBS, StandardRanks.SEVEN),
	EIGHT_CLUBS(FrenchSuit.CLUBS, StandardRanks.EIGHT),
	NINE_CLUBS(FrenchSuit.CLUBS, StandardRanks.NINE),
	TEN_CLUBS(FrenchSuit.CLUBS, StandardRanks.TEN),
	JACK_CLUBS(FrenchSuit.CLUBS, StandardRanks.JACK),
	QUEEN_CLUBS(FrenchSuit.CLUBS, StandardRanks.QUEEN),
	KING_CLUBS(FrenchSuit.CLUBS, StandardRanks.KING),

	ACE_DIAMONDS(FrenchSuit.DIAMONDS, StandardRanks.ACE),
	TWO_DIAMONDS(FrenchSuit.DIAMONDS, StandardRanks.TWO),
	THREE_DIAMONDS(FrenchSuit.DIAMONDS, StandardRanks.THREE),
	FOUR_DIAMONDS(FrenchSuit.DIAMONDS, StandardRanks.FOUR),
	FIVE_DIAMONDS(FrenchSuit.DIAMONDS, StandardRanks.FIVE),
	SIX_DIAMONDS(FrenchSuit.DIAMONDS, StandardRanks.SIX),
	SEVEN_DIAMONDS(FrenchSuit.DIAMONDS, StandardRanks.SEVEN),
	EIGHT_DIAMONDS(FrenchSuit.DIAMONDS, StandardRanks.EIGHT),
	NINE_DIAMONDS(FrenchSuit.DIAMONDS, StandardRanks.NINE),
	TEN_DIAMONDS(FrenchSuit.DIAMONDS, StandardRanks.TEN),
	JACK_DIAMONDS(FrenchSuit.DIAMONDS, StandardRanks.JACK),
	QUEEN_DIAMONDS(FrenchSuit.DIAMONDS, StandardRanks.QUEEN),
	KING_DIAMONDS(FrenchSuit.DIAMONDS, StandardRanks.KING),

	ACE_HEARTS(FrenchSuit.HEARTS, StandardRanks.ACE),
	TWO_HEARTS(FrenchSuit.HEARTS, StandardRanks.TWO),
	THREE_HEARTS(FrenchSuit.HEARTS, StandardRanks.THREE),
	FOUR_HEARTS(FrenchSuit.HEARTS, StandardRanks.FOUR),
	FIVE_HEARTS(FrenchSuit.HEARTS, StandardRanks.FIVE),
	SIX_HEARTS(FrenchSuit.HEARTS, StandardRanks.SIX),
	SEVEN_HEARTS(FrenchSuit.HEARTS, StandardRanks.SEVEN),
	EIGHT_HEARTS(FrenchSuit.HEARTS, StandardRanks.EIGHT),
	NINE_HEARTS(FrenchSuit.HEARTS, StandardRanks.NINE),
	TEN_HEARTS(FrenchSuit.HEARTS, StandardRanks.TEN),
	JACK_HEARTS(FrenchSuit.HEARTS, StandardRanks.JACK),
	QUEEN_HEARTS(FrenchSuit.HEARTS, StandardRanks.QUEEN),
	KING_HEARTS(FrenchSuit.HEARTS, StandardRanks.KING),
	
	JOKER(null, StandardRanks.JOKER),
	;
	
	private FrenchSuit suit;
	private StandardRanks rank;

	private StandardCard(FrenchSuit suit, StandardRanks rank) {
		this.suit = suit;
		this.rank = rank;
	}
	
	@Override
	public FrenchSuit getSuit() {
		return suit;
	}

	@Override
	public StandardRanks getRank() {
		return rank;
	}
}
