package controllers.engines;

public class ReductionUtil
{
	public static String reduceString(String s)
	{
		// String originalAlphabet = "abcdefghijklmnopqrstuvwxyz";
		String reducedAlphabet = "abcdefghigklnnopqrstuvvxyz";
		// make lowercase
		s = s.toLowerCase();

		// keep only consonants
		String consontantString = "";
		for (char c : s.toCharArray())
		{
			if (isAlphabetic(c))
			{
				consontantString += c;
			}
		}

		// reduce remaining chars
		String reducedString = "";
		for (char c : consontantString.toCharArray())
		{
			if (isAlphabetic(c))
			{
				int charIndex = c - 'a';
				reducedString += reducedAlphabet.charAt(charIndex);
			}
		}

		return reducedString;
	}
	
	private static boolean isAlphabetic(char c)
	{
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
	}
}
