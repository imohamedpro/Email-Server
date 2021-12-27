package university.project.MailBackend.Model.Filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import university.project.MailBackend.Model.Mail;

import java.util.ArrayList;

public class ReadCriteria implements Criteria{
    private boolean isRead;

    @JsonCreator
    public ReadCriteria(@JsonProperty("isRead") boolean isRead) {
        this.isRead = isRead;
    }

    @Override
    public ArrayList<Mail> meetCriteria(ArrayList<Mail> mails) {
        ArrayList<Mail> criteriaMails = new ArrayList<>();
        for(Mail mail: mails){
            if(mail.getIsRead() == this.isRead){
                criteriaMails.add(mail);
            }
        }
        return criteriaMails;
    }
}
