
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
        if(contact.getID() < 0){
            contacts.put(nextID++, contact);
        }else{
            contacts.put(contact.getID(), contact);
        }
    }

}
