package controllers;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import models.Image;
import models.Post;
import models.User;
import play.db.jpa.Blob;
import play.mvc.Controller;

import com.google.gson.GsonBuilder;

import controllers.engines.RecommendationEngine;
import controllers.engines.SearchEngine;

public class SearchResults extends Controller
{
//	public static void page(String searchString)
//	{
//		List<Post> searchResults = SearchEngine.search(searchString);
//		
//		String userId = session.get("userId");
//		
//		if(userId != null)
//		{
//			User user = User.findById(Long.parseLong(userId));
//			
//			if (user != null)
//			{
//				RecommendationEngine.sortRecommendations(searchResults, user.tags);
//				
//				render(user, searchString, searchResults);
//			}
//		}
//		
//		render(searchString, searchResults);
//	}
	
	public static void page(String searchString)
	{
		String userId = session.get("userId");
		
		if(userId != null)
		{
			User user = User.findById(Long.parseLong(userId));
			
			if (user != null)
			{
				render(user, searchString);
			}
		}
		
		render(searchString);
	}
	
	private static List<HashMap<String,String>> postListToCollections(List<Post> posts)
	{
		List<HashMap<String,String>> result = new LinkedList<HashMap<String,String>>();
		
		for(Post post : posts)
		{
			
			HashMap postMap = new HashMap();
			postMap.put("id", post.id);
			postMap.put("title", post.title);
			postMap.put("description", post.description);
			if(post.mapLocation != null)
			{
				postMap.put("longitude", post.mapLocation.longitude);
				postMap.put("latitude", post.mapLocation.latitude);
			}
			
			result.add(postMap);
		}
		
		return result;
	}
	
	public static void doSearch(String searchString) {
		List<Post> searchResults = SearchEngine.search(searchString);

		String userId = session.get("userId");
		
		if(userId != null)
		{
			User user = User.findById(Long.parseLong(userId));
			
			if (user != null)
			{
				RecommendationEngine.sortRecommendations(searchResults, user.tags);
				
				renderJSON(postListToCollections(searchResults));
			}
		}
		
		renderJSON(postListToCollections(searchResults));
	}
}
