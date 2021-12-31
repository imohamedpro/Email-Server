package TestUnits.Model;

import org.junit.Test;
import org.mockito.Mock;
import university.project.MailBackend.Model.Contact;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TestContact {
    @Mock
    String nameMock;
    @Mock
    HashSet<String> usernamesMock;
    @Mock
    int idMock;


    /*
        testing contains() method in Contact class
     */
    @Test
    public void test() {
        assertNull(nameMock);
        assertNull(usernamesMock);
        List<String> searchItem = new ArrayList<String>();
//        assertNull(idMock);
        searchItem.add("Mohammed");
        searchItem.add("Karim");
        Contact contact = new Contact(nameMock, usernamesMock, idMock);
        boolean check = searchItem.contains("Karim");
        assertTrue(check);
        searchItem.add("28-12-2021");
        check = searchItem.contains("28-12-2021");
        assertTrue(check);

        check = searchItem.contains("29-12-2021");
        assertFalse(check);
    }
}

