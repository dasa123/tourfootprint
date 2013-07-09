package models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class PostContent extends Model
{
	public String video;
	@ElementCollection
	public LinkedList<Blob> pictures;
	@ElementCollection
	public LinkedList<String> picturesTitles;

	public PostContent(String video, LinkedList<Blob> pictures,
			LinkedList<String> picturesTitles)
	{
		super();
		this.video = video;
		this.pictures = pictures;
		this.picturesTitles = picturesTitles;
	}
}
