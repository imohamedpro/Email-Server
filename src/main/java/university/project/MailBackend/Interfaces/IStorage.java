package university.project.MailBackend.Interfaces;

import university.project.MailBackend.Model.UserContact;
import university.project.MailBackend.Model.UserData;
import university.project.MailBackend.Model.UserInfo;

public interface IStorage {
    UserInfo getUserInfo(String user);
    UserData getUserData(String user);
    UserContact getUserContact(String user);
    void setUserInfo(UserInfo info);
    void setUserData(UserData data, String user);
    void setUserContact(UserContact contact, String user);

}
