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
            "543391872364338",
            "18b83f7c912e96e2a47f21f5f32f4939");
	
	
	/* Original code from play! code samples
	 * 
	 * public static void index() {
	        User u = connected();
	        JsonObject me = null;
	        if (u != null && u.access_token != null) {
	            me = WS.url("https://graph.facebook.com/me?access_token=%s", WS.encode(u.access_token)).get().getJson().getAsJsonObject();
	        }
	        render(me);
	    }*/
	
	
	/*
	 * Modified code by Claudia. Renamed index() function to facebook().
	 */
	
	
//	public static void facebook() {
//    	User u = connected();
//        me = null;
//        if (u != null && u.access_token != null) {
//            me = WS.url("https://graph.facebook.com/me?access_token=%s", WS.encode(u.access_token)).get().getJson().getAsJsonObject();
//        }
//        index();
//    }

	/* Original code from play! code samples
	 * 
	 * public static void auth() {
        if (OAuth2.isCodeResponse()) {
            User u = connected();
            OAuth2.Response response = FACEBOOK.retrieveAccessToken(authURL());
            u.access_token = response.accessToken;
            u.save();
            index();
        }
        FACEBOOK.retrieveVerificationCode(authURL());
    }*/
	
	
	/*
	 * Modified code by Claudia. Store userId from the session and give it to connected(userId).
	 */
	public static void auth(){
        if (OAuth2.isCodeResponse()) {
            User user = connected();
            
           if (user != null){
            	OAuth2.Response response = FACEBOOK.retrieveAccessToken(authURL());
            	user.access_token = response.accessToken;
            	user.save();
            	
            	JsonObject me = WS.url("https://graph.facebook.com/me?access_token=%s", WS.encode(user.access_token)).get().getJson().getAsJsonObject();
            	           
            	String email = me.get("email").toString();
            	
            	user.email = email;
           
            	System.out.println("auth(): Email: " + email);
            	index();
            }
            else{
            	flash.error("Login error. Please try to login with your email address and password or register.");
				index();
            }
            
        }
    	
        FACEBOOK.retrieveVerificationCode(authURL());
    }

	/* Original code from play! code samples
	 * 
	 *
	@Before
    static void setuser() {
        User user = null;
        if (session.contains("uid")) {
            Logger.info("existing user: " + session.get("uid"));
            user = User.get(Long.parseLong(session.get("uid")));
        }
        if (user == null) {
            user = User.createNew();
            session.put("uid", user.uid);
        }
        renderArgs.put("user", user);
    }}*/

	/*
	 * Modified code by Claudia. Modified the creation of a new user. Call of index() function in the end, instead of renderArgs.
	 *
	 */
	/*@Before
    static void setuser() {
        User user = null;
        if (session.contains("userId")) {
        	String userId = session.get("userId");
            Logger.info("existing user: " + session.get("userId"));
            //user = User.get(Long.parseLong(session.get("userId")));
            //user = User.findById(Long.parseLong(userId));
            
        }
        if (user == null) {
        	newUser = new User(null, null, null, null, null,
					null, null, null, null, null);
			user.save();
			
            session.put("userId", user.id);
        }
        //renderArgs.put("user", user);
        //index();
    }
    */
    static String authURL() {
        return play.mvc.Router.getFullUrl("MainPage.auth");
    }

    /* Original code from play! code samples
	 * 
	 *
    static User connected() {
        return (User)renderArgs.get("user");
    }*/
    
    /*
	 * Modified code by Claudia. Check if a session and user exists, put userId to session if true and lead the user to page MyPosts.
	 */
    
    static User connected() {    	
    	String userId = session.get("userId");
    	
    	if (userId != null){
				User user = User.findById(Long.parseLong(userId));

				if (user != null) 
				{
					return user;
				}
    	}
    	
    	
    	User newUser = new User(null, null, null, null, null,
				null, null, null, null, null);
    	newUser.save();
    	session.put("userId", newUser.id);
    	newUser.save();

    	return newUser;
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

		if (userId != null) 
		{
			User user = User.findById(Long.parseLong(userId));
			session.put("userId", user.id);
			
			if (user != null) {
				MyPosts.page();
				render(user);
		}

		// if no (valid) user authenticated
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
		render();*/
	}
}