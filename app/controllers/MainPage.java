package controllers;

import play.*;
import play.data.validation.Email;
import play.libs.OAuth2;
import play.libs.WS;
import play.mvc.*;

import java.io.PrintWriter;
import java.util.*;

import javax.print.attribute.standard.PagesPerMinute;

import com.google.gson.JsonObject;

import models.*;

public class MainPage extends Controller {

	/*
	 * Registration.
	 * 
	 * */
	public static void register(@Email String email, String password,
			String password_retyped)
	{
		String userId = session.get("userId");
		if (userId != null)
		{
			User user = User.findById(Long.parseLong(userId));
			if (user != null)
			{
				MyPosts.page();
			}
		}

		User user = User.find("byEmailAndPassword", email, password).first();

		if (user != null) {
			session.put("userId", user.id);
			MainPage.index();
		}

		/*if (!validation.email(email).ok)
		{
			pageNotAuthenticated(4);
			index();//false, false, 4, null
			return;
		}*/
		

		if (user == null)
		{
			if (password.equals(password_retyped) && password.length() != 0)
			{
				User newUser = new User(email, password, null, null, null,
						null, null, null, null, null);
				newUser.save();
				System.out.println("new user id :" + newUser.id);
				// render("pages/myPosts.html");
				session.put("userId", newUser.id);
				MainPage.index();
			}
			else
			{
				// index(false, false, 1, null);
			}
		}
		else
		{
			// index(false, false, 2, null);
		}
	}
	
	
	/*
	 * Login with e-mail address and password.
	 * 
	 * */
		public static void login(String email, String password) {
			
			String userId = session.get("userId");
			
			if (userId != null)
			{
				User user = User.findById(Long.parseLong(userId));

				if (user != null) 
				{
					session.put("userId", user.id);
					MyPosts.page();
				}	
			}

			User user = User.find("byEmailAndPassword", email, password).first();
			if (user != null)
			{
				session.put("userId", user.id);
				MyPosts.page();
			}
			else
			{
				flash.error("Login error. Please try to login again or register.");
				MainPage.index();
			}
		}
	
	
	/*
	 * Import Facebook account
	 * 
	 * */
	public static OAuth2 FACEBOOK = new OAuth2(
			"https://graph.facebook.com/oauth/authorize",
            "https://graph.facebook.com/oauth/access_token",
            "95341411595",
            "8eff1b488da7fe3426f9ecaf8de1ba54");

    public static void facebook() {
    	String userId = session.get("userId");
    	User u = connected(userId);
        JsonObject me = null;
        if (u != null && u.access_token != null) {
            me = WS.url("https://graph.facebook.com/me?access_token=%s", WS.encode((String) u.access_token)).get().getJson().getAsJsonObject();
        }
        MainPage.index();
    }

    public static void auth() {
        if (OAuth2.isCodeResponse()) {
        	String userId = session.get("userId");
            User u = connected(userId);
            OAuth2.Response response = FACEBOOK.retrieveAccessToken(authURL());
            u.access_token = response.accessToken;
            u.save();
            index();
        }
        FACEBOOK.retrieveVerificationCode(authURL());
    }

    //@Before
    static void setuser() {
        User user = null;
        if (session.contains("uid")) {
            Logger.info("existing user: " + session.get("uid"));
            user = User.findById(Long.parseLong(session.get("uid")));
        }
        if (user == null) {
        	user = new User(null, null, null, null, null,
					null, null, null, null, null);
			user.save();
            session.put("uid", user.id);
        }
        //renderArgs.put("user", user);
        index();
    }

    static String authURL() {
        return play.mvc.Router.getFullUrl("Application.auth");
    }

    static User connected(String userId) {
    	
    	if (userId != null)
		{
			User user = User.findById(Long.parseLong(userId));

			if (user != null) 
			{
				session.put("userId", user.id);
				MyPosts.page();
			}	
		}
    	
    	return (User)renderArgs.get("user");
    }
	
    /*
	 * Import Google+ account
	 * 
	 * */
	public static void google(){
		
	}
    
    public static void logout(String value) {
		session.put("userId", null);
		session.clear();
		index();
	}
	
	public static void index() {
		
		String userId = session.get("userId");
		
		System.out.println("userId : " + userId);

		if (userId != null) {
			User user = User.findById(Long.parseLong(userId));
			session.put("userId", user.id);
			
			System.out.println("user :" + user);
			if (user != null) {
				MyPosts.page();
				render(user);
			}
		}

		// if no (valid) user authenticated
		
		System.out.println("index: loaindg page for non-auth user");
		render();
	}

/*	public static void pageNotAuthenticated(int error) {
		if (error == 1) {
			render("passError", "true");
		} else if (error == 2) {
			render("userExists", "true");
		} else if (error == 3) {
			render("loginError", "true");
		} else if (error == 4) {
			renderArgs.put("emailError", "true");
		}

		renderArgs.put("authenticated", "false");

		render();
	}*/
}