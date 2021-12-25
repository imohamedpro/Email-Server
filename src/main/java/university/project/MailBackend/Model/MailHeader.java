package university.project.MailBackend.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MailHeader {
    private Contact from;
    private Contact[] to;
    private String subject;
    private int priority;
    private int nAttachments;

    @JsonCreator
    public MailHeader(
            @JsonProperty("from") Contact from,
            @JsonProperty("to") Contact[] to,
            @JsonProperty("subject") String subject,
            @JsonProperty("priority") int priority,
            @JsonProperty("nAttachments") int nAttachments)
    {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.priority = priority;
        this.nAttachments = nAttachments;
    }

}
