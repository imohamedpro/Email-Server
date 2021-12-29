package university.project.MailBackend.Service;


import university.project.MailBackend.Interfaces.IStorage;
import university.project.MailBackend.Model.Contact;
import university.project.MailBackend.Model.Email;
import university.project.MailBackend.Model.Folder;
import university.project.MailBackend.Model.UserContact;
import university.project.MailBackend.Model.UserData;
import university.project.MailBackend.Model.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class StorageAdapter {
    private IStorage storage;

    public StorageAdapter(IStorage storage){
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

    /*
        Needs refactoring
    */
    public void markAsRead(int emailID, String user){
        UserData data = storage.getUserData(user);
        data.markAsRead(emailID);
        storage.setUserData(data, user);
    }

    public void markAsUnread(int emailID, String user){
        UserData data = storage.getUserData(user);
        data.markAsUnread(emailID);
        storage.setUserData(data, user);
    }
    public void moveToTrash(String user, int emailID){
        UserData data = storage.getUserData(user);
        data.moveToTrash(emailID);
        storage.setUserData(data, user);
    }
    public void deleteEmail(String user, int emailID){
        UserData data = storage.getUserData(user);
        data.deleteEmail(emailID);
        storage.setUserData(data, user);
    }
    public void restoreEmail(String user, int emailID){
        UserData data = storage.getUserData(user);
        data.restoreEmail(emailID);
        storage.setUserData(data, user);
    }    
    public void moveEmail(String user, int emailID, Integer destinationID){
        UserData data = storage.getUserData(user);
        data.moveEmail(emailID, destinationID);
        storage.setUserData(data, user);
    }

    public Folder getFolder(String user, int folderID){
        return storage.getUserData(user).folders.get(folderID);
    }
    public Email[] getFolderContent(String user, int folderID, String searchToken){
        return storage.getUserData(user).getFolderContent(folderID, searchToken);
    }

    public void setFolder(String user, Folder folder){
        UserData data = storage.getUserData(user);
        data.addFolder(folder);
        storage.setUserData(data, user);
    }

    public ArrayList<Contact> getContactsList(String user){
        ArrayList<Contact> contacts = new ArrayList<>();
        HashMap<Integer, Contact> contactsMap = storage.getUserContact(user).contacts;
        for(Integer key: contactsMap.keySet()){
            contacts.add(contactsMap.get(key));
        }
        return contacts;
    }

    public Contact getContact(String user, int ContactID){
        return storage.getUserContact(user).contacts.get(ContactID);
    }

    public void setContact(String user, Contact contact){
        UserContact userContact = storage.getUserContact(user);
        userContact.addContact(contact);
        storage.setUserContact(userContact, user);
    }

    public void deleteContact(String user, int contactID){
        UserContact userContact = storage.getUserContact(user);
        userContact.deleteContact(contactID);
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

    public String[] getFoldersNames(String user){
        /*
            to be implemented
        */
        // return storage.getUserData(user).getFoldersName();
        return null;
    }

}