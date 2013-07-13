package controllers;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

import models.Comment;
import models.Image;
import models.MapLocation;
import models.Post;
import models.PostContent;
import models.User;
import play.data.validation.Required;
import play.db.jpa.Blob;
import play.mvc.Controller;

public class RequestUtils extends Controller
{
	public static void renderImage(Long imageId)
	{
		Image image = Image.findById(imageId);
		response.setContentTypeIfNotSet(image.imageDate.type());
		renderBinary(image.imageDate.get());
	}
	
	public static void renderUserImageByPostId(Long postId)
	{
		Post post = Post.findById(postId);
		User user = User.findById(post.sender.getId());
		Image image = Image.findById(user.image.getId());
		response.setContentTypeIfNotSet(image.imageDate.type());
		renderBinary(image.imageDate.get());
	}
}
