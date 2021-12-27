package university.project.MailBackend.Service;

import university.project.MailBackend.Model.Contact;
import university.project.MailBackend.Model.Filter.AndCriteria;
import university.project.MailBackend.Model.Filter.Criteria;
import university.project.MailBackend.Model.Filter.CriteriaInfo;
import university.project.MailBackend.Model.Mail;
import university.project.MailBackend.Model.MailFolder;
import university.project.MailBackend.Model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MailService {
    private ArrayList<User> users = new ArrayList<>();
    private final FileService fileService = new FileService();
    private int nUsers = 0;

    public MailService() throws IOException {
        File usersDatabase = new File("Database/UsersDatabase.json");
        fileService.createDirectory("Database/");
        if(usersDatabase.isFile()){
            this.users = (ArrayList<User>) fileService.readFile("Database/UsersDatabase.json", User.class, true);
            this.nUsers = this.users.get(users.size()-1).getUserID()+1;
        }else{
            if(usersDatabase.createNewFile()){
                System.out.println("Users Database created");
            }else System.out.println("Error! Users Database cannot be created!");
        }
    }

    public boolean addNewUser(User user){
        if(!isUsernameExists(user.getUsername())){
            user.setUserID(nUsers++);
            users.add(user);
            updateUsersDatabase();
            String path = "Database/" + user.getUserID() + "/";
            fileService.createDirectory(path);
            int id = user.getUserID();
            ArrayList<Mail> mails = new ArrayList<>();
            path = path + "Mails.json";
            fileService.writeFile(path, mails);
            for(String folderName: user.getFolders()){
                MailFolder mailFolder = new MailFolder(folderName);
                path = buildMailFolderPath(folderName, id);
                fileService.writeFile(path, mailFolder);
            }
            return true;
        }
        return false;
    }

    public void sendMail(Mail mail){
        User fromUser = getUserByUsername(mail.getHeader().getFrom());
        assert fromUser != null;
        addMail(mail, fromUser.getUserID(), false);
        for(String username: mail.getHeader().getTo()){
            User user = getUserByUsername(username);
            if(user != null){
                addMail(mail, user.getUserID(), true);
            }
        }
    }

    private void addMail(Mail mail, int userID, boolean isReceiver){
        ArrayList<Mail> mails = getAllMails(userID);
        int mailID;
        if(mails.isEmpty()){
            mailID  = 0;
        }else{
            mailID = mails.get(mails.size()-1).getId()+1;
        }
        mail.setId(mailID);
        mails.add(mail);
        fileService.writeFile(getMailFilePath(userID),  mails);
        String path = isReceiver ? buildMailFolderPath("Inbox", userID) : buildMailFolderPath("Sent", userID);
        MailFolder mailFolder = (MailFolder) fileService.readFile(path, MailFolder.class, false);
        mailFolder.addID(mailID);
        fileService.writeFile(path, mailFolder);
    }

    public void addFolder(String folderName, int id){
        User user = getUserById(id);
        if(user != null){
            user.addFolder(folderName);
            updateUsersDatabase();
            MailFolder mailFolder = new MailFolder(folderName);
            String path = buildMailFolderPath(folderName, id);
            fileService.writeFile(path, mailFolder);
        }
    }

    public void addMailToFolder(ArrayList<Integer> mailIDs, String folderName ,int userId){
        String path = buildMailFolderPath(folderName, userId);
        MailFolder mailFolder = (MailFolder) fileService.readFile(path, MailFolder.class, false);
        mailFolder.addID(mailIDs);
        fileService.writeFile(path, mailFolder);
    }

    public void renameFolder(String oldName, String newName, int userID){
        User user = getUserById(userID);
        if(user != null){
            String oldPath = buildMailFolderPath(oldName, userID);
            String newPath = buildMailFolderPath(newName, userID);
            if(fileService.renameFile(oldPath, newPath)){
                user.renameFolder(oldName, newName);
                updateUsersDatabase();
            }
        }
    }

    public void deleteFolder(String folderName, int id){
        User user = getUserById(id);
        if(user != null){
            user.deleteFolder(folderName);
            updateUsersDatabase();
            String path = buildMailFolderPath(folderName, id);
            fileService.deleteFile(path);
        }
    }

    public void addCriteriaToFolder(String folderName, CriteriaInfo criteria, int userID){
        User user = getUserById(userID);
        if(user != null){
            String path = buildMailFolderPath(folderName, userID);
            MailFolder mailFolder = (MailFolder) fileService.readFile(path, MailFolder.class, false);
            mailFolder.addCriteria(criteria);
            fileService.writeFile(path, mailFolder);
        }
    }

    public void addContact(Contact contact, int id){
        User user = getUserById(id);
        if(user != null){
            user.addContact(contact);
            updateUsersDatabase();
        }
    }

    public void editContact(Contact newContact, int userID, int contactID){
        User user = getUserById(userID);
        if(user != null){
            user.editContact(newContact, contactID);
            updateUsersDatabase();
        }
    }

    public void deleteContact(Contact contact, int userID){
        User user = getUserById(userID);
        if(user != null){
            user.removeContact(contact);
            updateUsersDatabase();
        }
    }
    
    public ArrayList<Integer> filter(ArrayList<Criteria> criterias, ArrayList<Mail> mails){
        for(int i = 0; i < criterias.size()/2; i++){
            Criteria andCriteria = new AndCriteria(criterias.get(i*2), criterias.get(i*2+1));
            mails = andCriteria.meetCriteria(mails);
        }
        if(criterias.size()%2 == 1){
            mails = criterias.get(criterias.size()-1).meetCriteria(mails);
        }
        ArrayList<Integer> criteriaMailID = new ArrayList<>();
        for(Mail mail: mails){
            criteriaMailID.add(mail.getId());
        }
        return criteriaMailID;
    }

    public ArrayList<Integer> filterInfo(ArrayList<CriteriaInfo> criteriaInfos, ArrayList<Mail> mails){
        ArrayList<Criteria> criterias = new ArrayList<>();
        for(CriteriaInfo criteriaInfo: criteriaInfos){
            criterias.add(criteriaInfo.transfer());
        }
        return filter(criterias, mails);
    }
    
    private ArrayList<Mail> getMailInFolder(String folderName, int userID){
        ArrayList<Mail> mails = getAllMails(userID);
        MailFolder mailFolder = (MailFolder) fileService.readFile(buildMailFolderPath(folderName, userID), MailFolder.class, false);
        mails.removeIf(mail -> !mailFolder.getIds().contains(mail.getId()));
        return mails;
    }

    private ArrayList<Mail> getAllMails(int userID){
        return (ArrayList<Mail>) fileService.readFile(getMailFilePath(userID), Mail.class, true);
    }

    private boolean isUsernameExists(String username){
        for(int i = 0; i < users.size(); i++){
            if(this.users.get(i).getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    private User getUserByUsername(String username){
        for(int i = 0; i < users.size(); i++){
            if(this.users.get(i).getUsername().equals(username)){
                return users.get(i);
            }
        }
        return null;
    }

    private User getUserById(int id){
        for(int i = 0; i < users.size(); i++){
            if(this.users.get(i).getUserID() == id){
                return users.get(i);
            }
        }
        return null;
    }

    private String buildMailFolderPath(String folderName, int id){
        return "Database/" + id + "/" + folderName + ".json";
    }

    private String getMailFilePath(int id){
        return "Database/" + id + "/" + "Mails.json";
    }

    private void updateUsersDatabase(){
        fileService.writeFile("Database/UsersDatabase.json", this.users);
    }
}
