package controllers.engines;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import models.Post;

public class RecommendationEngine
{
	public static void sortRecommendations(final List<Post> posts, final List<String> tags)
	{
		Collections.sort(posts, new Comparator<Post>(){

			@Override
			public int compare(Post o1, Post o2)
			{
				double e = estimateMatch(tags, o1.tags) - estimateMatch(tags, o2.tags); 
				return e > 0? 1 : -1;
			}
		});
	}
	
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
		s1 = ReductionUtil.reduceString(s1);
		s1 = ReductionUtil.reduceString(s1);

		double estimate = 0;
		
		double maxCharError = Math.pow(1.0 * ('z' - 'a'), 2.0);
		
		for(int i = 0; i < Math.min(s1.length(), s2.length()); ++i)
		{
			estimate += 1.0 - (Math.pow(s1.charAt(i) - s2.charAt(i), 2.0) / maxCharError);
		}
		
		estimate /= Math.max(s1.length(), s2.length());
		return estimate;
	}
}
