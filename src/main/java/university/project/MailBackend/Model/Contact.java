package university.project.MailBackend.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Contact {
    private String name;
    private ArrayList<String> usernames;

    Contact(String name){
        this.name = name;
        usernames = new ArrayList<>();
    }

    @JsonCreator
    public Contact(
            @JsonProperty("name") String name,
            @JsonProperty("usernames") ArrayList<String> usernames)
    {
        this.name = name;
        this.usernames = usernames;
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
