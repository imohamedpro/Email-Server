package university.project.MailBackend.Model.Filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CriteriaInfo {
    private String type;
    private String value;

    @JsonCreator
    public CriteriaInfo(
            @JsonProperty("type") String type,
            @JsonProperty("value") String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public Criteria transfer(){
        return switch (this.type) {
            case "from" -> new FromCriteria(value);
            case "to" -> new ToCriteria(value);
            case "subject" -> new SubjectCriteria(value);
            case "body" -> new BodyCriteria(value);
            case "read" -> new ReadCriteria(stringToBoolean());
            case "priority" -> new PriorityCriteria(Integer.parseInt(value));
            default -> null;
        };
    }

    private boolean stringToBoolean(){
        return value.equals("true");
    }
}
