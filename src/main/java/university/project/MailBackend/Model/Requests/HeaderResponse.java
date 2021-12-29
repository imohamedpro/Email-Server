package university.project.MailBackend.Model.Requests;

import university.project.MailBackend.Model.Email;
import university.project.MailBackend.Model.EmailHeader;

import java.util.Date;

public class HeaderResponse extends EmailHeader {
    public int id;
    public boolean isRead;

    public HeaderResponse(Email email){
        super(email.emailHeader.from, email.emailHeader.to, email.emailHeader.subject, email.emailHeader.date, email.emailHeader.priority);
        this.id = email.getID();
        this.isRead = email.isRead;
    }
}
