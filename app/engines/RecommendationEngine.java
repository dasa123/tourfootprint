package engines;

import java.util.Collection;

public class RecommendationEngine
{
	public static double estimateMatch(Collection<String> tagList1, Collection<String> tagList2)
	{
		double estimate = 0;
		for(String s1: tagList1)
		{
			for(String s2: tagList2)
			{
				estimate += estimateStringMatch(s1, s2);
			}
		}
		
		estimate /= tagList1.size() * tagList2.size();
		return estimate;
	}
	
	public static double estimateStringMatch(String s1, String s2)
	{
		// reduce strings
		s1 = reduceString(s1);
		s1 = reduceString(s1);

		double estimate = 0;
		
		double maxCharError = Math.pow(1.0 * ('z' - 'a'), 2.0);
		
		for(int i = 0; i < Math.min(s1.length(), s2.length()); ++i)
		{
			estimate += 1.0 - (Math.pow(s1.charAt(i) - s2.charAt(i), 2.0) / maxCharError);
		}
		
		estimate /= Math.max(s1.length(), s2.length());
		return estimate;
	}

	public static String reduceString(String s)
	{
		// String originalAlphabet = "abcdefghijklmnopqrstuvwxyz";
		String reducedAlphabet = "abcdefghigklnnopqrstuvvxyz";
		// make lowercase
		s = s.toLowerCase();
		
		//keep only consonents
		String consontantString = "";
		for(char c : s.toCharArray())
		{
			if(Character.isAlphabetic(c))
			{
				consontantString += c;
			}
		}
		
		//reduce remaining chars
		String reducedString = "";
		for(char c : consontantString.toCharArray())
		{
			if(Character.isAlphabetic(c))
			{
				int charIndex = c - 'a';
				reducedString += reducedAlphabet.charAt(charIndex);
			}
		}
		
		return reducedString;
	}

}
