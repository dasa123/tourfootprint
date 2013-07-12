package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import models.Image;
import models.Post;
import models.User;
import play.*;
import play.db.jpa.Blob;
import play.mvc.*;

public class MyProfile extends Controller
{

	public static void submit(String email, String password, String fullname,
			String country, String city, String gender, String religion,
			Date birthday, Blob photoData, String tags, String folloUserid,
			String unFollowUserid, String delTag)
	{
		Long userId = Long.parseLong(session.get("userId"));
		// System.out.println("follo:" + folloUserid);
		if (userId != null)
		{
			User user = User.findById(userId);
			if (user != null)
			{
				System.out.println("Now name is:" + fullname);
				if (fullname != null)
				{
					user.fullname = fullname;
				}
				if (city != null)
				{
					user.city = city;
				}
				if (gender != null)
				{
					user.gender = gender;
				}
				if (religion != null)
				{
					user.religion = religion;
				}
				if (birthday != null)
				{
					user.birthday = birthday;
				}

				System.out.println("Photo :" + photoData);
				if (photoData != null)
				{
					user.image = new Image(photoData);
					user.image.save();
				}

				if (tags != null)
				{
					LinkedList<String> tagList = new LinkedList<String>();
					for (String t : tags.split(";"))
					{
						t = t.trim();
						if (t.length() > 0)
						{
							tagList.add(t);
						}
					}
					user.tags = tagList;
				}

				if (delTag != null)
				{
					user.tags.remove(delTag);
				}

				if (folloUserid != null)
				{
					User folloUser = User.findById(Long.parseLong(folloUserid));
					if (!user.followed.contains(folloUser))
					{
						user.followed.add(folloUser);
					}
				}

				if (unFollowUserid != null)
				{
					User unFollowUser = User.findById(Long
							.parseLong(unFollowUserid));
					if (user.followed.contains(unFollowUser))
					{
						user.followed.remove(unFollowUser);
					}
				}

				user.save();
			}

			MyProfile.page();
		}
	}

	public static void page()
	{
		String userId = session.get("userId");
		if (userId != null)
		{
			User user = User.findById(Long.parseLong(userId));
			if (user != null)
			{
				String tagString = getTags(user.tags);
				render(user, tagString);
			}
		}
		
		MainPage.index();
	}

	public static String getTags(List<String> tags)
	{
		if (tags == null)
		{
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : tags)
		{
			if (flag)
			{
				result.append(";");
			}
			else
			{
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
	}

}
