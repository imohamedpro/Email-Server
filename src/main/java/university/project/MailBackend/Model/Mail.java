package university.project.MailBackend.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.util.ArrayList;

public class Mail {
    private int id;
    private MailHeader header;
    private String body;
    private File[] Attachments;
    private ArrayList<String> folders;
    private boolean isRead;

    @JsonCreator
    public Mail(
            @JsonProperty("id") int id,
            @JsonProperty("header") MailHeader header,
            @JsonProperty("body") String body,
            @JsonProperty("attachments") File[] attachments,
            @JsonProperty("folders") ArrayList<String> folders,
            @JsonProperty("isRead") boolean isRead) {
        this.id = id;
        this.header = header;
        this.body = body;
        Attachments = attachments;
        this.folders = folders;
        this.isRead = isRead;
    }

    public int getId() {
        return id;
    }

    public MailHeader getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public File[] getAttachments() {
        return Attachments;
    }

    public ArrayList<String> getFolders() {
        return folders;
    }

    public void setId(int id){
        this.id = id;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead){
        this.isRead = isRead;
    }

    public void addToFolder(String folder){
            folders.add(folder);
    }
}
