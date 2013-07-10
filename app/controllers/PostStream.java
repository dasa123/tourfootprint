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
				// Empty bucket, where posts of users, I follow, are put in
				List<Post> posts = new LinkedList<Post>();
				
				// All posts ordered by date
				List<Post> allPosts = Post.find("order by postingDate desc").fetch();
				
				// Users I follow
				List<User> followedUsers = user.followed;
				
				// Put the posts of users, who I follow, in the bucket
				for (int i = 0; i < allPosts.size(); i++) {
					Post currentPost = allPosts.get(i);
					
					System.out.println(currentPost.postingDate.toString());
					
					if ( followedUsers.contains(currentPost.sender) ) {
						posts.add(currentPost);
					}
				}
				
				//render
				render(user, posts);
			}
		}
		
		MainPage.index();
	}
}
