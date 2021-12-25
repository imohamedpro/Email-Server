package university.project.MailBackend.Service;

import university.project.MailBackend.Model.Contact;
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
            fileService.writeFile("Database/UsersDatabase.json", this.users);
            String path = "Database/" + user.getUserID() + "/";
            fileService.createDirectory(path);

            for(String folderName: user.getFolders()){
                MailFolder mailFolder = new MailFolder(folderName);
                path = "Database/" + user.getUserID() + "/" + folderName + ".json";
                fileService.writeFile(path, mailFolder);
            }
            return true;
        }
        return false;
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

    public void deleteFolder(String folderName, int id){
        User user = getUserById(id);
        if(user != null){
            user.deleteFolder(folderName);
            updateUsersDatabase();
            String path = buildMailFolderPath(folderName, id);
            fileService.deleteFile(path);
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
        }
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

    private void updateUsersDatabase(){
        fileService.writeFile("Database/UsersDatabase.json", this.users);
    }
}
