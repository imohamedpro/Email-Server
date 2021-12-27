package university.project.MailBackend.Model;

import java.util.Map;

public class UserData {
    public Map<String, Folder> folders;
    public Map<Integer, Email> emails;
    private int nextEmailID;

    public void addEmail(Email email, String type){
        if(email.id < 0){
            email.id = nextEmailID++;
        }
        emails.put(email.id, email);
        Folder folder;
        switch (type.toLowerCase()){
            case "draft":
                folder = folders.get(type.toLowerCase());
                folder.addEmail(email);
                break;
            case "sent":
                folder = folders.get("draft");
                folder.removeEmail(email);
                folder = folders.get("sent");
                folder.addEmail(email);
                break;
            case "received":
                folder = folders.get("inbox");
                folder.addEmail(email);
                for(Folder f: folders.values()){
                    f.filter(email);        // default folders doesn't have filter tokens(null object ?)
                }
                break;
        }
    }

    public void deleteEmail(Integer emailID){
        if(emails.containsKey(emailID)){
            Email email = emails.get(emailID);
            for(String folderName: email.folders){
                Folder folder = this.folders.get(folderName);
                folder.removeEmail(email);
            }
            Folder trash = folders.get("trash");
            trash.addEmail(email);
        }
    }
}
