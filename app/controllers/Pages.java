package controllers;

import play.mvc.Controller;
import models.Post;
import models.User;

public class Pages extends Controller
{
	public static void dummyPage()
	{
		//TODO: remove
		render();
	}
	
	public static void newPost()
	{
		render();
	}

	public static void viewPost(Long postId)
	{
		if (postId != null)
		{
			Post post = Post.findById(postId);
			if (post != null)
			{
				// TODO: get the signed in user id
				User user = User.all().first();
				boolean showEditButton = true; // user.id == post.sender.id;

				// TODO: check user authentication
				render(post, showEditButton);
			}
			
			//TODO: wrong/deleted post id given
		}

		// TODO: no post id provided
	}

	public static void editPost(Long postId)
	{
		if (postId == null)
		{
			newPost();
		}
		else
		{
			//TODO: make sure user authenticaed is allowed to edit
			Post post = Post.findById(postId);
			if (post != null)
			{
				System.out.println("PICTURES SIZE :"
						+ post.content.pictures.size());
				// TODO: check user authentication to make sure he is allowed to
				// view post
				render(post);
			}
			
			//TODO: handle case where post was deleted !
		}
	}
}
