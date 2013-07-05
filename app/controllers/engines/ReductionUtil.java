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
			if (Character.isAlphabetic(c))
			{
				consontantString += c;
			}
		}

		// reduce remaining chars
		String reducedString = "";
		for (char c : consontantString.toCharArray())
		{
			if (Character.isAlphabetic(c))
			{
				int charIndex = c - 'a';
				reducedString += reducedAlphabet.charAt(charIndex);
			}
		}

		return reducedString;
	}
}
