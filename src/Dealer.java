import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Dealer {
	List<String> cards = new ArrayList<String>(Arrays.asList("2","3","4","5","6","7","8","9","10","J","Q","K","A"));
	Random rd = new Random();
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
	public static void main(String[] args) {
		Dealer d = new Dealer();	
		String p1[] = d.deck();

		System.out.println(p1[0]+ p1[1]);
		System.out.println(d.checkPower(p1[0], p1[1]));
	}

}
