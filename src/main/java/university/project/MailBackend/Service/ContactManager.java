package university.project.MailBackend.Service;

import university.project.MailBackend.Model.Contact;

import java.util.*;

public class ContactManager {
    private final StorageAdapter storage;

    public ContactManager(StorageAdapter storageAdapter){
        this.storage = storageAdapter;
    }

    public int getNumberOfPages(String user, int contactsPerPage, List<String> tokens){
        int size = 0;
        if(tokens.size() == 0){
            size = storage.getContactsList(user).size();
        }else{
            for(Contact contact: storage.getContactsList(user)){
                if(contact.contains(tokens, true))
                    size++;
            }
        }
        return (int)Math.ceil(size / (double)contactsPerPage);
    }

    public List<Contact> getContactsList(String user, int pageNumber, int contactsPerPage, boolean sorted){
        List<Contact> contacts = storage.getContactsList(user);
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
        return storage.getContact(user, contactID);
    }

    public void deleteContact(String user, int contactID){
        storage.deleteContact(user, contactID);
    }

    public void addContact(String user, Contact contact){
        storage.setContact(user, contact);
    }

    public List<Contact> searchContact(String user, List<String> tokens, int pageNumber, int contactsPerPage, boolean sorted){
        if(tokens.isEmpty()){
            return getContactsList(user, pageNumber, contactsPerPage, sorted);
        }
        List<Contact> contacts = storage.getContactsList(user);
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

    protected List<Contact> Sort(List<Contact> contacts){
        contacts.sort(Comparator.comparing(Contact::getName, String::compareToIgnoreCase));
        return contacts;
    }
}
