package controllers;

import models.User;
import play.mvc.Controller;

public class MyPosts extends Controller{
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
		
		MainPage.index();
	}
}
