package org.bmartins.games.crapo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.bmartins.games.common.cards.Game;
import org.bmartins.games.common.cards.cards.StandardCard;
import org.bmartins.games.common.cards.suits.FrenchSuit;
import org.bmartins.games.crapo.stacks.CrapoFinalStack;
import org.bmartins.games.crapo.stacks.CrapoPlayStack;
import org.bmartins.games.crapo.stacks.CrapoStack;
import org.bmartins.games.crapo.stacks.CrapoTrashStack;

public class CrapoGame implements Game {
	
	private static final int NUM_CRAPO_CARDS = 13;
	private static final int NUM_INITIAL_CARDS = 4;
	
	private Map<FrenchSuit, CrapoFinalStack> stackingHouse1 = new HashMap<>();
	private Map<FrenchSuit, CrapoFinalStack> stackingHouse2 = new HashMap<>();
	
	private List<CrapoPlayStack> playingHouse = new ArrayList<>();
	private Map<Player, Stack<StandardCard>> crapoStack = new HashMap<>();
	private Map<Player, CrapoStack> crapoTurnedStack = new HashMap<>();
	private Map<Player, Stack<StandardCard>> mainStack = new HashMap<>();
	private Map<Player, CrapoTrashStack> mainTurnedStack = new HashMap<>();
	
	private Player currentPlayer;
	
	public CrapoGame() {
		this.crapoStack.put(Player.PLAYER_1, new Stack<>());
		this.crapoStack.put(Player.PLAYER_2, new Stack<>());

		this.mainStack.put(Player.PLAYER_1, new Stack<>());
		this.mainStack.put(Player.PLAYER_2, new Stack<>());

		this.crapoTurnedStack.put(Player.PLAYER_1, new CrapoStack());
		this.crapoTurnedStack.put(Player.PLAYER_2, new CrapoStack());

		this.mainTurnedStack.put(Player.PLAYER_1, new CrapoTrashStack());
		this.mainTurnedStack.put(Player.PLAYER_2, new CrapoTrashStack());

		for(int i = 0; i < 8; i++) {
			this.playingHouse.add(new CrapoPlayStack());	
		}		
	}
	
	@Override
	public void start() {
		CrapoDeck crapoDeck = new CrapoDeck();
		Collections.shuffle(crapoDeck.getCards());
		List<StandardCard> shuffledCards = crapoDeck.getCards();
		
		List<StandardCard> player1Game = shuffledCards.subList(0, shuffledCards.size()/2);				
		List<StandardCard> player2Game = shuffledCards.subList(shuffledCards.size()/2, shuffledCards.size());
		
		initPlayerGame(Player.PLAYER_1, player1Game);
		initPlayerGame(Player.PLAYER_2, player2Game);
	}
	
	private void initPlayerGame(Player player, List<StandardCard> game) {
		// remove crapoCards
		crapoStack.get(player).addAll(game.subList(game.size()-NUM_CRAPO_CARDS, game.size()));		
		List<StandardCard> mainStack = game.subList(0, game.size()-NUM_CRAPO_CARDS);	
		
		// Remove the initial 4 cards
		List<StandardCard> tempStack = mainStack.subList(mainStack.size() - NUM_INITIAL_CARDS, mainStack.size());
		for(int i = 0; i < tempStack.size(); i++) {
			StandardCard card = tempStack.get(i);
			int houseIndex = (player == Player.PLAYER_1 ? 0 : (int)Math.pow(2, 2)) + i;
			playingHouse.get(houseIndex).stack(card);
		}
		
		// set main stack
		mainStack = mainStack.subList(0, mainStack.size() - NUM_INITIAL_CARDS);
		this.mainStack.get(player).addAll(mainStack);
		
		determineStartingPlayer();
	}
	
	public Stack<StandardCard> getCrapoStack(Player player) {
		return this.crapoStack.get(player);
	}
	
	public Stack<StandardCard> getMainStack(Player player) {
		return this.mainStack.get(player);
	}
	
	public CrapoPlayStack getPlayingHouse(int house) {
		return this.playingHouse.get(house);
	}

	Stack<StandardCard> getCrapoTurnedStack(Player player) {
		return crapoTurnedStack.get(player).getStack();
	}

	Stack<StandardCard> getMainTurnedStack(Player player) {
		return mainTurnedStack.get(player).getStack();
	}
	
	private void determineStartingPlayer() {
		RankComparator rankComparator = new RankComparator();
		currentPlayer = Player.PLAYER_1;
		
		boolean foundStartingPlayer = false;
		
		while(!foundStartingPlayer 
				&& !crapoStack.get(Player.PLAYER_1).isEmpty()
				&& !crapoStack.get(Player.PLAYER_2).isEmpty()) {
						
			StandardCard player1Card = crapoStack.get(Player.PLAYER_1).pop();
			crapoTurnedStack.get(Player.PLAYER_1).stack(player1Card);
			
			StandardCard player2Card = crapoStack.get(Player.PLAYER_2).pop();
			crapoTurnedStack.get(Player.PLAYER_2).stack(player2Card);
			
			int result = rankComparator.compare(player1Card.getRank(), player2Card.getRank());
			if(result > 0) {
				foundStartingPlayer = true;
				currentPlayer = Player.PLAYER_1;
			} else if (result < 0) {
				foundStartingPlayer = true;
				currentPlayer = Player.PLAYER_2;				
			}
		}		
	}

	CrapoFinalStack getStackingHouse(int n, FrenchSuit suit) {
		if(n == 1) {
			return stackingHouse1.get(suit);
		} else if (n == 2) {
			return stackingHouse2.get(suit);
		}
		return null;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}	
}
