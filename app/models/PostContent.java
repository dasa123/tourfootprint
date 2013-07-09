package models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class PostContent extends Model
{
	public String video;
	@OneToMany(mappedBy = "content")
	public List<Image> pictures;
	

	public PostContent(String video, List<Image> pictures)
	{
		super();
		this.video = video;
		this.pictures = pictures;
	}
}
