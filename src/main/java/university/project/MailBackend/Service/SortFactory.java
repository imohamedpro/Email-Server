package university.project.MailBackend.Service;

import university.project.MailBackend.Model.EmailHeader;

import java.util.Comparator;

public class SortFactory {
    public Comparator<? super EmailHeader> getSortType(String type){
        return switch (type.toLowerCase()) {
            // case "subject d" -> Comparator.comparing(EmailHeader::getSubject, String::compareToIgnoreCase).reversed();
            // case "from d" -> Comparator.comparing(EmailHeader::getFrom, String::compareToIgnoreCase).reversed();
            // case "priority d" -> Comparator.comparing(EmailHeader::getPriority).reversed();
            // case "date" -> Comparator.comparing(EmailHeader::getDate);
            case "subject" -> Comparator.comparing(EmailHeader::getSubject, String::compareToIgnoreCase);
            case "from" -> Comparator.comparing(EmailHeader::getFrom, String::compareToIgnoreCase);
            case "priority" -> Comparator.comparing(EmailHeader::getPriority);
            default -> Comparator.comparing(EmailHeader::getDate);
        };
    }
}
