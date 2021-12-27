package university.project.MailBackend.Interfaces;

import java.util.List;

public interface Searchable {
    boolean contains(List<String> tokens, boolean filter);
}
