package university.project.MailBackend.Model.Requests;

import java.util.List;

public class ContactSearch {
    public String user;
    public List<String> tokens;
    public  int pageNumber;
    public  int perPage;
    public boolean sorted;
}
