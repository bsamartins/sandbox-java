package org.bmartins.games.crapo.stacks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.bmartins.games.common.cards.cards.StandardCard;
import org.junit.Test;

public class CrapoStackTest {

	private CrapoStack testSubject = new CrapoStack();
	
	@Test
	public void testCanStackEmptyNonAce() {
		assertTrue(testSubject.canStack(StandardCard.EIGHT_CLUBS));
	}

	@Test
	public void testCanStackEmptyAce() {
		assertTrue(testSubject.canStack(StandardCard.ACE_CLUBS));
	}

	@Test
	public void testCanStackSameSuitAfter() {
		testSubject.stack(StandardCard.TWO_CLUBS);
		assertTrue(testSubject.canStack(StandardCard.THREE_CLUBS));
	}

	@Test
	public void testCanStackSameSuitBefore() {
		testSubject.stack(StandardCard.THREE_CLUBS);
		assertTrue(testSubject.canStack(StandardCard.TWO_CLUBS));
	}

	@Test
	public void testCanStackDifferentSuit() {
		testSubject.stack(StandardCard.THREE_CLUBS);
		assertFalse(testSubject.canStack(StandardCard.TWO_DIAMONDS));
	}

	@Test
	public void testCanStackSame() {
		testSubject.stack(StandardCard.TWO_DIAMONDS);
		assertFalse(testSubject.canStack(StandardCard.TWO_DIAMONDS));
	}
}
