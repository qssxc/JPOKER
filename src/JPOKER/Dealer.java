package JPOKER;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Dealer {
	static List<String> cards = new ArrayList<String>(Arrays.asList("2","3","4","5","6","7","8","9","10","J","Q","K","A"));
	static Random rd = new Random();
	int Batting;
	public int shuffle() 
	{
		return rd.nextInt(cards.size());
	}
	
	public String[] deck() {
		String[] result = {cards.get(shuffle()),cards.get(shuffle())} ;
		
		return result;
	}
	
	private static int checkPower(String hand1,String hand2) {
		if(hand1 == hand2) {
			return cards.indexOf(hand1) + 13;
		}
		return (cards.indexOf(hand1) > cards.indexOf(hand2)) ? cards.indexOf(hand1) : cards.indexOf(hand2) ;
	}
	private static int checkPower(String hand1) {
		return cards.indexOf(hand1);
	}
	public void Batting(int Batting) {
		this.Batting = Batting;
		
	}
	
	public static void guessPower(String p1d1,String p1d2,String p2d1) {
		int dealerPower = checkPower(p1d1, p1d2);
		if(checkPower(p2d1,"2")>dealerPower) {
			System.out.println("서렌1");
		}
		else if(dealerPower>12) {
			if(checkPower(p2d1)<checkPower(p1d1)) {
				
			}
			else if(rd.nextInt(12)==12) {
				System.out.println("서렌2");
			}
		}
		else if(rd.nextInt(12)+1>dealerPower)
		{
			System.out.println("서렌3");
		}
		System.out.println(checkPower(p2d1,"2"));
	}
	
	public static void main(String[] args) {
		Dealer d = new Dealer();	
		String p1[] = d.deck();

		System.out.println(p1[0]+ p1[1]);
		System.out.println(checkPower(p1[0], p1[1]));
		guessPower("7", "7", "8");
	}
	
	

}
