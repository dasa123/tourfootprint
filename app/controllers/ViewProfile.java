package controllers;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import models.Image;
import models.Post;
import models.User;
import play.*;
import play.db.jpa.Blob;
import play.mvc.*;

public class ViewProfile extends Controller
{

	public static void page(Long Id)
	{
		String userId = session.get("userId");
		if (userId != null)
		{
			User user = User.findById(Long.parseLong(userId));
			
			if (user != null)
			{
				if (Long.parseLong(userId) == Id)
				{
					MyProfile.page();
				}
				else if (Id != null)
				{
					boolean followed = false;
					User otherUser = User.findById(Id);
					if (otherUser != null)
					{
						if (user.followed.contains(otherUser))
						{
							followed = true;
						}
						
						user = otherUser;
						render(user, followed);
					}
				}
			}
		}
		
		MyProfile.page();
	}

}
