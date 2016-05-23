package BlackJack;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int bet;
		boolean Quit = false;
		Deck deck = new Deck();
		deck.shuffle();
		
		/*
		 * Creates an array of players, depending on the int that is scanned in 
		 */
		ArrayList<Player> players = new ArrayList<Player>();
		System.out.println("Welcome to a game of Blackjack!");
		System.out.println("How many players?");
		int playerNum = scan.nextInt();
		System.out.println("");
		String player = "Player";
		for (int i = 1; i <= playerNum; i++) {
			players.add(new Player(player + i));
		}
		Player dealer = new Player("Dealer");
		
		/*
		 * The game continues until the boolean Quit = false
		 */
		while(!Quit) {
			dealer.addCard(deck.dealCard());
			for(Player curPlayer : players) {
				curPlayer.addCard(deck.dealCard());
			}
		
			dealer.addCard(deck.dealCard());
			for(Player curPlayer : players) {
				curPlayer.addCard(deck.dealCard());
			}
			
			/*
			 * The players makes their bets
			 */
			for(Player curPlayer : players) {
				curPlayer.blackjack = false;
				curPlayer.win = false;
				curPlayer.lose = false;
				curPlayer.bankrupt = false;
				curPlayer.busted = false;
				curPlayer.stay = false;
				curPlayer.tie = false;
				bet = 0;
				System.out.println(curPlayer+" you have "+curPlayer.cash+".");
				System.out.println("Please enter a bet (Enter 0 to end)");
				bet = scan.nextInt(); 
				if(bet < 0 || bet > curPlayer.cash)
					System.out.print("Your bet must be between 0 and "+curPlayer.cash+".");
				if( bet == 0)
					System.exit(0);
				else 
					curPlayer.bet = bet;
			}
		
			dealer.printHand(false);
			System.out.print("\n");
			for(Player curPlayer : players ) {
				curPlayer.printHand(true);
				System.out.println(curPlayer+"'s total sum is " + curPlayer.getHandSum());
				System.out.print("\n");
			}
		
			if(dealer.getHandSum() == 21) {
				dealer.printHand(true);
				System.out.println(" and the total score "+dealer.getHandSum());
				System.out.println("Dealer has Blackjack! You lose \n");
				for(Player curPlayer : players) {
					curPlayer.lose = true;
					curPlayer.end = true;
				}
			}
			
			/*
			 * This section of the game loops until all players stays or are busted
			 */
			boolean roundEnd = false;
			while(!roundEnd) {
				int endPlayer = 0;
				for (int i = 0; i < players.size(); i++) {
					Player curPlayer = players.get(i);
				if(!curPlayer.busted && !curPlayer.stay && !curPlayer.bankrupt && !curPlayer.end) {
					System.out.println(curPlayer+" it is your turn.");
					System.out.println("Your cards are:");
					curPlayer.printHand(true);
					System.out.println("Your total score is "+curPlayer.getHandSum());
					System.out.println("(H)it or (S)tay \n");
					char playerAction;
					do {
						char c = scan.next().charAt(0);
						playerAction = Character.toUpperCase(c);
						if(playerAction != 'H' && playerAction != 'S')
							System.out.println("Please respond H or S");
					}
					while (playerAction != 'H' && playerAction != 'S');
				
					if(playerAction == 'S') {
						curPlayer.stay = true;
					} else { 
						curPlayer.addCard(deck.dealCard());
						System.out.println(curPlayer+" hits.");
						System.out.println("Your hand is now");
						curPlayer.printHand(true);
						System.out.println("Your total score is "+curPlayer.getHandSum());
						if(curPlayer.getHandSum() > 21) {
							curPlayer.busted = true;
							System.out.println("Your total score is over 21. You are busted. \n");
						}
					}
				}
				else endPlayer++;
			}
			if(endPlayer == players.size())
				roundEnd = true;
		}
			/*
			 * If the player gets a blackjack
			 */
			for(Player curPlayer : players) {
				if(curPlayer.getHandSum() == 21) {
					curPlayer.printHand(true);
					System.out.println(" and the total score "+curPlayer.getHandSum());
					System.out.println(curPlayer+" has Blackjack! "+curPlayer+" wins. \n");
					curPlayer.blackjack = true;
					curPlayer.end = true;
				}
				
			}
			
			/* 
			 * The dealers turn
			 */
			dealer.printHand(true);
			while(dealer.getHandSum() < 16) {
				System.out.println("Dealer hits");
				dealer.addCard(deck.dealCard()); 
			}
			dealer.printHand(true);
			System.out.println("Dealer's total score is "+dealer.getHandSum());
			if(dealer.getHandSum() > 21) {
				System.out.println("Dealer busted by going over 21. You win! \n"); 
				for(Player curPlayer : players ) {
					curPlayer.win = true;
				}
			}
			
			/*
			 * Consequences of the game
			 * How the players cash changes
			 */
			for(Player curPlayer : players) {
				if(!curPlayer.bankrupt) {
					if(dealer.getHandSum() == 21 && curPlayer.getHandSum() != 21) {
						curPlayer.cash = curPlayer.cash - curPlayer.bet;
						System.out.println("Dealer had a Blackjack. You lost.");
					}
					else if(dealer.getHandSum() == 21 && curPlayer.getHandSum() == 21) {
						curPlayer.cash = curPlayer.cash + 0;
						System.out.println("Dealer and "+curPlayer+" both had Blackjack. They tied");
					}
					else if(curPlayer.getHandSum() > 21) {
						curPlayer.cash = curPlayer.cash - curPlayer.bet;
						System.out.println(curPlayer+" lost!");
					}
					else if(curPlayer.getHandSum() == 21) {
						curPlayer.cash = curPlayer.cash + (3*curPlayer.bet)/2;
						System.out.println(curPlayer+" had a Blackjack!");
					}
					else if(curPlayer.getHandSum() < 21 && curPlayer.getHandSum() > dealer.getHandSum()) {
						curPlayer.cash = curPlayer.cash + curPlayer.bet;
						System.out.println(curPlayer+" won! ");
					}
					else if(curPlayer.getHandSum() == dealer.getHandSum()) {
						curPlayer.cash = curPlayer.cash + 0;
						System.out.println(curPlayer+" tied with the dealer!");
					}
					else if(dealer.getHandSum() > 21) {
						curPlayer.cash = curPlayer.cash + curPlayer.bet;
						System.out.println(curPlayer+" won!");
					}
					else {
						curPlayer.cash = curPlayer.cash - curPlayer.bet;
						System.out.println(curPlayer+" lost!");
					}
					
				}
				if(curPlayer.cash == 0)
					curPlayer.bankrupt = true;
			}
			
			/*
			 * If one player is bankrupt the game ends
			 * Otherwise the game continues
			 */
			for(Player curPlayer : players) {
				if(curPlayer.bankrupt == true) {				
					System.out.println("");
					System.out.println("One or more players are bankrupt");
					System.out.println("Game over");
					System.exit(0);
				}
			}
					System.out.println("Do you want to continue playing?");
					System.out.println("(Y)es or (N)o \n");
					char gameAction;
					do {
						char g = scan.next().charAt(0);
						gameAction = Character.toUpperCase(g);
						if(gameAction != 'Y' && gameAction != 'N')
							System.out.println("Please respond Y or N");
					}
					while (gameAction != 'Y' && gameAction != 'N');

					if(gameAction == 'Y') {
						Quit = false;
						for(Player curPlayer : players) {
							curPlayer.emptyHand();
							curPlayer.stay = false;
							curPlayer.blackjack = false;
							curPlayer.busted = false;
							curPlayer.win = false;
							curPlayer.lose = false;
							curPlayer.tie = false;
							curPlayer.end = false;
						}
							dealer.emptyHand();
							deck = new Deck();
							deck.shuffle();
					}	
					else {
						System.out.println("Game over");
						Quit = true;
					}
			}
		scan.close();
	}
}
