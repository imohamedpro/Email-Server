package university.project.MailBackend.Controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import university.project.MailBackend.Model.*;
import university.project.MailBackend.Model.Requests.*;
import university.project.MailBackend.Service.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping("/api")
public class EmailController {
    private AccountManager accountManager;
    private ContactManager contactManager;
    private EmailManager emailManager;
    private FolderManager folderManager;
    private FileService fileService;

    public EmailController(FileService fileService) {
        this.fileService = new FileService();
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
    public FoldersInfo getHomeFolders(@RequestParam("user") String user){
        return folderManager.getFoldersInfo(user);
    }

    @GetMapping("/contact/pages")
    public int getContactPages(
            @RequestParam("user") String user,
            @RequestParam("perPage") int perPage)
    {
        return contactManager.getNumberOfPages(user, perPage);
    }

    @GetMapping("/contact/load")
    public List<Contact> loadContacts(
            @RequestParam("user") String user,
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("perPage") int perPage,
            @RequestParam("sorted") boolean sorted)
    {
        return contactManager.getContactsList(user, pageNumber, perPage, sorted);
    }

    @GetMapping("/contact/get")
    public Contact getContact(
            @RequestParam("user") String user,
            @RequestParam("id") int contactID)
    {
        return contactManager.getContact(user, contactID);
    }

    @GetMapping("/contact/search")
    public List<Contact> searchContact(@RequestBody ContactSearch contactSearch){
        return contactManager.searchContact(contactSearch.user, contactSearch.tokens, contactSearch.pageNumber, contactSearch.perPage, contactSearch.sorted);
    }

    @DeleteMapping("/contact/delete")
    public void deleteContact(
            @RequestParam("user") String user,
            @RequestParam("id") int contactID){
        contactManager.deleteContact(user, contactID);
    }

    @PostMapping("/contact/add")
    public void addContact(@RequestBody ContactAndUsername contactAndUsername){
        contactManager.addContact(contactAndUsername.user, contactAndUsername.contact);
    }

    @PostMapping("/email/send")
    public void sendEmail(@RequestBody Email email){
        emailManager.sendEmail(email);
    }

    @PostMapping("/email/save-draft")
    public void saveDraft(@RequestBody Email email){
        emailManager.saveDraft(email);
    }

    @DeleteMapping("/email/trash")
    public void moveToTrash(@RequestBody EmailUserClass email){
        emailManager.moveToTrash(email.emailIDs, email.user);
    }

    @DeleteMapping("/email/delete")
    public void deleteEmails(@RequestBody EmailUserClass email){
        emailManager.deleteEmails(email.emailIDs, email.user);
    }

    @GetMapping("/email/get")
    public Email getEmail(
            @RequestParam("id") int emailID,
            @RequestParam("user") String user)
    {
        return emailManager.readEmail(emailID, user);
    }

    @PostMapping("/email/restore")
    public void restoreEmails(@RequestBody EmailUserClass email){
        emailManager.restoreEmails(email.emailIDs, email.user);
    }

    @PostMapping("/email/read")
    public void markAsRead(@RequestBody EmailUserClass email){
        emailManager.markAsRead(email.emailIDs, email.user);
    }

    @PostMapping("/email/unread")
    public void markAsUnread(@RequestBody EmailUserClass email){
        emailManager.markAsUnread(email.emailIDs, email.user);
    }

    @PostMapping("/email/move")
    public void moveEmails(@RequestBody MoveEmailClass moveEmailObject){
        emailManager.moveEmails(moveEmailObject.emailIDs, moveEmailObject.destinationID, moveEmailObject.user);
    }

    @PostMapping("/folder/set")
    public void setFolder(@RequestBody SetFolder setFolder){
        folderManager.setFolder(setFolder.folderID, setFolder.folderName, setFolder.filterTokens, setFolder.user);
    }

    @GetMapping("/folder/get")
    public Folder getFolder(@RequestParam("id") int folderID, @RequestParam("user") String user){
        return folderManager.getFolder(folderID, user);
    }

    @GetMapping("/folder/load")
    public List<HeaderResponse> loadFolder(@RequestBody LoadFolderClass loadFolderClass){
        return folderManager.loadFolder(
                loadFolderClass.folderID,
                loadFolderClass.sortBy,
                loadFolderClass.reverse,
                loadFolderClass.searchToken,
                loadFolderClass.pageNumber,
                loadFolderClass.emailsPerPage,
                loadFolderClass.user);
    }

    @GetMapping("/folder/pages")
    public int getFolderPages(
            @RequestParam("id") int folderID,
            @RequestParam("perPage") int perPage,
            @RequestParam("user") String user
    ){
        return folderManager.getNumberOfPages(folderID, perPage, user);
    }

    @GetMapping("/attachment/download")
    public ResponseEntity<Object> downloadAttachment(
            @RequestParam("user") String username,
            @RequestParam("emailID") int emailID,
            @RequestParam("fileName") String fileName)
    {
        String path = "Database/" + username + "/attachments/" + emailID + "/" + fileName;
        File file = new File(path);

        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(
                    MediaType.parseMediaType("application/txt")).body(resource);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body("Exception occurred for: " + fileName + "!");
        }
    }

    @PostMapping("/attachment/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("user") String username,
            @RequestParam("emailID") int emailID,
            @RequestParam("fileName") String fileName)
    {
        String path = "Database/" + username + "/attachments/" + emailID + "/" + fileName;
        try {
            fileService.writeFile(path, file.getBytes());
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Files uploaded successfully: " + file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body("Exception occurred for: " + file.getOriginalFilename() + "!");
        }
    }

    @DeleteMapping("/attachment/delete")
    public void deleteFile(
            @RequestParam("user") String username,
            @RequestParam("emailID") int emailID,
            @RequestParam("fileName") String fileName)
    {
        String path = "Database/" + username + "/attachments/" + emailID + "/" + fileName;
        fileService.deleteFile(path);
        //Delete file from email
    }

}
