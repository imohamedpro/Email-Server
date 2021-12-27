package university.project.MailBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import university.project.MailBackend.Model.Filter.CriteriaInfo;
import university.project.MailBackend.Model.Filter.FromCriteria;
import university.project.MailBackend.Model.Mail;
import university.project.MailBackend.Model.MailHeader;
import university.project.MailBackend.Model.User;
import university.project.MailBackend.Service.MailService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		service.addFolder("Work", 0);
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

		MailHeader header = new MailHeader("mooooh@mail.com", new ArrayList<>(List.of("moooh@mail.com")), "hello", 4, 0);
		Mail mail = new Mail(1, header, "This is a body message.", null, null, false);

		service.sendMail(mail);
		header = new MailHeader("mooooh@mail.com", new ArrayList<>(List.of("hiiiii@mail.com", "mh@mail.com", "moooh@mail.com")), "hello222", 4, 0);
		mail = new Mail(1, header, "Testing....", null, null, false);

		service.sendMail(mail);
		header = new MailHeader("moooh@mail.com", new ArrayList<>(List.of("hiiiii@mail.com", "mh@mail.com", "mooooh@mail.com")), "hello222", 4, 0);
		mail = new Mail(1, header, "Testing....", null, null, false);

		service.sendMail(mail);

		service.addCriteriaToFolder("Work", new CriteriaInfo("from", "mh@mail.com"), 0);
	}

}
