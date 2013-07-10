package controllers;

import play.*;
import play.data.validation.Email;
import play.mvc.*;

import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import models.Image;
import models.Post;
import models.User;
import play.data.validation.Email;
import play.db.jpa.Blob;
import play.mvc.Controller;

public class MainPage extends Controller {

	public static void register(@Email String email, String password,
			String password_retyped) {
		// TODO:
		String userId = session.get("userId");
		if (userId != null) {
			User user = User.findById(Long.parseLong(userId));
			if (user != null) {
				MyPosts.page();
			}
		}

		if (!validation.email(email).ok) {
			// index(false, false, 4, null);
			// return;
		}

		User user = User.find("byEmail", email).first();

		if (user == null) {
			if (password.equals(password_retyped) && password.length() != 0) {
				User newUser = new User(email, password, null, null, null,
						null, null, null, null, null);

				newUser.save();
				// render("pages/myPosts.html");
				session.put("userId", newUser.id);
				MainPage.index();
			} else {
				// index(false, false, 1, null);
			}
		} else {
			// index(false, false, 2, null);
		}
	}

	public static void index() {
		String userId = session.get("userId");

		if (userId != null) {
			User user = User.findById(Long.parseLong(userId));
			if (user != null) {
				render(user);
			}
		}

		// if no (valid) user authenticated

		Post mostLikedPost = Post.getMostLikedWithImages();
		Post recentlyFinishedPost = Post.getMostRecentWithImages();

		render(mostLikedPost, recentlyFinishedPost);
	}

	// Login with e-mail address and password.
	public static void login(String email, String password) {
		String userId = session.get("userId");
		if (userId != null) {
			User user = User.findById(Long.parseLong(userId));
			if (user != null) {
				MyPosts.page();
			}
		}

		User user = User.find("byEmailAndPassword", email, password).first();

		if (user != null) {
			session.put("userId", user.id);
		}

		index();
	}

	public static void logout() {
		
		if(session.contains("googleAccessToken")) {
			String token = session.get("googleAccessToken");
			System.out.println("logout: Google access token :" + token);
		try {
			new URL("https://accounts.google.com/o/oauth2/revoke?token=" + token).openConnection().getInputStream();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
		// session.put("userId", null);
		session.clear();
		index();
	}

	public static void pageNotAuthenticated(int error) {
		if (error == 1) {
			renderArgs.put("passError", "true");
		} else if (error == 2) {
			renderArgs.put("userExists", "true");
		} else if (error == 3) {
			renderArgs.put("loginError", "true");
		} else if (error == 4) {
			renderArgs.put("emailError", "true");
		}

		renderArgs.put("authenticated", "false");

		render();
	}
	
	public static void getRandomPicture(Long postId) {
		
		if (postId != null) {
			Post post = Post.findById(postId);
			List<Image> images = post.content.pictures;
			Random generator = new Random();
			Long id = images.get(generator.nextInt(images.size())).getId();
			RequestUtils.renderImage(id);
		}
	}
	
	public static void google(String email, String fullname, String accessToken) {
		System.out.println("google callback: " + fullname + " " + email);
		
		User user = User.find("byEmail", email).first();
		
		if (user == null) {
			user = new User(email, null, fullname, null, null,
					null, null, null, null, null);
			user.save();
			// render("pages/myPosts.html");
			
			System.out.println("asdf");
		}
		
		session.put("userId", user.id);
		System.out.println("new token: " + accessToken);
		session.put("googleAccessToken", accessToken);
		MyPosts.page();
	}
}