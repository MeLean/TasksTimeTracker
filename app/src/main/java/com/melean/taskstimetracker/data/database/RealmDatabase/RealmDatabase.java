package com.melean.taskstimetracker.data.database.RealmDatabase;

import android.content.Context;

import java.security.SecureRandom;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmDatabase {

    public RealmDatabase(Context context) {
        Realm.init(context);
    }

    public static Realm getRealmInstance() {
        //Realm configuration

        //byte[] key = new byte[64];
        //new SecureRandom().nextBytes(key);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                //.encryptionKey(key)
                .build();
        return Realm.getInstance(realmConfiguration);
    }

    public synchronized <T extends RealmObject> T add(T model) {
        final Realm realm = getRealmInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
        return model;
    }
    public synchronized <T extends RealmObject> List<T> addAll(List<T> list) {
        if(list == null || list.size() == 0) return null;
        final Realm realm = getRealmInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(list);
        realm.commitTransaction();
        return list;
    }

    public synchronized <T extends RealmObject> RealmResults<T> findAll(Class<T> clazz) {
          return getRealmInstance().where(clazz).findAll();
    }

    public synchronized <T extends RealmObject> List<T> copyAll(Class<T> clazz) {
        return getRealmInstance().copyFromRealm(findAll(clazz));
    }

    public synchronized <T extends RealmObject> RealmResults<T> findAllByProperty(Class<T> clazz, String propertyName, Object propertyValue) {
        //add more cases if needed

        // todo try to change if with:
        // return getRealmInstance().where(clazz).equalTo(propertyName, (propertyValue.getClass()) propertyValue).findAll();
        if(propertyValue instanceof Integer) {
            return getRealmInstance().where(clazz).equalTo(propertyName, (Integer) propertyValue).findAll();
        }else if(propertyValue instanceof String){
            return getRealmInstance().where(clazz).equalTo(propertyName, (String) propertyValue).findAll();
        }else if(propertyValue instanceof Boolean){
            return getRealmInstance().where(clazz).equalTo(propertyName, (Boolean) propertyValue).findAll();
        }
        return null;
    }

    public synchronized <T extends RealmObject> List<T> copyAllByProperty(Class<T> clazz,String propertyName, Object propertyValue) {
        return getRealmInstance().copyFromRealm(findAllByProperty(clazz, propertyName, propertyValue));
    }

    public <T extends RealmObject> RealmObject findFirst(Class<T> clazz) {
        return getRealmInstance().where(clazz).findFirst();
    }

    public synchronized <T extends RealmObject> boolean deleteAll(Class<T> modelClass) {
        final Realm realm = getRealmInstance();
        RealmResults<T> existingRecords = realm.where(modelClass).findAll();

        if(existingRecords != null && existingRecords.size() > 0) {
            realm.beginTransaction();
            existingRecords.deleteAllFromRealm();
            realm.commitTransaction();
            return true;
        }
        return false;
    }

    public synchronized  <T extends RealmObject> boolean deleteAllBy(Class<T> modelClass, String propertyName, Object propertyValue) {
        final Realm realm = getRealmInstance();
        RealmResults<T> existingRecords = null;
        if(propertyValue instanceof Integer) {
            existingRecords = getRealmInstance().where(modelClass).equalTo(propertyName, (Integer) propertyValue).findAll();
        }else if(propertyValue instanceof String){
            existingRecords = getRealmInstance().where(modelClass).equalTo(propertyName, (String) propertyValue).findAll();
        }else if(propertyValue instanceof Boolean){
            existingRecords = getRealmInstance().where(modelClass).equalTo(propertyName, (Boolean) propertyValue).findAll();
        }
        if(existingRecords != null && existingRecords.size() > 0){
            realm.beginTransaction();
            existingRecords.deleteAllFromRealm();
            realm.commitTransaction();
            return true;
        }
        return false;
    }
}
