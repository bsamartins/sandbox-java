package org.bmartins.games.crapo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.bmartins.games.common.cards.ranks.Rank;
import org.bmartins.games.common.cards.ranks.StandardRanks;

public class RankComparator implements Comparator<Rank>{
	
	private static final List<Rank> RANK_ORDER = Arrays.asList(
		StandardRanks.ACE,
		StandardRanks.ACE,
		StandardRanks.TWO,
		StandardRanks.THREE,
		StandardRanks.FOUR,
		StandardRanks.FIVE,
		StandardRanks.SIX,
		StandardRanks.SEVEN,
		StandardRanks.EIGHT,
		StandardRanks.NINE,
		StandardRanks.TEN,
		StandardRanks.JACK,
		StandardRanks.QUEEN,
		StandardRanks.KING				
	);
	
	@Override
	public int compare(Rank r1, Rank r2) {
		int i1 = getIndexOfRank(r1);
		int i2 = getIndexOfRank(r2);

		return i1 - i2;
	}

	private static int getIndexOfRank(Rank r) {
		int i1 = RANK_ORDER.indexOf(r);
		if(i1 < 0) {
			throw new IllegalArgumentException(r + " not found");
		}
		return i1;
	}

	public static boolean isRightAfter(Rank r1, Rank r2) {
		int i1 = getIndexOfRank(r1);
		int i2 = getIndexOfRank(r2);
		
		return (i1 - i2) == 1;
	}
	
	public static boolean isRightBefore(Rank r1, Rank r2) {
		int i1 = getIndexOfRank(r1);
		int i2 = getIndexOfRank(r2);
		
		return (i2 - i1) == 1;
	}
}
