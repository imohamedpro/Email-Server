package university.project.MailBackend.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import university.project.MailBackend.Interfaces.ReadUserInfo;

public class UserInfo implements ReadUserInfo{
    private String email;
    private String password;
    private int id;

    @JsonCreator
    public UserInfo(
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("id") int id) {
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
