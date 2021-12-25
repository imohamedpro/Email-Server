package university.project.MailBackend.Model;

import java.io.File;

public class Mail {
    private int id;
    private String body;
    private File[] Attachments;
    private String[] folder;
    private boolean isRead;
}
