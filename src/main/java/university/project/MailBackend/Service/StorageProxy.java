package university.project.MailBackend.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import university.project.MailBackend.Interfaces.IStorage;
import university.project.MailBackend.Model.UserContact;
import university.project.MailBackend.Model.UserData;
import university.project.MailBackend.Model.UserInfo;

public class StorageProxy implements IStorage {
    IStorage storage;
    Map<String, UserData> cachedData;
    Map<String, UserInfo> cachedInfo;
    Map<String, UserContact> cachedContact;
    final int CACHE_CAPACITY = 5;


    public StorageProxy(IStorage storage) {
        this.storage = storage;
        cachedInfo = new HashMap<String, UserInfo>(5);
        cachedData = new HashMap<String, UserData>(5);
        cachedContact = new HashMap<String, UserContact>(5);

    }

    @Override
    public UserInfo getUserInfo(String user) {
        UserInfo data = cachedInfo.get(user);
        if(data == null){
            data = storage.getUserInfo(user);
            CacheInfo(data);
        }
        return data;
    }

    @Override
    public UserData getUserData(String user) {
        UserData data = cachedData.get(user);
        if(data == null){
            data = storage.getUserData(user);
            CacheData(data, user);
        }
        this.delete(data.autoDelete(), user);
        return data;
    }

    @Override
    public UserContact getUserContact(String user) {
        UserContact data = cachedContact.get(user);
        if(data == null){
            data = storage.getUserContact(user);
            CacheContact(data, user);
        }
        return data;
    }

    @Override
    public void setUserInfo(UserInfo info) {
        if(cachedInfo.containsKey(info.getEmail())){
            cachedInfo.put(info.getEmail(), info);
        }else{
            CacheInfo(info);
        }
        storage.setUserInfo(info);
        
    }

    @Override
    public void setUserData(UserData data, String user) {
        this.delete(data.autoDelete(), user);
        if(cachedData.containsKey(user)){
            cachedData.put(user, data);
        }else{
            CacheData(data, user);
        }
        storage.setUserData(data, user);
        
    }

    @Override
    public void setUserContact(UserContact contact, String user) {
        if(cachedContact.containsKey(user)){
            cachedContact.put(user, contact);
        }else{
            CacheContact(contact, user);
        }
        storage.setUserContact(contact, user);
        
    }

    private String getRandomKey(Set<String> set){
        List<String> l = new ArrayList<String>(set);
        return l.get((int)(Math.random() * 5));
    }

    private void CacheInfo(UserInfo info){
        if(info == null)
            return;
        if(cachedInfo.size() >= CACHE_CAPACITY){
            String randKey = getRandomKey(cachedInfo.keySet());
            cachedInfo.remove(randKey);
        }
        cachedInfo.put(info.getEmail(), info);
    }

    private void CacheData(UserData data, String user){
        if(cachedData.size() >= CACHE_CAPACITY){
            String randKey = getRandomKey(cachedData.keySet());
            cachedData.remove(randKey);
        }
        cachedData.put(user, data);
    }

    private void CacheContact(UserContact contact, String user){
        if(cachedContact.size() >= CACHE_CAPACITY){
            String randKey = getRandomKey(cachedContact.keySet());
            cachedContact.remove(randKey);
        }
        cachedContact.put(user, contact);
    }

    private void delete(List<Integer> deleted, String user){
        for(int id: deleted){
            storage.delete(user, id);
        }
    }

    // @Override
    // public void delete(String user, int id, String name) {
    //     storage.delete(user, id, name);        
    // }

    @Override
    public void delete(String user, int id) {
        storage.delete(user, id);
    }

    @Override
    public void addAttachment(String user, int emaiID, File f) {
        storage.addAttachment(user, emaiID, f);        
    }
        
}
