package university.project.MailBackend.Service;

import university.project.MailBackend.Interfaces.IStorage;
import university.project.MailBackend.Model.Contact;
import university.project.MailBackend.Model.StorageAdapter;

import java.util.*;

public class ContactManager {
    private StorageAdapter storageAdapter;

    public ContactManager(StorageAdapter storageAdapter){
        this.storageAdapter = storageAdapter;
    }

    public int getNumberOfPages(String user, int contactsPerPage){
        List<Contact> contacts = storageAdapter.getContactsList(user);
        return contacts.size() / contactsPerPage + 1;
    }

    public List<Contact> getContactsList(String user, int pageNumber, int contactsPerPage, boolean sorted){
        List<Contact> contacts = storageAdapter.getContactsList(user);
        int startIndex = (pageNumber-1)*contactsPerPage;
        int stopIndex = pageNumber*contactsPerPage;
        if(stopIndex > contacts.size())
            stopIndex = contacts.size();
        if(sorted){
            contacts = Sort(contacts);
        }
        return contacts.subList(startIndex, stopIndex);
    }

    public Contact getContact(String user, int contactID){
        return storageAdapter.getContact(user, contactID);
    }

    public void deleteContact(String user, int contactID){
        storageAdapter.deleteContact(user, contactID);
    }

    public void addContact(String user, Contact contact){
        storageAdapter.setContact(user, contact);
    }

    public List<Contact> searchContact(String user, List<String> tokens, int pageNumber, int contactsPerPage, boolean sorted){
        List<Contact> contacts = storageAdapter.getContactsList(user);
        List<Contact> result = new ArrayList<>();
        for(Contact contact: contacts){
            if(contact.contains(tokens, true))
                result.add(contact);
        }
        int startIndex = (pageNumber-1)*contactsPerPage;
        int stopIndex = pageNumber*contactsPerPage;
        if(stopIndex > result.size())
            stopIndex = result.size();
        if(result.isEmpty())
            return new ArrayList<>();
        else
            if(sorted)
                result = Sort(result);
            return result.subList(startIndex, stopIndex);
    }

    private List<Contact> Sort(List<Contact> contacts){
        contacts.sort(Comparator.comparing(Contact::getName));
        return contacts;
    }
}
