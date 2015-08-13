package org.bmartins.games.common.cards;

import java.util.Stack;

import org.bmartins.games.common.cards.cards.Card;

public interface CardStack<C extends Card<?,?>> {

	C pop();
	C peek();
	void push(C card);
	boolean isEmpty();
	
	int count();
	
	void stack(C card);
	boolean canStack(C card);
	
	Stack<C> getStackView();
	
}
