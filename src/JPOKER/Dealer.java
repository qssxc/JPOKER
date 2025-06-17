package JPOKER;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Dealer {
	private String[] p1deck;
	private String[] p2deck;
	List<String> cards = new ArrayList<String>(Arrays.asList("2","3","4","5","6","7","8","9","10","J","Q","K","A"));
	Random rd = new Random();
	int Batting;
	public int shuffle() 
	{
		return rd.nextInt(cards.size());
	}
	
	public String[] deck() {
		String[] result = {cards.get(shuffle()),cards.get(shuffle())} ;
		
		return result;
	}
	
	private int checkPower(String hand1,String hand2) {
		if(hand1 == hand2) {
			return cards.indexOf(hand1) + 13;
		}
		return (cards.indexOf(hand1) > cards.indexOf(hand2)) ? cards.indexOf(hand1) : cards.indexOf(hand2) ;
	}
	private int checkPower(String hand1) {
		return cards.indexOf(hand1);
	}
	public void Batting(int Batting) {
		this.Batting = Batting;
	}
	//true = 콜 false = 폴드
	public boolean guessPower(String p1d1,String p1d2,String p2d1) {
		int dealerPower = checkPower(p1d1, p1d2);
		if(checkPower(p2d1,"2")>dealerPower) {
			return false;
		}
		else if(dealerPower>12) {
			if(checkPower(p2d1)<checkPower(p1d1)) {
				return true;
			}
			else if(rd.nextInt(12)==12) {
				return false;
			}
		}
		else if(rd.nextInt(12)+1>dealerPower)
		{
			return false;
		}
		return true;
	}
	public boolean open(boolean call,String p1d1,String p1d2,String p2d1,String p2d2) {
		if (call) {
			if(checkPower(p1d1, p1d2)>checkPower(p2d1,p2d2)) return false;
			else return true;
			}
		return true;
	}

}
