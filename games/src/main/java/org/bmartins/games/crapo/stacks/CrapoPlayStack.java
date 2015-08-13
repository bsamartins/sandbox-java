package org.bmartins.games.crapo.stacks;

import org.bmartins.games.common.cards.DefaultCardStack;
import org.bmartins.games.common.cards.cards.StandardCard;
import org.bmartins.games.crapo.RankComparator;

public class CrapoPlayStack extends DefaultCardStack<StandardCard> {

	@Override
	public boolean canStack(StandardCard card) {
		StandardCard topCard = peek();
		if(topCard == null) {
			return true;
		} else {
			if(topCard.getSuit().isNotSameColor(card.getSuit())
					&& RankComparator.isRightAfter(card.getRank(), topCard.getRank())) {
				return true;
			}
		}
		
		return false;
	}	

}
