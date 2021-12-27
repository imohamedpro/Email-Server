package university.project.MailBackend.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MailHeader {
    private String from;
    private String[] to;
    private String subject;
    private int priority;
    private int nAttachments;

    @JsonCreator
    public MailHeader(
            @JsonProperty("from") String from,
            @JsonProperty("to") String[] to,
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

    public String getFrom() {
        return from;
    }

    public String[] getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public int getPriority() {
        return priority;
    }

    public int getnAttachments() {
        return nAttachments;
    }
}
