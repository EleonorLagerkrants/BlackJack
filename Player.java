package BlackJack;

public class Player {
	
	private String name;
	/*
	 * 10 as a Card limit since it is very improbable
	 * Busted,stay,bankrupt is different conditions for the Players 
	 */
	private Card[] hand = new Card[10];
	private int numCards;
	public int cash = 200;
	public int bet;
	public boolean busted;
	public boolean stay;
	public boolean bankrupt;
	public boolean blackjack;
	public boolean win;
	public boolean lose;
	public boolean tie;
	public boolean end;
	
	/* 
	 * The constructor creates a player with a name and an empty hand
	 */
	public Player(String aName) {
		this.name = aName;
		this.emptyHand();
	}
	
	public void emptyHand() {

		for (int c = 0; c < 10; c++) {
			this.hand[c] = null;
		}
		this.numCards = 0;
	}
	
	public boolean addCard(Card aCard) {
		if(this.numCards == 10) {
			System.err.println("Hand already has 10 cards.");
			System.exit(1);
		}
		
		this.hand[this.numCards] = aCard;
		this.numCards++;
		return (this.getHandSum() <= 21);
	}
	
	/*
	 * Calculates the sum of the hand. An Ace can be both 1 and 11.
	 * If the total sum is more than 21 an ace counts as 1.
	 * The method also calculates Jacks,Queens and Kings as 10
	 */
	public int getHandSum() {
		int handSum = 0;
		int cardNum;
		int numAces = 0;
		
		for( int c = 0; c < this.numCards; c++) {
			cardNum = this.hand[c].getValue();
			if(cardNum == 1) {
				numAces++;
				handSum += 11;
			} else if (cardNum > 10) {
				handSum += 10;
			} else {
				handSum += cardNum;
			}
		}
		while (handSum > 21 && numAces > 0) {
			handSum -= 10;
			numAces--;
		}
		return handSum;
	}

	public void printHand(boolean showFirstCard) {
		
		System.out.printf("%s's cards:\n", this.name);
		for(int c = 0; c < this.numCards; c++) {
			if(c == 0 && !showFirstCard) {
				System.out.println(" [hidden]");
			} else {
				System.out.printf(" %s\n", this.hand[c].toString());
			}
		}
	}
	
	public String toString() {
		return name.toString();
	}
}