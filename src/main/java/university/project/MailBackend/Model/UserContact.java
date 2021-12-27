
package university.project.MailBackend.Model;

import java.util.HashMap;

public class UserContact {
    public HashMap<Integer, Contact> contacts;
    private int nextID;
    UserContact(){
        contacts = new HashMap<Integer, Contact>();
        nextID = 0;
    }

    public void addContact(Contact contact){
        if(contact.getID() < 0){
            contacts.put(nextID++, contact);
        }else{
            contacts.put(contact.getID(), contact);
        }
    }

}
