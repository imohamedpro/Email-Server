package university.project.MailBackend.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private String username;
    private int userID;
    private ArrayList<String> folders;
    private ArrayList<Contact> contacts;


    public User(String firstName, String lastName, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.folders = new ArrayList<String>(List.of("Inbox", "Sent", "Draft", "Trash"));
        this.contacts = new ArrayList<>();
    }

    @JsonCreator
    public User(
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("username") String username,
            @JsonProperty("userID") int userID,
            @JsonProperty("folders") ArrayList<String> folders,
            @JsonProperty("contacts") ArrayList<Contact> contacts)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.userID = userID;
        this.folders = folders;
        this.contacts = contacts;
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

    public void setUserID(int id){
        this.userID = id;
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

    public void removeContact(int contactID){
        contacts.remove(contactID);
    }

    public void editContact(Contact newContact, int contactID){
        if(contactID < contacts.size()){
            contacts.remove(contactID);
        }
        contacts.add(newContact);
    }
}
