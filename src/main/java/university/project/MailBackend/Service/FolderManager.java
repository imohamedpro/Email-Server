package university.project.MailBackend.Service;

// import java.util.ArrayList;
import java.util.List;

import university.project.MailBackend.Model.Email;
import university.project.MailBackend.Model.Folder;

public class FolderManager {
    private StorageAdapter storage;
    FolderManager(StorageAdapter storageAdapter){
        this.storage = storageAdapter;
    }
    public void addFolder(String name, String[] filterTokens, String user){
        Folder folder = new Folder(name, List.of(filterTokens));
        storage.setFolder(user, folder);
    }
    public Folder getFolder(String name, String user){
        return storage.getFolder(user, name);
    }
    public Email[] getFolderContent(String name, String user){
        return storage.getFolderContent(user, name);
    }
}
