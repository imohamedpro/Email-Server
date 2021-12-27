package university.project.MailBackend.Model.Filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import university.project.MailBackend.Model.Mail;

import java.util.ArrayList;

public class ToCriteria implements Criteria{
    private String criteria;

    @JsonCreator
    public ToCriteria(@JsonProperty("criteria") String criteria) {
        this.criteria = criteria;
    }

    @Override
    public ArrayList<Mail> meetCriteria(ArrayList<Mail> mails) {
        ArrayList<Mail> criteriaMails = new ArrayList<>();
        for(Mail mail: mails){
            if(mail.getHeader().getTo().contains(criteria)){
                criteriaMails.add(mail);
            }
        }
        return criteriaMails;
    }
}
