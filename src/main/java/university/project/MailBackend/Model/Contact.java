package university.project.MailBackend.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashSet;

public class Contact {
    private String name;
    private HashSet<String> usernames;
    private int id;

    Contact(String name, int id){
        this.name = name;
        usernames = new HashSet<String>();
        this.id = id;
    }

    @JsonCreator
    public Contact(
            @JsonProperty("name") String name,
            @JsonProperty("usernames") HashSet<String> usernames,
            @JsonProperty("id") int id)
    {
        this.name = name;
        this.usernames = usernames;
        this.id = id;
    }
    public int getID(){
        return this.id;
    }
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getUsernames() {
        return new ArrayList<String>(this.usernames);
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