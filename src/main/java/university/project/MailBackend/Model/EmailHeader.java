package university.project.MailBackend.Model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import university.project.MailBackend.Interfaces.Searchable;

public class EmailHeader implements Searchable {
    public String from;
    public String[] to;
    public String subject;
    public Date date;
    public int priority;

    @JsonCreator
    public EmailHeader(
            @JsonProperty("from") String from,
            @JsonProperty("to") String[] to,
            @JsonProperty("subject") String subject,
            @JsonProperty("date") Date date,
            @JsonProperty("priority") int priority) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.date = date;
        this.priority = priority;
    }

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
