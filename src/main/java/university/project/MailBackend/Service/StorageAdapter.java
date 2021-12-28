package university.project.MailBackend.Service;


import university.project.MailBackend.Interfaces.IStorage;
import university.project.MailBackend.Model.Contact;
import university.project.MailBackend.Model.Email;
import university.project.MailBackend.Model.Folder;
import university.project.MailBackend.Model.UserContact;
import university.project.MailBackend.Model.UserData;
import university.project.MailBackend.Model.UserInfo;

public class StorageAdapter {
    private IStorage storage;

    StorageAdapter(IStorage storage){
        this.storage = storage;
    }

    public Email getEmail(String user, int emailID){
        return storage.getUserData(user).emails.get(emailID);
    }
    
    public Email readEmail(String user, int emailID){
        return storage.getUserData(user).readEmail(emailID);
    }
    public void setEmail(String user, Email email, String type){
        UserData data = storage.getUserData(user);
        data.addEmail(email, type);
        storage.setUserData(data, user);
    }

    public void moveToTrash(String user, int emailID){
        UserData data = storage.getUserData(user);
        data.moveToTrash(emailID);
        storage.getUserData(user);
    }
    public void deleteEmail(String user, int emailID){
        UserData data = storage.getUserData(user);
        data.deleteEmail(emailID);
        storage.getUserData(user);
    }

    public Folder getFolder(String user, String folderName){
        return storage.getUserData(user).folders.get(folderName);
    }

    public void setFolder(String user, Folder folder){
        UserData data = storage.getUserData(user);
        data.addFolder(folder);
        storage.setUserData(data, user);
    }

    public Contact getContact(String user, int ContactID){
        return storage.getUserContact(user).contacts.get(ContactID);
    }

    public void setContact(String user, Contact contact){
        UserContact userContact = storage.getUserContact(user);
        userContact.contacts.put(contact.getID(), contact);
        storage.setUserContact(userContact, user);
    }

    public UserInfo getUserInfo(String user){
        return storage.getUserInfo(user);
    }

    public void setUserInfo(UserInfo info){
        // UserInfo info = storage.getUserInfo(user.getEmail());
        // info = user;
        // storage.setUserInfo(info);
        storage.setUserInfo(info);
    }

    public void createAcount(UserInfo info, UserData data, UserContact contact){
        storage.setUserInfo(info);
        storage.setUserContact(contact, info.getEmail());
        storage.setUserData(data, info.getEmail());
    }

}