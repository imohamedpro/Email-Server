package university.project.MailBackend.Model.Filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import university.project.MailBackend.Model.Mail;

import java.util.ArrayList;

public class SubjectCriteria implements Criteria{
    private String criteria;

    @JsonCreator
    public SubjectCriteria(@JsonProperty("criteria") String criteria) {
        this.criteria = criteria;
    }

    @Override
    public ArrayList<Mail> meetCriteria(ArrayList<Mail> mails) {
        ArrayList<Mail> criteriaMails = new ArrayList<>();
        for(Mail mail: mails){
            if(mail.getHeader().getSubject().contains(criteria)){
                criteriaMails.add(mail);
            }
        }
        return criteriaMails;
    }
}
