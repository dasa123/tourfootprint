package controllers;

import play.*;
import play.data.validation.Email;
import play.mvc.*;

import java.io.PrintWriter;
import java.util.*;

import javax.print.attribute.standard.PagesPerMinute;

import models.*;

public class MainPage extends Controller {
	
    public static void register(@Email String email, String password, String password_retyped) {
    	if(session.get("userId") != null)
    	{
    		Pages.myPosts();
    		return;
    	}
    	
    	
        if(! validation.email(email).ok)
        {
        	index(false, false, 4, null);
        	return;
        }
    	
    	User user = User.find("byEmail", email).first();
    	
    	if ( user == null ) {
    		if ( password.equals(password_retyped) && password.length() != 0) {
    			User newUser = new User(email, password, null, null, 
						null,null,null,null,null,null);
    			newUser.save();
    			//render("pages/myPosts.html");
    			session.put("userId", newUser.id);
    			index(false, true, 0, newUser);
    		}
    		else {
    	    	index(false, false, 1, null);
    		}
    	}
    	else {
			index(false, false, 2, null);
    	}
    }
    
    public static void index(boolean advertisement, boolean authenticated, int error, User user) {
    	if(session.get("userId") != null)
    	{
    		Pages.myPosts();
    		return;
    	}
    	
    	//advertisement is not shown on the main page.
    	renderArgs.put("advertisement", "false");
    	
    	if ( error ==  1 ) {
    		renderArgs.put("passError", "true");
    	}
    	else if ( error == 2 ){
    		renderArgs.put("userExists", "true");
    	}
    	else if ( error == 3 ){
    		renderArgs.put("loginError", "true");
    	}
    	else if ( error == 4 ){
    		renderArgs.put("emailError", "true");
    	}
    	
    	renderArgs.put("email", user.email);
    	
    	if (authenticated == true) {
    		renderArgs.put("authenticated", "true");
    		render("pages/mainPageNotAuth.html");
    	}
    	else {
    		renderArgs.put("authenticated", "false");
    		render("pages/mainPageNotAuth.html");
    	}
    }
    
    //Login with e-mail address and password. 
    public static void login(String email, String password) {
    	if(session.get("userId") != null)
    	{
    		Pages.myPosts();
    		return;
    	}
    	
        User user = User.find("byEmailAndPassword", email, password).first();
        
        if(user != null) {
            session.put("userId", user.id);
            index(false, true, 0, user);
        }
        else {
        	index(false, false, 3, null);
        }
    }  

    /*
    public static void logout() {
        session.clear();
        index(false);
    }
	*/
}