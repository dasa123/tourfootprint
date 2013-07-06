package controllers.engines;

import java.util.LinkedList;

import play.db.jpa.JPABase;

import models.Post;

public class SearchEngine
{
	public static LinkedList<Post> search(String searchString)
	{
		searchString = searchString == null? "" : searchString;
		
		// reduce string
		String[] searchWordsArray = searchString.split(" ");

		LinkedList<String> searchWordsList = new LinkedList<String>();
		for (String s : searchWordsArray)
		{
			s = s.trim();
			if (s.length() > 0)
			{
				// reduce string
				s = ReductionUtil.reduceString(s);

				searchWordsList.add(s);
			}
		}

		LinkedList<Post> resultList = new LinkedList<Post>();

		for (JPABase p : Post.findAll())
		{
			Post post = (Post) p;

			String title = ReductionUtil.reduceString(post.title);
			String description = ReductionUtil.reduceString(post.description);

			for (String searchWord : searchWordsList)
			{
				if (title.indexOf(searchWord) != -1
						|| description.indexOf(searchWord) != -1)
				{
					resultList.add(post);
				}
			}
		}

		return resultList;
	}
}
