package university.project.MailBackend.Model;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UserData{
    public Map<String, Folder> folders;
    public Map<Integer, Email> emails;
    private int nextEmailID;

    public UserData(){
        emails = new HashMap<Integer, Email>();
        folders = new HashMap<String, Folder>();
        nextEmailID = 0;
        folders.put("inbox", new Folder("inbox", new ArrayList<String>()));
        folders.put("sent", new Folder("sent", new ArrayList<String>()));
        folders.put("draft", new Folder("draft", new ArrayList<String>()));
        folders.put("trash", new Folder("trash", new ArrayList<String>()));
    }
    @JsonCreator
    public UserData(
            @JsonProperty("folders") Map<String, Folder> folders,
            @JsonProperty("emails") Map<Integer, Email> emails,
            @JsonProperty("nextEmailID") int nextEmailID) {
        this.folders = folders;
        this.emails = emails;
        this.nextEmailID = nextEmailID;
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
                folder = folders.get("draft");
                folder.addEmail(email);
                break;
            case "sent":
                folder = folders.get("draft");
                folder.removeEmail(email);
                folder = folders.get("sent");
                folder.addEmail(email);
                break;
            case "received":
                email.isRead = false;
                folder = folders.get("inbox");
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
            for(String folderName: email.folders){
                Folder folder = this.folders.get(folderName);
                folder.removeEmail(email);
            }
            Folder trash = folders.get("trash");
            email.deleteDate = new Date();
            trash.addEmail(email);
        }
    }
    public void deleteEmail(int emailID){
        if(emails.containsKey(emailID)){
            Folder trash = folders.get("trash");
            Email email = emails.remove(emailID);
            trash.removeEmail(email);
        }
    }
    public void moveEmail(int emailID, String folderName){
        Folder folder =  this.folders.get(folderName);
        Email email = this.emails.get(emailID);
        folder.addEmail(email);
    }

    public void restoreEmail(int emailID, String folderName){
        Folder folder =  this.folders.get(folderName);
        Email email = this.emails.get(emailID);
        email.deleteDate = null;
        folder.addEmail(email);
        folder = this.folders.get("trash");
        folder.removeEmail(email);
    }

    public void autoDelete(){
        Folder trash = folders.get("trash");
        for(int emailID: trash.emails){
            Email email = emails.get(emailID);
            if(TimeUnit.DAYS.convert(new Date().getTime() - email.deleteDate.getTime(), TimeUnit.MILLISECONDS) > 30){
                emails.remove(email.id);
                trash.removeEmail(email);
            }
        }
    }

    public void addFolder(Folder folder){
        this.folders.put(folder.name, folder);
    }
    public void deleteFolder(String name){
        Folder folder = this.folders.remove(name);
        for(int emailID: folder.emails){
            folder.removeEmail(this.emails.get(emailID));
        }
    }
    public Email[] getFolder(String name) {
        Folder folder = this.folders.get(name);
        LinkedList<Email> l = new LinkedList<Email>();
        for(int id: folder.emails){
            l.add(this.emails.get(id));
        }
        return l.toArray(new Email[0]);
    }

}
