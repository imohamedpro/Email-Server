package university.project.MailBackend.Service;

import university.project.MailBackend.Model.EmailHeader;

import java.util.Comparator;

public class SortFactory {
    public Comparator<? super EmailHeader> getSortType(String type){
        // case "subject d" -> Comparator.comparing(EmailHeader::getSubject, String::compareToIgnoreCase).reversed();
        // case "from d" -> Comparator.comparing(EmailHeader::getFrom, String::compareToIgnoreCase).reversed();
        // case "priority d" -> Comparator.comparing(EmailHeader::getPriority).reversed();
        // case "date" -> Comparator.comparing(EmailHeader::getDate);
        switch (type.toLowerCase()) {
            case "subject":
                return Comparator.comparing(EmailHeader::getSubject, String::compareToIgnoreCase);
            case "from":
                return Comparator.comparing(EmailHeader::getFrom, String::compareToIgnoreCase);
            case "priority":
                return Comparator.comparing(EmailHeader::getPriority);
            default:
                return Comparator.comparing(EmailHeader::getDate);
        }
    }
}
