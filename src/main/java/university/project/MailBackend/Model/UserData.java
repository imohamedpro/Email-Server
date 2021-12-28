package university.project.MailBackend.Model;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UserData{
    public Map<Integer, Folder> folders;
    public Map<Integer, Email> emails;
    private int nextEmailID;
    private int nextFolderID;

    public UserData(){
        emails = new HashMap<Integer, Email>();
        folders = new HashMap<Integer, Folder>();
        nextEmailID = 0;
        folders.put(0, new Folder(0, "inbox", new ArrayList<String>()));
        folders.put(1, new Folder(1, "sent", new ArrayList<String>()));
        folders.put(2, new Folder(2, "draft", new ArrayList<String>()));
        folders.put(3, new Folder(3, "trash", new ArrayList<String>()));
        nextFolderID = folders.size();
    }
    @JsonCreator
    public UserData(
            @JsonProperty("folders") Map<Integer, Folder> folders,
            @JsonProperty("emails") Map<Integer, Email> emails,
            @JsonProperty("nextEmailID") int nextEmailID,
            @JsonProperty("nextFolderID") int nextFolderID) {
        this.folders = folders;
        this.emails = emails;
        this.nextEmailID = nextEmailID;
        this.nextFolderID = nextFolderID;
    }

    public Email readEmail(int emailID){
        Email e = emails.get(emailID);
        e.markAsRead(folders);
        return e;
    }
    public void addEmail(Email email, String type){
        if(email.id < 0){
            email.id = nextEmailID++;
        }
        emails.put(email.id, email);
        Folder folder;
        switch (type.toLowerCase()){
            case "draft":
                folder = folders.get(2);    // draft
                folder.addEmail(email);
                break;
            case "sent":
                folder = folders.get(2);
                folder.removeEmail(email);
                folder = folders.get(1);    //sent
                folder.addEmail(email);
                break;
            case "received":
                email.isRead = false;
                folder = folders.get(0);    //inbox
                folder.addEmail(email);
                for(Folder f: folders.values()){
                    f.filter(email);        // default folders doesn't have filter tokens(null object ?)
                }
                break;
        }
    }
    public void markAsRead(int emailID){
        Email e = this.emails.get(emailID);
        e.markAsRead(folders);
    }

    public void markAsUnread(int emailID){
        Email e = this.emails.get(emailID);
        e.markAsUnread(folders);
    }
    public void moveToTrash(Integer emailID){
        if(emails.containsKey(emailID)){
            Email email = emails.get(emailID);
            for(int folderID: email.folders){
                Folder folder = this.folders.get(folderID);
                folder.moveToTrash(emailID);
            }
            Folder trash = folders.get(3);      //trash
            email.deleteDate = new Date();
            trash.restoreEmail(emailID); /// dunno
        }
    }
    public void restoreEmail(int emailID){
        Email email = this.emails.get(emailID);
        for(Integer folderID: email.folders){
            Folder folder = this.folders.get(folderID);
            folder.restoreEmail(emailID);
        }
        email.deleteDate = null;
        this.folders.get(3).moveToTrash(emailID); ///dunno
    }

    public void deleteEmail(int emailID){
        if(emails.containsKey(emailID)){
            Folder trash = folders.get(3);
            Email email = emails.remove(emailID);
            trash.removeEmail(email);
        }
    }
    public void moveEmail(int emailID, Integer folderID){
        Folder folder =  this.folders.get(folderID);
        Email email = this.emails.get(emailID);
        folder.addEmail(email);
    }



    public void autoDelete(){
        Folder trash = folders.get(3); // trash
        for(int emailID: trash.emails){
            Email email = emails.get(emailID);
            if(TimeUnit.DAYS.convert(new Date().getTime() - email.deleteDate.getTime(), TimeUnit.MILLISECONDS) > 30){
                emails.remove(email.id);
                trash.removeEmail(email);
            }
        }
    }

    public void addFolder(Folder folder){
        if(folder.id < 0){
            folder.id = nextFolderID++;
        }
        this.folders.put(folder.id, folder);
    }
    public void deleteFolder(int folderID){
        Folder folder = this.folders.remove(folderID);
        for(int emailID: folder.emails){
            folder.removeEmail(this.emails.get(emailID));
        }
    }
    public Email[] getFolderContent(int folderID, String searchToken) {
        Folder folder = this.folders.get(folderID);
        LinkedList<Email> l = new LinkedList<Email>();
        for(int id: folder.emails){
            Email e = this.emails.get(id);
            if(e.contains(List.of(searchToken), false)){
                l.add(this.emails.get(id));
            }
        }
        return l.toArray(new Email[0]);
    }

    public Folder[] getFoldersName(){
        return folders.values().toArray(new Folder[0]);
    }
}
