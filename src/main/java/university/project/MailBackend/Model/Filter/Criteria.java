package university.project.MailBackend.Model.Filter;

import university.project.MailBackend.Model.Mail;

import java.util.ArrayList;

public interface Criteria {
    ArrayList<Mail> meetCriteria(ArrayList<Mail> mails);
}
