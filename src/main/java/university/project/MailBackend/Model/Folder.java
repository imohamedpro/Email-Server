package university.project.MailBackend.Model;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import university.project.MailBackend.Interfaces.Observable;
import university.project.MailBackend.Interfaces.Observer;

public class Folder implements Observer {
    public String name;
    public ArrayList<Integer> emails;
    public int unreadCount;
    public List<String> filterTokens;

    Folder(String name, List<String> filterTokens){
        this. name = name;
        this.filterTokens = filterTokens;
        unreadCount = 0;
        emails = new ArrayList<>();
    }

    @JsonCreator
    public Folder(
            @JsonProperty("name") String name,
            @JsonProperty("emails") ArrayList<Integer> emails,
            @JsonProperty("unreadCount") int unreadCount,
            @JsonProperty("filterTokens") List<String> filterTokens) {
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
    @Override
    public String getName() {
        return this.name;
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
