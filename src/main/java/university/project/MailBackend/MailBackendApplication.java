package university.project.MailBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import university.project.MailBackend.Model.*;
import university.project.MailBackend.Service.FileService;
import university.project.MailBackend.Service.Storage;

import java.io.IOException;
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
		StorageAdapter storageAdapter = new StorageAdapter(storage);
		UserInfo userInfo = new UserInfo("mohamed@site.com", "hello", 0);
		storageAdapter.setUserInfo(userInfo);
		UserContact userContact = new UserContact();
		HashSet<String> hashSet = new HashSet<>();
		hashSet.add("hello@site.com");
		userContact.addContact(new Contact("Mohamed", hashSet, 0));
		fileService.writeFile("Database/mohamed@site.com/Contact.json", userContact);
		//fileService.writeFile("Database/mohamed@site.com/Info.json", userInfo);
//		Map<String, Folder> folders
//		UserData userData = new UserData()
	}

}
