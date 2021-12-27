package university.project.MailBackend.Model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import university.project.MailBackend.Interfaces.Observable;
import university.project.MailBackend.Interfaces.Observer;

public class Folder implements Observer {
    public String name;
    public Set<Integer> emails;
    public int unreadCount;
    public List<String> filterTokens;

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
