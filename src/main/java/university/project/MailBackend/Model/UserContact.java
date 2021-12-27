package university.project.MailBackend.Model;

import java.util.HashMap;

public class UserContact {
    public HashMap<Integer, Contact> contacts;
    private int nextID;
    UserContact(){
        contacts = new HashMap<Integer, Contact>();
        nextID = 0;
    }

}
