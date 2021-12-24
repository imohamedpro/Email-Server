package university.project.MailBackend.Model;

public class MailHeader {
    private Contact from;
    private Contact[] to;
    private String subject;
    private int priority;
    private int id;
    private int nAttachments;
    private String[] folder;

    public MailHeader(Contact from, Contact[] to, String subject, int priority, int id, int nAttachments, String[] folder) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.priority = priority;
        this.id = id;
        this.nAttachments = nAttachments;
        this.folder = folder;
    }
}
