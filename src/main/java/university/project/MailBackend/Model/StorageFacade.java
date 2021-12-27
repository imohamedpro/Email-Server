package university.project.MailBackend.Model;

import university.project.MailBackend.Interfaces.IStorage;
import university.project.MailBackend.Service.FileService;

public class StorageFacade {
    private IStorage storage;
    private FileService fileService;
    private String defaultPath;
    StorageFacade(IStorage storage, FileService fileService){
        this.storage = storage;
        this.fileService = fileService;
        this.defaultPath = "Database/";
    }

    public Email getEmail(String user, int emailID){
        String path = defaultPath + user + "/Data.json";
        UserData userData = (UserData) fileService.readFile(path, UserData.class, false);
        return userData.emails.get(emailID);
    }

    public void setEmail(String user, Email email, String type){
        String path = defaultPath + user + "/Data.json";
        UserData userData = (UserData) fileService.readFile(path, UserData.class, false);
        userData.addEmail(email, type);
        fileService.writeFile(path, userData);
    }

    public Folder getFolder(String user, String folderName){
        String path = defaultPath + user + "/Data.json";
        UserData userData = (UserData) fileService.readFile(path, UserData.class, false);
        return userData.folders.get(folderName);
    }

    public void setFolder(String user, Folder folder){
        String path = defaultPath + user + "/Data.json";
        UserData userData = (UserData) fileService.readFile(path, UserData.class, false);
        userData.addFolder(folder);
        fileService.writeFile(path, userData);
    }

    public Contact getContact(String user, int contactID){
        String path = defaultPath + user + "/Contacts.json";
        UserContact userContact = (UserContact) fileService.readFile(path, UserContact.class, false);
        return userContact.getContact(contactID);
    }

    public void setContact(String user, Contact contact){
        String path = defaultPath + user + "/Contacts.json";
        UserContact userContact = (UserContact) fileService.readFile(path, UserContact.class, false);
        userContact.addContact(contact);
        fileService.writeFile(path, userContact);
    }

    public UserInfo getUserInfo(String user){
        String path = defaultPath + user + "/Info.json";
        return (UserInfo) fileService.readFile(path, UserInfo.class, false);
    }

    public void setUserInfo(UserInfo userInfo){
        String path = defaultPath + userInfo.getEmail() + "/Info.json";
        fileService.writeFile(path, userInfo);
    }

}
