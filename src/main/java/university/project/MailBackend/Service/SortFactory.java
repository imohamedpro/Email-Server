package university.project.MailBackend.Service;

import university.project.MailBackend.Model.EmailHeader;

import java.util.Comparator;

public class SortFactory {
    public Comparator<?> getSortType(String type){
        return switch (type.toLowerCase()) {
            case "subject" -> Comparator.comparing(EmailHeader::getSubject, String::compareToIgnoreCase);
            case "from" -> Comparator.comparing(EmailHeader::getFrom, String::compareToIgnoreCase);
            case "priority" -> Comparator.comparing(EmailHeader::getPriority);
            default -> Comparator.comparing(EmailHeader::getDate);
        };
    }
}
