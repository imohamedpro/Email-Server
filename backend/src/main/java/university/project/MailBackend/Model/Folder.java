package university.project.MailBackend.Model;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import university.project.MailBackend.Interfaces.Observable;
import university.project.MailBackend.Interfaces.Observer;

public class Folder implements Observer {
    public int id;
    public String name;
    public HashSet<Integer> emails;
    public int unreadCount;
    public List<String> filterTokens;

    public Folder(int id, String name, List<String> filterTokens){
        this.id = id;
        this. name = name;
        this.filterTokens = filterTokens;
        unreadCount = 0;
        emails = new HashSet<>();
    }

    @JsonCreator
    public Folder(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("emails") HashSet<Integer> emails,
            @JsonProperty("unreadCount") int unreadCount,
            @JsonProperty("filterTokens") List<String> filterTokens) {
        this.id = id;
        this.name = name;
        this.emails = emails;
        this.unreadCount = unreadCount;
        this.filterTokens = filterTokens;
    }

    @Override
    public void notify(int change) {
        this.unreadCount += change;
        
    }
    @Override
    public void addEmail(Observable email) {
        if(!this.emails.contains(email.getID())){
            emails.add(email.getID());
            email.addFolder(this);
        }
        
    }
    @Override
    public void removeEmail(Observable email) {
        if(this.emails.contains(email.getID())){
            emails.remove(email.getID());
            email.removeFolder(this);
        }
    }
    public void moveToTrash(int emailID){
        if(this.emails.contains(emailID)){
            emails.remove(emailID);
        }
    }

    public void restoreEmail(int emailID){
        emails.add(emailID);
    }
    @Override
    public int getID() {
        return this.id;
    }

    public void filter(Email email){
        if(email.contains(this.filterTokens, true)){
            this.addEmail(email);
        }
    }

    public List<Integer> search(String str, Map<Integer, Email> map){
        List<Integer> emailIDs = new LinkedList<Integer>();
        for(Integer emailID: this.emails){
            Email email = map.get(emailID);
            List<String> l = new LinkedList<String>();
            l.add(str);
            if(email.contains(l, false)){
                emailIDs.add(emailID);
            }
        }
        return emailIDs;
    }
}
