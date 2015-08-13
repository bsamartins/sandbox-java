package org.bmartins.games.crapo.stacks;

import org.bmartins.games.common.cards.DefaultCardStack;
import org.bmartins.games.common.cards.cards.StandardCard;
import org.bmartins.games.common.cards.ranks.StandardRanks;
import org.bmartins.games.crapo.RankComparator;

public class CrapoFinalStack extends DefaultCardStack<StandardCard> {

	@Override
	public boolean canStack(StandardCard card) {
		StandardCard topCard = peek();
		if(topCard == null) {
			if(card.getRank() == StandardRanks.ACE) {
				return true;
			}
		} else {
			if(topCard.getSuit() == card.getSuit()
					&& RankComparator.isRightAfter(card.getRank(), topCard.getRank())) {
				return true;
			}
		}
		
		return false;
	}	
}
