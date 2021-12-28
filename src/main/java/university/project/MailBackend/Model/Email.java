package university.project.MailBackend.Model;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import university.project.MailBackend.Interfaces.Observable;
import university.project.MailBackend.Interfaces.Observer;
import university.project.MailBackend.Interfaces.Searchable;

public class Email implements Observable, Searchable {
    public int id;
    public boolean isRead;
    public Set<String> folders;
    public EmailHeader emailHeader;
    public EmailBody emailBody;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    public  Date deleteDate;

    @JsonCreator
    public Email(
            @JsonProperty("id") int id,
            @JsonProperty("isRead") boolean isRead,
            @JsonProperty("folders") Set<String> folders,
            @JsonProperty("emailHeader") EmailHeader emailHeader,
            @JsonProperty("emailBody") EmailBody emailBody,
            @JsonProperty("deleteDate") Date deleteDate) {
        this.id = id;
        this.isRead = isRead;
        this.folders = folders;
        this.emailHeader = emailHeader;
        this.emailBody = emailBody;
        this.deleteDate = deleteDate;
    }
    public Email(){
        this.id = -1;
        this.isRead = true;
        this.folders = new HashSet<String>();
    }
    public Email(Email e){
        this.id = -1;
        this.isRead = true;
        this.folders = new HashSet<String>();
        this.emailHeader = e.emailHeader;
        this.emailBody = e.emailBody;
    }

    @Override
    public void markAsRead(Map<String, Folder> folders) { //map is used to save memory
        if(!this.isRead){
            this.isRead = true;
            for(String s: this.folders){
                Observer o = folders.get(s);
                o.notify(-1);
            }
        }
    }
    @Override
    public void markAsUnread(Map<String, Folder> folders) { //map is used to save memory
        if(this.isRead){
            this.isRead = false;
            for(String s: this.folders){
                Observer o = folders.get(s);
                o.notify(1);
            }
        }
    }

    /*
        might be bugged
    */
    @Override
    public void addFolder(Observer folder) {
        if(!this.folders.contains(folder.getName())){
            this.folders.add(folder.getName());
            if(!this.isRead){
                folder.notify(1);
            }
        }
        
    }
    @Override
    public void removeFolder(Observer folder) {
        if(this.folders.contains(folder.getName())){
            this.folders.remove(folder.getName());
            if(!this.isRead){
                folder.notify(-1);
            }
        }
    }
    @Override
    public int getID() {
        return this.id;
    }
    @Override
    public boolean contains(List<String> tokens, boolean filter) {
        return emailHeader.contains(tokens, filter) || emailBody.contains(tokens, filter);
    }
    // @Override
    // public void deleted(Map<String, Folder> folders) {
    //     for(String folderName: this.folders){

    //     }
        
    // }
}
