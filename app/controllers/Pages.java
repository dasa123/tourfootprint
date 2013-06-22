package controllers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import play.mvc.Controller;
import models.MapLocation;
import models.Post;
import models.User;

public class Pages extends Controller
{
	public static void newPost()
	{
		renderArgs.put("authenticated", "true");
		render();
	}

	public static void viewPost(Long postId)
	{
		if (postId != null)
		{
			Post post = Post.findById(postId);
			if (post != null)
			{
				boolean showEditButton = false;

				String userId = session.get("userId");
				if (userId != null)
				{
					User user = User.findById(Long.parseLong(userId));
					showEditButton = user != null;
					renderArgs.put("authenticated", "true");
				}

				render(post, showEditButton);
				return;
			}
		}

		// wrong post id or no post id provided
		String userId = session.get("userId");
		if (userId != null)
		{
			renderArgs.put("authenticated", "true");
			myPosts();
		}
		else
		{
			render("pages/mainPageNotAuth.html");
		}

	}

	public static void editPost(Long postId)
	{
		if (postId == null)
		{
			newPost();
		}
		else
		{
			// make sure user authenticaed is allowed to edit
			String userId = session.get("userId");
			if (userId != null)
			{
				User user = User.findById(Long.parseLong(userId));

				if (user != null)
				{
					Post post = Post.findById(postId);
					if (post != null)
					{
						renderArgs.put("authenticated", "true");
						// render edit post
						render(post);
						return;
					}
				}

			}

			// if user not authenticated render view post
			viewPost(postId);
			return;
		}

		// handle case where post was deleted
		myPosts();
	}

	public static void myPosts()
	{
		String userId = session.get("userId");
		List<Post> myPosts = new LinkedList<Post>();

		if (userId != null)
		{
			User user = User.findById(Long.parseLong(userId));
			if (user != null)
			{
				myPosts = user.posts;
			}
			renderArgs.put("authenticated", "true");
			render(myPosts);
			return;
		}

		render("pages/mainPageNotAuth.html");
	}
}