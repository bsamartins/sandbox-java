package org.bmartins.games.common.cards.suits;

public enum FrenchSuit implements Suit {
	HEARTS(FrenchSuitColor.RED),
	CLUBS(FrenchSuitColor.BLACK),
	DIAMONDS(FrenchSuitColor.RED),
	SPADES(FrenchSuitColor.BLACK);
	
	private FrenchSuitColor color;
	
	private FrenchSuit(FrenchSuitColor color) {
		this.color = color;
	}
	
	public FrenchSuitColor getColor() {
		return this.color;
	}
	
	public boolean isSameColor(FrenchSuit suit) {
		return getColor() == suit.getColor();
	}

	public boolean isNotSameColor(FrenchSuit suit) {
		return getColor() != suit.getColor();
	}

}
