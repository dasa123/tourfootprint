package controllers;

import java.util.LinkedList;
import java.util.List;

import models.Post;
import models.User;
import play.mvc.Controller;

public class PostStream extends Controller{
	public static void page()
	{
		String userId = session.get("userId");
		
		if (userId != null)
		{
			User user = User.findById(Long.parseLong(userId));
			
			if (user != null)
			{
				//get all posts from all users in a list
				List<Post> posts = new LinkedList<Post>();
				
				//order posts by date
				
				//render
				render(user, posts);
			}
		}
		
		MainPage.index();
	}
}
