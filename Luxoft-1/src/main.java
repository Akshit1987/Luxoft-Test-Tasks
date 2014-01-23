
public class main {

	public static void main(String[] args) {
	
		String[] words = {"olleH", "syug", "morf", "tfoxuL"};

		System.out.print( reverseAndConcaternate( words ) );
		
	}
	
	public static String reverseAndConcaternate(String[] words)
	{
		StringBuilder ret = new StringBuilder( words.length );
		
		for(String word : words) 
		{
			ret.append( new StringBuilder(word).reverse().toString() ).append(" ");
		}
		
		return ret.toString();
	}

}
