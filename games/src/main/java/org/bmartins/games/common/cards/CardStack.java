package org.bmartins.games.common.cards;

import java.util.Stack;

import org.bmartins.games.common.cards.cards.Card;

public interface CardStack<C extends Card<?,?>> {

	C pop();
	void stack(C card);
	C peek();
	boolean canStack(C card);
	int count();
	Stack<C> getStack();
	
}
