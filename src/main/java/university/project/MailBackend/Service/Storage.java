package university.project.MailBackend.Service;

import university.project.MailBackend.Interfaces.IStorage;
import university.project.MailBackend.Model.UserContact;
import university.project.MailBackend.Model.UserData;
import university.project.MailBackend.Model.UserInfo;

public class Storage implements IStorage {
    private FileService fileService;
    private String defaultPath;

    public Storage(FileService fileService){
        this.fileService = fileService;
        this.defaultPath = "Database/";
    }

    @Override
    public UserInfo getUserInfo(String user) {
        String path = defaultPath + user + "/Info.json";
        return (UserInfo) fileService.readFile(path, UserInfo.class, false);
    }

    @Override
    public UserData getUserData(String user) {
        String path = defaultPath + user + "/Data.json";
        return  (UserData) fileService.readFile(path, UserData.class, false);
    }

    @Override
    public UserContact getUserContact(String user) {
        String path = defaultPath + user + "/Contacts.json";
        return  (UserContact) fileService.readFile(path, UserContact.class, false);
    }

    @Override
    public void setUserInfo(UserInfo info) {
        String email = info.getEmail();
        String path = defaultPath + email + "/Info.json";
        fileService.writeFile(path, info);
    }

    @Override
    public void setUserData(UserData data, String user) {
        String path = defaultPath + user + "/Data.json";
        fileService.writeFile(path, data);
    }

    @Override
    public void setUserContact(UserContact contact, String user) {
        String path = defaultPath + user + "/Contacts.json";
        fileService.writeFile(path, contact);
    }
}
