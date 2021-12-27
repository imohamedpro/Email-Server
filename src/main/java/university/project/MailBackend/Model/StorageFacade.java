package university.project.MailBackend.Model;

import university.project.MailBackend.Interfaces.IStorage;

public class StorageFacade {
    private IStorage storage;

    StorageFacade(IStorage storage){
        this.storage = storage;
    }

    public Email getEmail(String user, int emailID){
        return null;
    }

    public void setEmail(String user, Email email, String type){

    }

    public Folder getFolder(String user, String folderName){
        return null;
    }

    public void setFolder(String user, Folder folder){

    }

    public Contact getContact(String user, int ContactID){
        return null;
    }

    public void setContact(String user, Contact contact){

    }

    public UserInfo getUserInfo(String user){
        return null;
    }

    public void setUserInfo(UserInfo user){

    }

}
