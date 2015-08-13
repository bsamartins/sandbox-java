package org.bmartins.games.crapo.stacks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.bmartins.games.common.cards.cards.StandardCard;
import org.junit.Test;

public class CrapoFinalStackTest {

	private CrapoFinalStack testSubject = new CrapoFinalStack();
	
	@Test
	public void testCanStackEmptyNonAce() {
		assertFalse(testSubject.canStack(StandardCard.EIGHT_CLUBS));
	}

	@Test
	public void testCanStackEmptyAce() {
		assertTrue(testSubject.canStack(StandardCard.ACE_CLUBS));
	}

	@Test
	public void testCanStackRightAfter() {
		testSubject.stack(StandardCard.ACE_CLUBS);
		assertTrue(testSubject.canStack(StandardCard.TWO_CLUBS));
	}

	@Test
	public void testCanStackAfter() {
		testSubject.stack(StandardCard.ACE_CLUBS);
		assertFalse(testSubject.canStack(StandardCard.THREE_CLUBS));
	}

	@Test
	public void testCanStackBefore() {
		testSubject.stack(StandardCard.ACE_CLUBS);
		testSubject.stack(StandardCard.TWO_CLUBS);
		assertFalse(testSubject.canStack(StandardCard.ACE_CLUBS));
	}

	@Test
	public void testCanStackDifferentRank() {
		testSubject.stack(StandardCard.ACE_CLUBS);
		assertFalse(testSubject.canStack(StandardCard.TWO_DIAMONDS));
	}

}
