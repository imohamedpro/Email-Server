package university.project.MailBackend.Model;
import java.util.List;
import java.util.Map;
import java.util.Set;

import university.project.MailBackend.Interfaces.Observable;
import university.project.MailBackend.Interfaces.Observer;
import university.project.MailBackend.Interfaces.Searchable;

public class Email implements Observable, Searchable {
    public int id;
    public boolean isRead;
    public Set<String> folders;
    public EmailHeader emailHeader;
    public EmailBody emailBody;

    @Override
    public void markAsRead(Map<String, Observer> folders) { //map is used to save memory
        this.isRead = true;
        for(String s: this.folders){
            Observer o = folders.get(s);
            o.notify(-1);
        }
    }
    @Override
    public void markAsUnread(Map<String, Observer> folders) { //map is used to save memory
        this.isRead = false;
        for(String s: this.folders){
            Observer o = folders.get(s);
            o.notify(1);
        }
    }
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
