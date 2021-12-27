package university.project.MailBackend.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import university.project.MailBackend.Model.Filter.CriteriaInfo;

import java.util.ArrayList;

public class MailFolder {
    String name;
    ArrayList<Integer> ids;
    ArrayList<CriteriaInfo> criterias;

    @JsonCreator
    public MailFolder(
            @JsonProperty("name") String name,
            @JsonProperty("ids") ArrayList<Integer> ids,
            @JsonProperty("criterias") ArrayList<CriteriaInfo> criterias)
    {
        this.name = name;
        this.ids = ids;
        this.criterias = criterias;
    }

    public MailFolder(String name){
        this.name = name;
        this.ids = new ArrayList<Integer>();
        this.criterias = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getIds() {
        return ids;
    }

    public ArrayList<CriteriaInfo> getCriterias(){
        return criterias;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIds(ArrayList<Integer> ids) {
        this.ids = ids;
    }

    public void setCriterias(ArrayList<CriteriaInfo> criterias){
        this.criterias = criterias;
    }

    public void addID(int id){
        this.ids.add(id);
    }

    public void addID(ArrayList<Integer> ids){
        this.ids.addAll(ids);
    }

    public void addCriteria(CriteriaInfo criteria){
        criterias.add(criteria);
    }
}
