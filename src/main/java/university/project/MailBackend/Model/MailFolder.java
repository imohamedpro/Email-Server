package university.project.MailBackend.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class MailFolder {
    String name;
    ArrayList<Integer> ids;

    @JsonCreator
    public MailFolder(
            @JsonProperty("name") String name,
            @JsonProperty("ids") ArrayList<Integer> ids)
    {
        this.name = name;
        this.ids = ids;
    }

    public MailFolder(String name){
        this.name = name;
        this.ids = new ArrayList<Integer>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getIds() {
        return ids;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIds(ArrayList<Integer> ids) {
        this.ids = ids;
    }

    public void addID(int id){
        this.ids.add(id);
    }

    public void addID(ArrayList<Integer> ids){
        this.ids.addAll(ids);
    }
}
