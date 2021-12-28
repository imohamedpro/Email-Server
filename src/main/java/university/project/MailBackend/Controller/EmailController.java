package university.project.MailBackend.Controller;

import org.springframework.web.bind.annotation.*;
import university.project.MailBackend.Model.Contact;
import university.project.MailBackend.Model.Email;
import university.project.MailBackend.Model.Requests.ContactAndUsername;
import university.project.MailBackend.Model.Requests.ContactSearch;
import university.project.MailBackend.Model.Requests.EmailDelete;
import university.project.MailBackend.Model.Requests.SetFolder;
import university.project.MailBackend.Model.UserInfo;
import university.project.MailBackend.Service.*;

import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping("/api")
public class EmailController {
    private AccountManager accountManager;
    private ContactManager contactManager;
    private EmailManager emailManager;
    private FolderManager folderManager;

    public EmailController(FileService fileService) {
        Storage storage = new Storage(fileService);
        StorageProxy storageProxy = new StorageProxy(storage);
        StorageAdapter storageAdapter = new StorageAdapter(storageProxy);
        this.accountManager = new AccountManager(storageAdapter);
        this.contactManager = new ContactManager(storageAdapter);
        this.emailManager = new EmailManager(storageAdapter);
        this.folderManager = new FolderManager(storageAdapter, new SortFactory());
    }

    @PostMapping("/signup")
    public boolean signUp(@RequestBody UserInfo userInfo){
        return accountManager.signUp(userInfo);
    }

    @PostMapping("/login")
    public boolean signIn(@RequestBody UserInfo userInfo){
        return accountManager.signIn(userInfo);
    }

    @GetMapping("/home-folders")
    public String[] getHomeFolders(@RequestBody String user){
        return folderManager.getFoldersNames(user);
    }

    @GetMapping("/contacts/pages")
    public int getContactPages(
            @RequestParam("user") String user,
            @RequestParam("perPage") int perPage)
    {
        return contactManager.getNumberOfPages(user, perPage);
    }

    @GetMapping("/contacts/get/List")
    public List<Contact> getContactList(
            @RequestParam("user") String user,
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("perPage") int perPage,
            @RequestParam("sorted") boolean sorted)
    {
        return contactManager.getContactsList(user, pageNumber, perPage, sorted);
    }

    @GetMapping("/contacts/get")
    public Contact getContact(
            @RequestParam("user") String user,
            @RequestParam("id") int contactID)
    {
        return contactManager.getContact(user, contactID);
    }

    @GetMapping("/contacts/search")
    public List<Contact> searchContact(@RequestBody ContactSearch contactSearch){
        return contactManager.searchContact(contactSearch.user, contactSearch.tokens, contactSearch.pageNumber, contactSearch.perPage, contactSearch.sorted);
    }

    @DeleteMapping("/contacts/delete")
    public void deleteContact(
            @RequestParam("user") String user,
            @RequestParam("id") int contactID){
        contactManager.deleteContact(user, contactID);
    }

    @PostMapping("/contacts/add")
    public void addContact(@RequestBody ContactAndUsername contactAndUsername){
        contactManager.addContact(contactAndUsername.user, contactAndUsername.contact);
    }

    @PostMapping("/email/send")
    public void sendEmail(@RequestBody Email email){
        emailManager.sendEmail(email);
    }

    @PostMapping("/email/save-draft")
    public void saveDraft(@RequestBody Email email){
        emailManager.sendEmail(email);
    }

    @DeleteMapping("/email/trash")
    public void moveToTrash(@RequestBody EmailDelete emailDelete){
        emailManager.moveToTrash(emailDelete.emailIDs, emailDelete.user);
    }

    @DeleteMapping("/email/delete")
    public void deleteEmails(@RequestBody EmailDelete emailDelete){
        emailManager.deleteEmails(emailDelete.emailIDs, emailDelete.user);
    }

    @GetMapping("/email/read")
    public Email readEmail(
            @RequestParam("id") int emailID,
            @RequestParam("user") String user)
    {
        return emailManager.readEmail(emailID, user);
    }

    @PostMapping("/folder/set")
    public void setFolder(@RequestBody SetFolder setFolder){
        
    }



}
