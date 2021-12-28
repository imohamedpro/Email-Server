package university.project.MailBackend.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import university.project.MailBackend.Interfaces.Searchable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Contact implements Searchable {
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
    public int getId(){
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean contains(List<String> tokens, boolean filter) {
        for(String token: tokens){
            if(name.contains(token))
                return true;
            for(String username: usernames){
                if(username.contains(token))
                    return true;
            }
        }
        return false;
    }
}