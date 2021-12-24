package university.project.MailBackend.Model;

import java.util.ArrayList;

public class Contact {
    private String name;
    private ArrayList<String> usernames;

    Contact(String name){
        this.name = name;
        usernames = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getUsernames() {
        return this.usernames;
    }

    public void rename(String newName){
        this.name = newName;
    }

    public void addUsername(String username){
        this.usernames.add(username);
    }

    public void removeUsername(String username){
        this.usernames.remove(username);
    }
}
