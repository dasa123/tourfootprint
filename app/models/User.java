package models;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity(name = "user_table")
public class User extends Model
{
	public String email;
	public String password;
	public String fullname;
	public String country;
	public String city;
	public String gender;
	public String religion;
	public Date birthday;

	@OneToOne
	public Image image;

	@ElementCollection
	public List<String> tags;
	@OneToMany
	public List<User> followed;
	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
	public List<Post> posts;

	public User(String email, String password, String fullname, String country,
			String city, String gender, String religion, Date birthday,
			Image image, List<String> tags)
	{
		super();
		this.email = email;
		this.password = password;
		this.fullname = fullname;
		this.country = country;
		this.city = city;
		this.gender = gender;
		this.religion = religion;
		this.birthday = birthday;
		this.image = image;
		this.tags = tags;

		if (image == null)
		{
			Blob userImageBlob = new Blob();
			try
			{
				userImageBlob.set(new FileInputStream(
						"public/images/defaultUserImage.jpg"), "jpg");
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}

			this.image = new Image(userImageBlob);
			this.image.save();
		}
	}
}
