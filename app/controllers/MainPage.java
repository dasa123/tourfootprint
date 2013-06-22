package controllers;

import play.*;
import play.mvc.*;

import java.io.PrintWriter;
import java.util.*;
import models.*;

public class MainPage extends Controller {
	
    public static void register(String email, String password, String password_retyped) {
        
    	User user = User.find("byEmail", email).first();
    	
    	if ( user == null ) {
    		if ( password.equals(password_retyped) && password != null ) {
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
    	
    	//advertisement is not shown on the main page.
    	renderArgs.put("advertisement", false);
    	
    	if ( error ==  1 ) {
    		renderArgs.put("passError", true);
    	}
    	else if ( error == 2 ){
    		renderArgs.put("userExists", true);
    	}
    	else if ( error == 3 ){
    		renderArgs.put("loginError", true);
    	}
    	
    	renderArgs.put("email", user.email);
    	
    	if (authenticated == true) {
    		renderArgs.put("authenticated", true);
    		render("pages/mainPageNotAuth.html");
    	}
    	else {
    		renderArgs.put("authenticated", true);
    		render("pages/mainPageNotAuth.html");
    	}
    }
    
    //Login with e-mail address and password. 
    public static void login(String email, String password) {
        System.out.println("email :" + email);
        System.out.println("pwd:" + password);
        
        User user = User.find("byEmailAndPassword", email, password).first();
        
        System.out.println("User :" + user);
        
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