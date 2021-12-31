package university.project.MailBackend.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import university.project.MailBackend.Interfaces.ReadUserInfo;

public class UserInfo implements ReadUserInfo{
    private String email;
    private String password;

    @JsonCreator
    public UserInfo(
            @JsonProperty("email") String email,
            @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String getEmail() {
        return email;
    }
    @Override
    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean authenticate(UserInfo userInfo){
        return this.email.equals(userInfo.getEmail()) && this.password.equals(userInfo.getPassword());
    }

}
