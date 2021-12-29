package university.project.MailBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import university.project.MailBackend.Model.*;
import university.project.MailBackend.Service.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;

@SpringBootApplication
public class MailBackendApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(MailBackendApplication.class, args);
//	}

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MailBackendApplication.class, args);
		FileService fileService = new FileService();
		Storage storage = new Storage(fileService);
		StorageProxy storageProxy = new StorageProxy(storage);
		StorageAdapter storageAdapter = new StorageAdapter(storageProxy);
//		Email email = new Email(-1, false, new HashSet<>(), new EmailHeader("moh@site.com", new String[]{"hello@site.com"}, "Done", new Date(), 4), new EmailBody("DODODO", null), null);
//		EmailManager emailManager = new EmailManager(storageAdapter);
		//emailManager.sendEmail(email);
//		UserInfo userInfo = new UserInfo("hello@site.com", "hahaha");
//		UserContact userContact = new UserContact();
//		HashSet<String> hashSet = new HashSet<>();
//		hashSet.add("mohamed@site.com");
//		userContact.addContact(new Contact("Mohamed", hashSet, 0));
//		UserData userData = new UserData();
//		storageAdapter.createAcount(userInfo, userData ,userContact);
//		userInfo = new UserInfo("moh@site.com", "hahaha");
//		userContact.addContact(new Contact("Ahmed", hashSet, 1));
//		storageAdapter.createAcount(userInfo, userData, userContact);
//		EmailManager emailManager = new EmailManager(storageAdapter);
//		Set<Integer> set = ;
//		set.add("Inbox");
//		Email email = new Email(-1, false, new HashSet<>(), new EmailHeader("hello@site.com", new String[]{"moh@site.com"}, "Heyyyy", new Date(), 4), new EmailBody("Hellooooo", null), null);
//		emailManager.sendEmail(email);
	}

}
