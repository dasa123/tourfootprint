package controllers;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import play.*;
import play.db.jpa.Blob;
import play.jobs.*;
import play.libs.MimeTypes;
import play.templates.JavaExtensions;
import play.test.*;
import play.vfs.VirtualFile;
import models.*;

@OnApplicationStart
public class Bootstrap extends Job {
    /*public void doJob() {
        
    	// Check if the database is empty
        if(User.count() == 0) {
            Fixtures.loadModels("initial-data.yml");
        }*/
    
    @Override
    public void doJob() {
      // Check if the database is empty
      if(User.count() == 0) {
        Logger.info("Loading Initial Data.");
        Fixtures.loadModels("initial-data2.yml");
        }
        List<Post> posts = Post.findAll();
        
        for (Post post: posts) {
            Logger.info("Looking for files for post: [" + post.title + "]");
        }
        
        for (Post post: posts) {
          Logger.info("Looking for files for post: [" + post.title + "]");
          
          LinkedList<Image> pictures = new LinkedList<Image>();
          
          for (int i=0; i < 5; i++) {
            
            String imageFile = "public/uploads/"+ post.title + "-" + i + ".jpg";

              try {
            	Blob blobImage = new Blob();
                blobImage.set(new FileInputStream(imageFile), MimeTypes.getContentType(imageFile));
                Image image = new Image(blobImage, imageFile);
                image.save();
                pictures.add(image);
                System.out.println("asdfasfweafaewdfasfads: " + image.getId().toString());
                
                Logger.info("File: [%s] Loaded", imageFile);
              } catch (FileNotFoundException e) {
            	  Logger.info(e.toString());
            	  Logger.info("File: [%s] Not Loaded", imageFile);
              }
           
            }
          PostContent content = new PostContent(null,pictures);
          
          content.save();
          
          for(Image img: pictures){
        	  img.content = content;
        	  img.save();
          }
        
          post.addContent(content);
          post.save();
        }
        
        List<User> users = User.findAll();
		
		for (User user: users) {
	          Logger.info("Looking for files for user: [" + user.email + "]");
	          
	            
	            String imageFile = "public/uploads/user"+ user.email + ".jpg";

	              try {
	            	Blob blobImage = new Blob();
	                blobImage.set(new FileInputStream(imageFile), MimeTypes.getContentType(imageFile));
	                user.image = new Image(blobImage, imageFile);
	                user.image.save();
	                user.save();
	                
	                Logger.info("File: [%s] Loaded", imageFile);
	              } catch (FileNotFoundException e) {
	            	  Logger.info(e.toString());
	            	  Logger.info("File: [%s] Not Loaded", imageFile);
	              }
		}
    }
}