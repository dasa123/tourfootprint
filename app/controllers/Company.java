package controllers;

import models.User;
import play.mvc.Controller;

public class Company extends Controller {
	
	public static void aboutUs(){
		String userId = session.get("userId");
		if (userId != null)
		{
			User user = User.findById(Long.parseLong(userId));

			if (user != null)
			{
				render(user);
			}
		}

		render();
	}
	
	public static void privacyPolicy(){
		String userId = session.get("userId");
		if (userId != null)
		{
			User user = User.findById(Long.parseLong(userId));

			if (user != null)
			{
				render(user);
			}
		}

		render();
	}
	
	public static void tos(){
		String userId = session.get("userId");
		if (userId != null)
		{
			User user = User.findById(Long.parseLong(userId));

			if (user != null)
			{
				render(user);
			}
		}

		render();
	}
}