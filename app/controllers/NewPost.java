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

public class NewPost extends Controller
{

	public static void page()
	{
		String userId = session.get("userId");
		if (userId != null)
		{
			User user = User.findById(Long.parseLong(userId));

			if (user != null)
			{
				render(user);
			}
		}

		MyPosts.page();
	}

	public static void submit(String submitAction, String title,
			String description, Blob photoData1, Blob photoData2,
			Blob photoData3, Blob photoData4, String video, String shareRadio,
			String address, Double locationLongitude, Double locationLatitude,
			String tags)
	{
		String userId = session.get("userId");
		if (userId != null)
		{
			User sender = User.findById(Long.parseLong(userId));

			if (sender != null)
			{
				if (submitAction.equals("post"))
				{
					MapLocation mapLocation;
					try
					{
						mapLocation = new MapLocation(address,
								locationLongitude, locationLatitude);
					}
					catch (Exception e)
					{
						mapLocation = new MapLocation(address, 0.0, 0.0);
					}

					mapLocation.save();

					LinkedList<Image> imageList = new LinkedList<Image>();
					for (Blob pd : Arrays.asList(photoData1, photoData2,
							photoData3, photoData4))
					{
						if (pd != null)
						{
							Image image = new Image(pd).save();
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

					Post post = new Post(title, description, mapLocation,
							new Date(), shareRadio.equals("yes"), sender,
							content, tagList);
					post.save();
				}
				else if (submitAction.equals("cancel"))
				{
				}

				// redirect to edit post
				MyPosts.page();
			}
		}

		MainPage.index();
	}

}
