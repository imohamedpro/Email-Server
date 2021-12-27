package university.project.MailBackend.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import university.project.MailBackend.Model.UserData;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileService {
    ObjectMapper objectMapper = new ObjectMapper();

    public Object readFile(String path, Class<?> cls, boolean isArrayList){
        Path p = Path.of(path);
        if(Files.exists(p)){
            String json = null;
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, cls);
            try {
                json = Files.readString(p, StandardCharsets.UTF_8);
                if(isArrayList){
                    return objectMapper.readValue(json, listType);
                }
                return objectMapper.readValue(json, cls);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void writeFile(String path, Object object){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(path), object);
            File file = new File(path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(path + " updated");
    }

    public void deleteFile(String path){
        File file = new File(path);
        if(file.delete()){
            System.out.println(path + " is deleted");
        }
    }

    public void createDirectory(String path){
        Path p = Path.of(path);
        try {
            if(!Files.exists(p)){
                Files.createDirectory(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
