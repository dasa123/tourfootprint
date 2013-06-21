package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

public class MainPage extends Controller {

	public static void index() {
		
		renderArgs.put("advertisement", "false");
		/*
		if ( authenticated ) {
			renderArgs.put("authenticated", "true");
			render("pages/mainPageAuth.html");
			}
		else {
		*/
			renderArgs.put("authenticated", "false");
			render("pages/mainPageNotAuth.html");
		// }
	}

}
