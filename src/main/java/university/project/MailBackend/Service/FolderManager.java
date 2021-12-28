package university.project.MailBackend.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
// import java.util.ArrayList;
import java.util.List;

import university.project.MailBackend.Model.Email;
import university.project.MailBackend.Model.EmailHeader;
import university.project.MailBackend.Model.Folder;

public class FolderManager {
    private StorageAdapter storage;
    private SortFactory sortFactory ;
    FolderManager(StorageAdapter storageAdapter, SortFactory sortFactory){
        this.storage = storageAdapter;
        this.sortFactory = sortFactory;
    }
    public void setFolder(int folderID, String folderName, String[] filterTokens, String user){
        Folder folder = new Folder(folderID, folderName, List.of(filterTokens));
        storage.setFolder(user, folder);
    }
    public Folder getFolder(int folderID, String user){
        return storage.getFolder(user, folderID);
    }
    private Email[] getFolderContent(int folderID, String user, String searchToken){
        return storage.getFolderContent(user, folderID, searchToken);
    }

    private Email[] sortFolder(EmailHeader[] emails, String sortBy, Boolean dirction){
        // Arrays.sort(emails, sortFactory.getSortType(sortBy));
        return null;
    }
    // private Email[] searcFolder(Email[] emails, String searchToken){
    //     LinkedList<Email> es = storage.get;
    //     return null;
    // }
    public Email[] loadFolder(int folderID, String sortBy, Boolean direction, String searchToken, String user){
        return null;
    }

    public String[] getFoldersNames(String user){
        return storage.getFoldersNames(user);
    }
}
