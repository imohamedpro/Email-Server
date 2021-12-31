package university.project.MailBackend.Model.Requests;

import university.project.MailBackend.Model.Folder;

import java.util.ArrayList;

public class FoldersInfo {
    public ArrayList<String> folderNames;
    public ArrayList<Integer> folderIDs;
    public ArrayList<Integer> unreadCount;

    public FoldersInfo(Folder[] folders){
        this.folderNames = new ArrayList<>();
        this.folderIDs = new ArrayList<>();
        this.unreadCount = new ArrayList<>();
        for(Folder folder: folders){
            folderNames.add(folder.name);
            folderIDs.add(folder.id);
            unreadCount.add(folder.unreadCount);
        }
    }
}
