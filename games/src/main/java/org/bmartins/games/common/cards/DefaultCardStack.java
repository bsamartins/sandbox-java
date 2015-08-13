package org.bmartins.games.common.cards;

import java.util.Stack;

import org.bmartins.games.common.cards.cards.Card;

public class DefaultCardStack<C extends Card<?,?>> implements CardStack<C> {

	private Stack<C> stack = new Stack<>();
	
	@Override
	public C pop() {
		return stack.pop();
	}

	protected void push(C card) {
		stack.push(card);
	}
	
	@Override
	public void stack(C card) {
		if(canStack(card)) {
			push(card);
		}
	}

	@Override
	public C peek() {
		return stack.peek();
	}

	@Override
	public int count() {
		return stack.size();
	}
	
	@Override
	public boolean canStack(C card) {
		return true;
	}

	@Override
	public Stack<C> getStack() {
		return stack;
	}
}
