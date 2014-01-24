package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.Form;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class User extends Model{

	@Id
	private long id;
	
	@Required
	private String fullName;
	
	@Required
	private String company;
	
	@Email
	private String email;
	
	@Required
	private String password;
	
	private static Finder<Long,User> find = new Finder(Long.class,User.class);

	public static Finder<Long, User> getFind() {
		return find;
	}

	public static void setFind(Finder<Long, User> find) {
		User.find = find;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	public static void save(User user){
		user.save();
	}
	
	public static List<User> all(){
		return find.all();
	}
	
	public static void delete(Long id){
		find.ref(id).delete();
	}
	
	public static User read(Long id){
		User user = User.getFind().byId(id);
		return user;
	}
	
	public static void update(Long id){
		User user = User.getFind().byId(id);
		Form<User> form = Form.form(User.class);
		Form<User> userForm = form.bindFromRequest();
		User u = userForm.get();
		user.setFullName(u.getFullName());
		user.setCompany(u.getCompany());
		user.setEmail(u.getEmail());
		user.setPassword(u.getPassword());
		user.update();
	}
}
