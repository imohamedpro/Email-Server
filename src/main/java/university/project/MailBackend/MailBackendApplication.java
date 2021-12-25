package university.project.MailBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import university.project.MailBackend.Model.Mail;
import university.project.MailBackend.Model.User;
import university.project.MailBackend.Service.MailService;

import java.io.IOException;

@SpringBootApplication
public class MailBackendApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(MailBackendApplication.class, args);
//	}

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MailBackendApplication.class, args);
		MailService service = new MailService();
		User user = new User("Moooo", "Magdy", "mooooh@mail.com");
		boolean bool = service.addNewUser(user);
		if(!bool){
			System.out.println("Mail is the same");
		}
		service.addFolder("Job", 0);
		user = new User("Name", "Haha", "hiiiii@mail.com");
		bool = service.addNewUser(user);
		if(!bool){
			System.out.println("Mail is the same");
		}
		user = new User("John", "Steve", "moooh@mail.com");
		bool = service.addNewUser(user);
		if(!bool){
			System.out.println("Mail is the same");
		}
		user = new User("John", "Steve", "mh@mail.com");
		bool = service.addNewUser(user);
		if(!bool){
			System.out.println("Mail is the same");
		}
	}

}
