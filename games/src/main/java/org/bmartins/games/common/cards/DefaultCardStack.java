package org.bmartins.games.common.cards;

import java.util.Stack;

import org.bmartins.games.common.cards.cards.Card;

public class DefaultCardStack<C extends Card<?,?>> implements CardStack<C> {

	private Stack<C> stack = new Stack<>();
	
	@Override
	public C pop() {
		return stack.pop();
	}

	@Override
	public void push(C card) {
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
		if(!stack.isEmpty()) {
			return stack.peek();			
		}
		return null;
	}

	@Override
	public int count() {
		return stack.size();
	}
	
	@Override
	public boolean canStack(C card) {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Stack<C> getStackView() {
		return (Stack<C>) stack.clone();
	}

	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}
}
