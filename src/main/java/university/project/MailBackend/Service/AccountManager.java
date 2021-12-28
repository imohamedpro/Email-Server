package university.project.MailBackend.Service;

import university.project.MailBackend.Model.UserContact;
import university.project.MailBackend.Model.UserData;
import university.project.MailBackend.Model.UserInfo;

public class AccountManager {
    private final StorageAdapter storage;

    public AccountManager(StorageAdapter storageAdapter){
        this.storage = storageAdapter;
    }

    public boolean signUp(UserInfo userInfo){
        if(storage.getUserInfo(userInfo.getEmail()) != null){
            UserData data = new UserData();
            UserContact contact = new UserContact();
            storage.createAcount(userInfo, data, contact);
            return true;
        }
        return false;
    }

    public boolean signIn(UserInfo userInfo){
        return storage.getUserInfo(userInfo.getEmail()).authenticate(userInfo);
    }

    public String[] getFoldersNames(String user){
        return storage.getFoldersNames(user);
    }
}
