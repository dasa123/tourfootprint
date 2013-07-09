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
      /*if(WebAdministrator.count() == 0) {*/
        Logger.info("Loading Initial Data.");
        Fixtures.loadModels("initial-data.yml");
        List<Post> posts = Post.findAll();
        for (Post post: posts) {
          Logger.info("Looking for files for post: [" + post.title + "]");
          for (int i=0; true; i++) {
            VirtualFile vf = VirtualFile.fromRelativePath("/conf/initialMedia/"
                + JavaExtensions.camelCase(post.title) + "-" + i + ".jpg");
            File imageFile = vf.getRealFile();

            if (imageFile.exists()) {
              try {
            	LinkedList<Blob> pictures = new LinkedList<Blob>();
            	LinkedList<String> picturesTitles = new LinkedList<String>();
            	Blob blobImage = new Blob();
                blobImage.set(new FileInputStream(imageFile), MimeTypes.getContentType(imageFile.getName()));
                pictures.add(blobImage);
                picturesTitles.add(imageFile.getName());
                PostContent content = new PostContent(null,pictures, picturesTitles);
                content.save();
                post.addContent(content);
                post.save();
                Logger.info("File: [%s] Loaded", imageFile.getAbsolutePath());
              } catch (FileNotFoundException e) {
                System.out.println("No upload files found.");
              }
            } else {
              Logger.info("Media Loaded for post [%s]: %d files.", post.title, i);
              break;
            }
          }
        }
      }
    }
//}