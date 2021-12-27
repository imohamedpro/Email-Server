package university.project.MailBackend.Interfaces;

import java.util.Map;

// import university.project.MailBackend.Model.Folder;
// import university.project.MailBackend.Model.StorageFacade;

public interface Observable {
    int getID();
    void markAsRead(Map<String, Observer> folders);
    void markAsUnread(Map<String, Observer> folders);
    void addFolder(Observer folder);
    void removeFolder(Observer folder);
    // void deleted(Map<String, Observer> folders);
}
