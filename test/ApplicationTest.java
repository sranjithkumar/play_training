import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box.Filler;

import com.fasterxml.jackson.databind.JsonNode;

import models.User;

import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.Form;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.WS;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

    @Test
    public void renderTemplate() {
        Content html = views.html.index.render("Hello Worid!");
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Hello Worid!");
    }
    
    @Test
    public void renderUpdate() {
    	String fullName = "ranjith";
    	String company = "juhomi";
    	String email = "ranjith@juhomi.com";
    	String password = "ranjith";
    	User user = new User();
    	user.setFullName(fullName);
    	user.setCompany(company);
    	user.setEmail(email);
    	user.setPassword(password);
    	Form<models.User> form = Form.form(models.User.class);
        Content html = views.html.update_user.render(form.fill(user),user);
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("juhomi");
    }
    @Test
    public void create() {
        Result result = callAction(
          controllers.routes.ref.User.create()
        );
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("FullName");
    }
    
    @Test
    public void routeTest() {
      Result result = routeAndCall(fakeRequest(GET, "/registration"));
      assertThat(result).isNotNull();
    }
    
    @Test
    public void testInServer() {
      running(testServer(3333), new Runnable() {
          public void run() {
             assertThat(
               WS.url("http://localhost:3333/registration").get().get().getBody()
             ).contains("FullName");
          }
      });
    }
    @Test
    public void runInBrowser() {
        running(testServer(3333), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
               browser.goTo("http://localhost:3333/registration"); 
               assertThat(browser.$("#title").getTexts().get(0)).isEqualTo("GATHI::Sign Up");
               browser.fill("#fullName").with("ranjith");
               browser.fill("#company").with("juhomi");
               browser.fill("#email").with("ranjith@juhomi.com");
               browser.fill("#password").with("kumar");
               browser.$("#create").click();
               assertThat(browser.url()).isEqualTo("http://localhost:3333/registration");
               assertThat(browser.$("#userlist li").getText().contains("ranjith"));
            }
        });
    }
}
