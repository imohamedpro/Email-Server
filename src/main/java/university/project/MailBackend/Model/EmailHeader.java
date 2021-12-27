package university.project.MailBackend.Model;

import java.util.Date;
import java.util.List;

import university.project.MailBackend.Interfaces.Searchable;

public class EmailHeader implements Searchable {
    public String from;
    public String[] to;
    public String subject;
    public Date date;
    public int priority;

    @Override
    public boolean contains(List<String> tokens, boolean filter) {
        for(String token: tokens){
            if(from.contains(token) || subject.contains(token)){
                return true;
            }
            if(!filter){
                for(String s: to){
                    if(s.contains(token)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
}
