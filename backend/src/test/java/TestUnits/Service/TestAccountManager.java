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
import university.project.MailBackend.Service.StorageAdapter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestAccountManager {
    @Mock
    StorageAdapter storageMock;
//    TestAccountManager(StorageAdapter storageAdapterMock) {
//        this.storageMock = storageAdapterMock;
//    }
    @Mock
    UserInfo userInfoMock;
    @Mock UserData userDataMock;
    @Mock UserContact userContactMock;

    /*
        testSignIn tests that if it is called with a userInfo with no email
        then info == null
        then it will return false.
     */
    @Test
    public void testSignIn( ) {
        AccountManager accountManager = new AccountManager(storageMock);
        when(storageMock.getUserInfo(userInfoMock.getEmail())).thenReturn(null);
        boolean check = accountManager.signIn(userInfoMock);
//        User info for userInfoMock will be null.
        assertFalse(check);
        System.out.println(userInfoMock);
    }

    /*
        testSignUp is not ready yet!
     */
    @Test
    public void testSignUp( ) {
        AccountManager accountManager = new AccountManager(storageMock);
        verifyNoInteractions(storageMock);
//        boolean check = accountManager.signIn(userInfoMock);
////        User info for userInfoMock will be null.
//
//        verify(storageMock).createAcount(userInfoMock,userDataMock,userContactMock);
//        System.out.println(userInfoMock);
    }
}

