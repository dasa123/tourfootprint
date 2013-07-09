package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class Image extends Model
{
	@ManyToOne
	public PostContent content; 
	public Blob imageDate;
	public String title;

	public Image(Blob imageData)
	{
		this.imageDate = imageData;
	}
	
	public Image(Blob imageData, String title)
	{
		this.imageDate = imageData;
		this.title = title;
	}
}
