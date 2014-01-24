package controllers;

import java.util.List;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class User extends Controller{
	
	public static Result all(){
		List<models.User> users = models.User.all();
		return ok(views.html.user_list.render(users));
	}

	public static Result create(){
		Form<models.User> form=  Form.form(models.User.class);
		return ok(views.html.registration.render(form));
	}
	
	public static Result save(){
		Form<models.User> form=  Form.form(models.User.class);
		Form<models.User> userForm =form.bindFromRequest();
		if(userForm.hasErrors()) {
		    return badRequest(
		    		views.html.registration.render(userForm)
		    );
		  }
		else {
			models.User.save(userForm.get());
			List<models.User> users = models.User.all();
			flash("success", "User Created Successfully...");
			return ok(views.html.user_list.render(users));  
		  }
	}
	
	public static Result delete(Long id){
		models.User.delete(id);
		List<models.User> users = models.User.all();
		flash("success", "User Deleted Successfully...");
		return ok(views.html.user_list.render(users));
	}
	
	public static Result read(Long id){
		 models.User user = models.User.read(id);
		 Form<models.User> form = Form.form(models.User.class);
		 return ok(views.html.update_user.render(form.fill(user),user));
		}
	
	public static Result update(Long id){
		 models.User.update(id);
		 List<models.User> users = models.User.all();
		 flash("success", "User Updated Successfully...");
		 return ok(views.html.user_list.render(users));
		}
}
