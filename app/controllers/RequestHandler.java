package controllers;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

import models.Comment;
import models.Image;
import models.MapLocation;
import models.Post;
import models.PostContent;
import models.User;
import play.data.validation.Required;
import play.db.jpa.Blob;
import play.mvc.Controller;

public class RequestHandler extends Controller
{
	public static void postComment(Long postId, String commentText, String commentRating)
	{
		if (postId != null)
		{
			Post post = Post.findById(postId);
			
			if (post != null)
			{
				String userId = session.get("userId");
				if (userId != null)
				{
					User sender = User.findById(Long.parseLong(userId));
					
					Comment comment = new Comment(commentText, Integer.parseInt(commentRating), sender, post);
					comment.save();
				}
			}
		}

		Pages.viewPost(postId);
	}
	
	
	public static void newPostSubmit(String submitAction,
			@Required String title, @Required String description,
			Blob photoData1, Blob photoData2, Blob photoData3, Blob photoData4,
			String video, @Required String shareRadio, String address,
			Double locationLongitude, Double locationLatitude, String tags)
	{
		// TODO: get the signed in user id
		User sender = User.all().first();

		if (submitAction.equals("post"))
		{
			MapLocation mapLocation;
			try
			{
				mapLocation = new MapLocation(address, locationLongitude, locationLatitude);
			}
			catch (Exception e)
			{
				mapLocation = new MapLocation(address, 0.0, 0.0);
			}
			mapLocation.save();

			LinkedList<Image> imageList = new LinkedList<Image>();
			for (Blob data : Arrays.asList(photoData1, photoData2, photoData3, photoData4))
			{
				if (data != null)
				{
					Image image = new Image(data).save();
					imageList.add(image);
				}
			}

			PostContent content = new PostContent(video, imageList,
					Arrays.asList("", "", "", ""));
			content.save();

			LinkedList<String> tagList = new LinkedList<String>();
			for (String t : tags.split(";"))
			{
				t = t.trim();
				if (t.length() > 0)
				{
					tagList.add(t);
				}
			}

			Post post = new Post(title, description, mapLocation, new Date(),
					shareRadio.equals("yes"), sender, content, tagList);
			post.save();

			System.out.println("NEW POST ID :" + post.id);

			// redirect to edit post
			Pages.viewPost(post.id);
			return;
		}
		else if (submitAction.equals("cancel"))
		{
			// TODO: go back to main page or stream or something
		}
	}
	
	public static void genericPostSubmit(String postId, String submitAction,
			String deleteImages, String title, String description,
			Blob photoData1, Blob photoData2, Blob photoData3, Blob photoData4,
			String video, String shareRadio, String address,
			String locationLongitude, String locationLatitude, String tags)
	{
		String userId = session.get("userId");
		if (userId == null)
		{
			renderArgs.put("authenticated", "false");
			render("pages/mainPageNotAuth.html");
			return;
		}
		
		User sender = User.findById(Long.parseLong(userId));

		if (sender == null)
		{
			renderArgs.put("authenticated", "false");
			render("pages/mainPageNotAuth.html");
			return;
		}
		
		if (submitAction.equals("post"))
		{
			MapLocation mapLocation;
			try
			{
				mapLocation = new MapLocation(address,
						Double.parseDouble(locationLongitude),
						Double.parseDouble(locationLatitude));
			}
			catch (Exception e)
			{
				mapLocation = new MapLocation(address, 0.0, 0.0);
			}
			mapLocation.save();

			LinkedList<Image> imageList = new LinkedList<Image>();
			if (photoData1 != null)
			{
				Image image = new Image(photoData1).save();
				imageList.add(image);
			}

			if (photoData2 != null)
			{
				Image image = new Image(photoData2).save();
				imageList.add(image);
			}

			if (photoData3 != null)
			{
				Image image = new Image(photoData3).save();
				imageList.add(image);
			}

			if (photoData4 != null)
			{
				Image image = new Image(photoData4).save();
				imageList.add(image);
			}

			PostContent content = new PostContent(video, imageList,
					Arrays.asList("", "", "", ""));
			content.save();

			LinkedList<String> tagList = new LinkedList<String>();
			for (String t : tags.split(";"))
			{
				t = t.trim();
				if (t.length() > 0)
				{
					tagList.add(t);
				}
			}

			Post post = new Post(title, description, mapLocation, new Date(),
					shareRadio.equals("yes"), sender, content, tagList);
			post.save();


			// redirect to edit post
			Pages.myPosts();
			return;
		}
		else if (submitAction.equals("cancel"))
		{
			if(postId != null)
			{
				Pages.viewPost(Long.parseLong(postId));
				return;
			}
		}
		else if (submitAction.equals("save"))
		{
			if (postId != null)
			{
				Post post = Post.findById(Long.parseLong(postId));
				if (post != null)
				{
					post.title = title;
					post.description = description;
					post.sharedWithOthers = shareRadio.equals("yes");
					post.mapLocation.address = address;
					try
					{
						post.mapLocation.latitude = Double
								.parseDouble(locationLatitude);
						post.mapLocation.longitude = Double
								.parseDouble(locationLongitude);
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

					Pages.viewPost(Long.parseLong(postId));
					return;
				}
			}
		}
		else if (submitAction.equals("delete"))
		{
			if (postId != null)
			{
				Post post = Post.findById(Long.parseLong(postId));

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

		Pages.myPosts();
	}

	public static void renderImage(Long imageId)
	{
		Image image = Image.findById(imageId);
		response.setContentTypeIfNotSet(image.imageDate.type());
		renderBinary(image.imageDate.get());
	}
}
