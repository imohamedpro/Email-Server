package university.project.MailBackend.Interfaces;

import university.project.MailBackend.Model.UserContact;
import university.project.MailBackend.Model.UserData;
import university.project.MailBackend.Model.UserInfo;

public interface IStorage {
    UserInfo getUserInfo(String email);
    UserData getUserData(String email);
    UserContact getUserContact(String email);
    void setUserInfo(UserInfo info);
    void setUserData(UserData data);
    void setUserContact(UserContact contact);

}
