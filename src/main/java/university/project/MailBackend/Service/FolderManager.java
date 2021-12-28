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
    public void setFolder(String name, String[] filterTokens, String user){
        Folder folder = new Folder(name, List.of(filterTokens));
        storage.setFolder(user, folder);
    }
    public Folder getFolder(String name, String user){
        return storage.getFolder(user, name);
    }
    private Email[] getFolderContent(String name, String user, String searchToken){
        return storage.getFolderContent(user, name, searchToken);
    }

    private Email[] sortFolder(EmailHeader[] emails, String sortBy, Boolean dirction){
        Arrays.sort(emails, sortFactory.getSortType(sortBy));
        return null;
    }
    // private Email[] searcFolder(Email[] emails, String searchToken){
    //     LinkedList<Email> es = storage.get;
    //     return null;
    // }
    public Email[] loadFolder(String name, String sortBy, Boolean direction, String searchToken, String user){
        return null;
    }

    public String[] getFoldersNames(String user){
        return storage.getFoldersNames(user);
    }
}
