package university.project.MailBackend.Interfaces;

// import university.project.MailBackend.Model.Email;
// import university.project.MailBackend.Model.Folder;

public interface Observer {
    String getName();
    void notify(int change);
    //subscribe
    void addEmail(Observable email);
    //unsubscribe
    void removeEmail(Observable email);
}
