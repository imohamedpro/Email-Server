package TestUnits.Service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import university.project.MailBackend.Model.Contact;
import university.project.MailBackend.Model.UserContact;
import university.project.MailBackend.Model.UserData;
import university.project.MailBackend.Model.UserInfo;
import university.project.MailBackend.Service.AccountManager;
import university.project.MailBackend.Service.ContactManager;
import university.project.MailBackend.Service.StorageAdapter;

import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestContactManager {
    @Mock
    StorageAdapter storageMock;
    @Mock
    ContactManager contactManagerMock;
    String name1 = "Mohamed";   int id1 = 1;
    String name2 = "Youhanna";     int id2 = 2;
    String name3 = "Karim";     int id3 = 3;
    HashSet<String> usernames1 = new HashSet<String>();
    HashSet<String> usernames2 = new HashSet<String>();
    HashSet<String> usernames3 = new HashSet<String>();
    HashSet<String> usernames4 = new HashSet<String>();
    HashSet<String> usernames5 = new HashSet<String>();

    @Mock Contact contact1;
    @Mock Contact contact2;
    @Mock Contact contact3;
    @Mock Contact contact4;
    @Mock Contact contact5;

    private List<Contact> Sort(List<Contact> contacts){
        contacts.sort(Comparator.comparing(Contact::getName, String::compareToIgnoreCase));
        return contacts;
    }

    /*
        This Sort method is used in ContactManager class
        it's assumed to re order a list of Contacts alphabetically,
        and it was tested to be true.
     */
    @Test
    public void testSort() {
//        contactList is the tested one
        List<Contact> contactList = new ArrayList<>();
//        contactList2 is the true one
        List<Contact> contactList2 = new ArrayList<>();

        usernames1.add("iMo@bla.com");
        usernames2.add("youyou@bla.com");
        usernames3.add("z1912@bla.com");
        contact1 = new Contact(name1, usernames1, id1);
        contact2 = new Contact(name2, usernames2, id2);
        contact3 = new Contact(name3, usernames3, id3);

        contactList.add(contact1);
        contactList.add(contact2);
        contactList.add(contact3);

        List<Contact> check = Sort(contactList);

        contactList2.add(contact3);
        contactList2.add(contact1);
        contactList2.add(contact2);
        assertEquals(contactList2, check);
    }

    /*
        Trying to test getNumberOfPages() method
        not successful yet!
     */
    @Test
    public void testGetNumberOfPages() {
        String user = "Youssef";
        int contactsPerPage = 2;
        List<Contact> contactList = new ArrayList<Contact>();
        usernames1.add("joe.com");
        usernames2.add("youyou@bla.com");
        usernames3.add("Usef@bla.com");
        usernames4.add("me@bla.com");
        usernames5.add("meAgain@bla.com");

        contact1 = new Contact(name1, usernames1, id1);
        contact2 = new Contact(name2, usernames2, id2);
        contact3 = new Contact(name3, usernames3, id3);
        contact4 = new Contact("name4", usernames4, 4);
        contact5 = new Contact("name5", usernames5, 5);

        contactList.add(contact1);  contactList.add(contact2); contactList.add(contact3);
        contactList.add(contact4);  contactList.add(contact5);
        when(storageMock.getContactsList(user)).thenReturn((ArrayList<Contact>) contactList);
        assertEquals(contactList, storageMock.getContactsList(user));
//        int num = contactManagerMock.getNumberOfPages(user, 2);
//        assertEquals(2, num);
    }
}
