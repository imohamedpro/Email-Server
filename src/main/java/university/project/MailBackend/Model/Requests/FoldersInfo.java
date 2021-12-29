package university.project.MailBackend.Model.Requests;

import university.project.MailBackend.Model.Folder;

import java.util.ArrayList;

public class FoldersInfo {
    public ArrayList<String> folderNames;
    public ArrayList<Integer> unreadCount;

    FoldersInfo(Folder folder){
        this.folderNames = new ArrayList<>();
        this.unreadCount = new ArrayList<>();
    }
}
