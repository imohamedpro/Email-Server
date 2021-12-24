package university.project.MailBackend.Model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private String username;
    private int userID;
    private ArrayList<String> folders;
    private ArrayList<Contact> contacts;


    public User(String firstName, String lastName, String username, int userID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.userID = userID;
        this.folders = new ArrayList<String>(List.of("Inbox", "Sent", "Draft", "Trash"));
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public int getUserID() {
        return userID;
    }

    public ArrayList<String> getFolders() {
        return folders;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void addFolder(String folderName){
        folders.add(folderName);
    }

    public void deleteFolder(String folderName){
        folders.remove(folderName);
    }

    public void addContact(Contact contact){
        contacts.add(contact);
    }

    public void removeContact(Contact contact){
        contacts.remove(contact);
    }
}
