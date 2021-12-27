package university.project.MailBackend.Model.Filter;

import university.project.MailBackend.Model.Mail;

import java.util.ArrayList;

public class OrCriteria implements Criteria{
    private Criteria criteria;
    private Criteria otherCriteria;

    public OrCriteria(Criteria criteria, Criteria otherCriteria) {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public ArrayList<Mail> meetCriteria(ArrayList<Mail> mails) {
        ArrayList<Mail> firstCriteriaMails = criteria.meetCriteria(mails);
        ArrayList<Mail> secondCriteriaMails = otherCriteria.meetCriteria(mails);

        for(Mail mail: secondCriteriaMails){
            if(!firstCriteriaMails.contains(mail)){
                firstCriteriaMails.add(mail);
            }
        }
        return firstCriteriaMails;
    }
}
