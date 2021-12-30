package university.project.MailBackend.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import university.project.MailBackend.Model.Email;
// import university.project.MailBackend.Model.EmailHeader;
import university.project.MailBackend.Model.Folder;
import university.project.MailBackend.Model.Requests.FoldersInfo;
import university.project.MailBackend.Model.Requests.HeaderResponse;

public class FolderManager {
    private StorageAdapter storage;
    private SortFactory sortFactory ;
    public FolderManager(StorageAdapter storageAdapter, SortFactory sortFactory){
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
    private List<HeaderResponse> getFolderContent(int folderID, String user, String searchToken){
        Email[] emails = storage.getFolderContent(user, folderID, searchToken);
        List<HeaderResponse> headers = new ArrayList<HeaderResponse>();
        for(int i = 0; i < emails.length; i++){
            headers.add(new HeaderResponse(emails[i]));
        }
        return headers;
    }
    // private void reverse(Object[] arr){
    //     for(int i = 0; i < arr.length/2; i++){
    //         Object temp = arr[i];
    //         arr[i] = arr[arr.length - 1 - i];
    //         arr[arr.length - 1 - i] = temp;
    //     }
    // }
    // private slice
    // private Email[] sortFolder(EmailHeader[] emails, String sortBy, Boolean reverse){
    //     Arrays.sort(emails, sortFactory.getSortType(sortBy));
    // }
    // // private Email[] searcFolder(Email[] emails, String searchToken){
    // //     LinkedList<Email> es = storage.get;
    // //     return null;
    // // }
    public List<HeaderResponse> loadFolder(int folderID, String sortBy, boolean reverse, String searchToken, int pageNumber, int emailsPerPage, String user){
        List<HeaderResponse> headers = this.getFolderContent(folderID, user, searchToken);
        Collections.sort(headers, this.sortFactory.getSortType(sortBy));
        int l, h;
        if(reverse){
            l = headers.size() - pageNumber * emailsPerPage;
            h = headers.size() - (pageNumber - 1) * emailsPerPage;
            if(l < 0)                   l = 0;
            headers = headers.subList(l, h);
            Collections.reverse(headers);


        }else{
            l = (pageNumber - 1) * emailsPerPage;
            h = (pageNumber) * emailsPerPage;
            if(h > headers.size())      h = headers.size();
            headers = headers.subList(l, h);
        }
        return headers;
    }

    public int getNumberOfPages(int folderID, int emailPerPage, String user, String searchToken){
        int size;
        if(searchToken.isBlank())
            size = storage.getFolder(user, folderID).emails.size();
        else
            size = getFolderContent(folderID, user, searchToken).size();
        return (int)Math.ceil(size / (double)emailPerPage);
    }

    public FoldersInfo getFoldersInfo(String user){
        return new FoldersInfo(storage.getFolders(user));
    }

    public void rename(int folderID, String newName, String user){
        Folder folder = getFolder(folderID, user);
        folder.name = newName;
        storage.setFolder(user, folder);
    }

    public void editTokens(int folderID, List<String> tokens, String user){
        Folder folder = getFolder(folderID, user);
        folder.filterTokens = tokens;
        storage.setFolder(user, folder);
    }

    public List<String> getFilterTokens(int folderID, String user){
        return getFolder(folderID, user).filterTokens;
    }
}
