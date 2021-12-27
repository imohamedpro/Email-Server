package university.project.MailBackend.Model.Filter;

import university.project.MailBackend.Model.Mail;

import java.util.ArrayList;

public class ToCriteria implements Criteria{
    private String criteria;

    public ToCriteria(String criteria) {
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
