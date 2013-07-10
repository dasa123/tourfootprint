package controllers;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

import models.Image;
import models.MapLocation;
import models.Post;
import models.PostContent;
import models.User;
import play.db.jpa.Blob;
import play.mvc.Controller;

public class EditPost extends Controller
{
	public static void page(Long postId)
	{
		if (postId != null)
		{
			Post post = Post.findById(postId);
			if (post != null)
			{
				// make sure user authenticated is allowed to edit
				String userId = session.get("userId");
				if (userId != null)
				{
					User user = User.findById(Long.parseLong(userId));

					if (user != null && user.id == post.sender.id)
					{
						// render edit post
						render(user, post);
					}
				}

				// if user not authenticated render view post
				ViewPost.page(postId);
			}

		}

		// handle case where post was deleted
		MyPosts.page();
	}

	public static void submit(Long postId, String submitAction,
			String deleteImages, String title, String description,
			String video, String shareRadio, String address,
			Double locationLongitude, Double locationLatitude, String tags)
	{
		String userId = session.get("userId");
		if (postId != null && userId != null)
		{
			User sender = User.findById(Long.parseLong(userId));
			Post post = Post.findById(postId);

			if (post != null && sender != null)
			{
				if (submitAction.equals("save"))
				{
					post.title = title;
					post.description = description;
					post.sharedWithOthers = shareRadio.equals("yes");
					post.mapLocation.address = address;
					try
					{
						post.mapLocation.latitude = locationLatitude;
						post.mapLocation.longitude = locationLongitude;
						post.mapLocation.save();
					}
					catch (Exception e)
					{
					}

					post.content.video = video;
					post.content.save();

					LinkedList<String> tagList = new LinkedList<String>();
					for (String t : tags.split(";"))
					{
						t = t.trim();
						if (t.length() > 0)
						{
							tagList.add(t);
						}
					}
					
					post.tags = tagList;
					post.save();

					ViewPost.page(postId);
				}
				else if (submitAction.equals("delete"))
				{
					// check if sender is same !
					if (post.sender.id == sender.id)
					{
						if (post != null)
						{
							post.delete();
						}
					}
				}
			}

			MyPosts.page();
		}
	}
}