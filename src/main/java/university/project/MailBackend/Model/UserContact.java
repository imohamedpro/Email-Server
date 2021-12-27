package university.project.MailBackend.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class UserContact {
    public HashMap<Integer, Contact> contacts;
    private int nextID;

    UserContact(){
        contacts = new HashMap<Integer, Contact>();
        nextID = 0;
    }

    @JsonCreator
    public UserContact(
            @JsonProperty("contacts") HashMap<Integer, Contact> contacts,
            @JsonProperty("nextID") int nextID) {
        this.contacts = contacts;
        this.nextID = nextID;
    }

    public Contact getContact(int contactID) {
        return contacts.get(contactID);
    }

    public void addContact(Contact contact){
        //
    }
}
