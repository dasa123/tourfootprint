package controllers;

import play.*;
import play.data.validation.Email;
import play.mvc.*;

import java.io.PrintWriter;
import java.util.*;

import javax.print.attribute.standard.PagesPerMinute;

import models.*;

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
}