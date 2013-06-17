import java.util.Arrays;
import java.util.Date;

import models.Comment;
import models.Post;
import models.User;

import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class ModelsTest extends UnitTest
{

	@Test
	public void mainTest()
	{
		Fixtures.deleteDatabase();
		
		//Test user creation
		User user1 = new User("a@b.c", "pwd", "firstname lastname", "germany",
				"munich", "male", "religion1", new Date(), null, Arrays.asList(
						"tag1", "tag2", "tag3"));
		
		user1.save();
		
		assertEquals(User.count(), 1);
		
		User userTest1 = User.all().first();
		assertEquals(userTest1.email, user1.email);
		assertEquals(userTest1.password, user1.password);
		assertEquals(userTest1.fullname, user1.fullname);
		assertEquals(userTest1.country, user1.country);
		assertEquals(userTest1.city, user1.city);
		assertEquals(userTest1.gender, user1.gender);
		assertEquals(userTest1.religion, user1.religion);
		assertEquals(userTest1.birthday, user1.birthday);
		assertArrayEquals(userTest1.tags.toArray(), new String[]{"tag1", "tag2", "tag3"});
		
		
		//Test post creation
		Post post1 = new Post("title", "desc", null, new Date(), true, user1, null, Arrays.asList("post tag1", "post tag2"));
		post1.save();
		
		Post testPost1 = Post.all().first();

		assertEquals(testPost1.title, "title");
		assertEquals(testPost1.description, "desc");
		assertEquals(testPost1.sender.id, user1.id);
		assertArrayEquals(testPost1.tags.toArray(), new String[]{"post tag1", "post tag2"});
		
		Post post2 = new Post("title2", "desc2", null, new Date(), false, user1, null, Arrays.asList("post tag1", "post tag2"));
		post2.save();
		
		//Test user posts
		user1.refresh();
		assertEquals(user1.posts.size(), 2);
		
		Post user1Post1 = user1.posts.get(0);
		assertEquals(post1.title, user1Post1.title);
		assertEquals(post1.description, user1Post1.description);
		assertEquals(post1.postingDate, user1Post1.postingDate);
		assertEquals(post1.sender.id, user1Post1.sender.id);
		assertEquals(post1.tags, user1Post1.tags);
		
		
		Post user2Post2 = user1.posts.get(1);
		assertEquals(post2.title, user2Post2.title);
		assertEquals(post2.description, user2Post2.description);
		assertEquals(post2.postingDate, user2Post2.postingDate);
		assertEquals(post2.sender.id, user2Post2.sender.id);
		assertEquals(post2.tags, user2Post2.tags);
		
		
		//Test comment creation
		User user2 = new User("x@y.z", "pwd2", "firstname2 lastname2", "germany",
				"munich", "male", "religion2", new Date(), null, Arrays.asList(
						"tag4", "tag5", "tag6"));
		user2.save();
		
		Comment comment1 = new Comment("comment1 text", 4, user2, post1);
		comment1.save();

		Comment testComment1 = Comment.all().first();
		assertEquals(testComment1.text, comment1.text);
		assertEquals(testComment1.rating, comment1.rating);
		assertEquals(testComment1.sender.id, comment1.sender.id);
		assertEquals(testComment1.post.id, comment1.post.id);
		
		Comment comment2 = new Comment("comment2 text", 4, user2, post1);
		comment2.save();

		Comment comment3 = new Comment("comment3 text", 4, user1, post1);
		comment3.save();
		
		assertEquals(Comment.count(), 3);
		
		//Test post comments
		post1.refresh();
		assertEquals(post1.comments.size(), 3);
		
		Comment post1Comment1 = post1.comments.get(0);
		assertEquals(post1Comment1.id, comment1.id);
		assertEquals(post1Comment1.text, comment1.text);
		assertEquals(post1Comment1.rating, comment1.rating);
		assertEquals(post1Comment1.sender.id , comment1.sender.id);
		assertEquals(post1Comment1.post.id, comment1.post.id);
	}

}
