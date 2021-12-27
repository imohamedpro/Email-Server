package university.project.MailBackend.Model;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import university.project.MailBackend.Interfaces.Searchable;

public class EmailBody implements Searchable{
    public String body;
    public List<File> attachments;

    @JsonCreator
    public EmailBody(
            @JsonProperty("body") String body,
            @JsonProperty("attachments") List<File> attachments) {
        this.body = body;
        this.attachments = attachments;
    }

    @Override
    public boolean contains(List<String> tokens, boolean filter) {
        for(String token: tokens){
            if(body.contains(token)){
                return true;
            }
            for(File f: attachments){
                if(f.getName().contains(token)){
                    return true;
                }
            }
        }
        return false;
    }
}
