package models;

import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class Post extends Model
{
	public String title;
	public String description;
	public Date postingDate;
	public Boolean sharedWithOthers;
	
	@OneToOne
	public MapLocation mapLocation;
	
	@OneToOne
	public PostContent content;

	@ManyToOne
	public User sender;

	@ElementCollection
	public List<String> tags;

	@OneToMany(mappedBy = "post")
	public List<Comment> comments;

	public Post(String title, String description, MapLocation mapLocation,
			Date postingDate, User sender, PostContent content, List<String> tags)
	{
		super();
		this.title = title;
		this.description = description;
		this.mapLocation = mapLocation;
		this.postingDate = postingDate;
		this.sender = sender;
		this.content = content;
		this.tags = tags;
	}
}