
package university.project.MailBackend.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class UserContact {
    public HashMap<Integer, Contact> contacts;
    private int nextID;
    public UserContact(){
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

    public void addContact(Contact contact){
        if(contact.getId() < 0){
            contact.setId(nextID++);
        }
        contacts.put(contact.getId(), contact);
    }

}
