package university.project.MailBackend.Model;

import university.project.MailBackend.Interfaces.ReadUserInfo;

public class UserInfo implements ReadUserInfo {
    private String email;
    private String password;
    private int id;

    public UserInfo(String email, String password, int id) {
        this.email = email;
        this.password = password;
        this.id = id;
    }
    

    @Override
    public String getEmail() {
        return email;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public int getID() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
