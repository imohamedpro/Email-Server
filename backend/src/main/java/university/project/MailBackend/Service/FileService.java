package university.project.MailBackend.Service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
@Service
public class FileService {
    ObjectMapper objectMapper = new ObjectMapper();

    public Object readJson(String path, Class<?> cls, boolean isArrayList){
        Path p = Path.of(path);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
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

    public void saveAsJson(String path, Object object){
        createDirIfNotExist(path);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            objectMapper.writeValue(new File(path), object);
            File file = new File(path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(path + " updated");
    }

    public void writeFile(String path, byte[] bytes) throws IOException {
        createDirIfNotExist(path);
        Files.write(Paths.get(path), bytes);
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

    private void createDirIfNotExist(String path){
        String[] directories = path.split("/");
        String dir = "";
        for(String directory: Arrays.copyOfRange(directories, 0, directories.length-1)){
            dir = dir + directory + "/";
            createDirectory(dir);
        }
    }
}
