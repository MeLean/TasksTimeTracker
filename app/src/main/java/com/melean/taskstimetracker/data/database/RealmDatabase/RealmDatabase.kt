package com.melean.taskstimetracker.data.database.RealmDatabase

import android.content.Context

import java.security.SecureRandom

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject
import io.realm.RealmResults

open class RealmDatabase(context: Context) {

    init {
        Realm.init(context)
    }

    @Synchronized
    fun <T : RealmObject> add(model: T): T {
        val realm = realmInstance
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(model)
        realm.commitTransaction()
        return model
    }

    @Synchronized
    fun <T : RealmObject> addAll(list: List<T>?): List<T>? {
        if (list == null || list.isEmpty()) return null
        val realm = realmInstance
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(list)
        realm.commitTransaction()
        return list
    }

    @Synchronized
    fun <T : RealmObject> findAll(clazz: Class<T>): RealmResults<T> =
            realmInstance.where(clazz).findAll()

    @Synchronized
    fun <T : RealmObject> copyAll(clazz: Class<T>): MutableList<T> =
            realmInstance.copyFromRealm(findAll(clazz))

    @Synchronized
    fun <T : RealmObject> findAllByProperty(clazz: Class<T>, propertyName: String, propertyValue: Any): RealmResults<T>? {
        //add more cases if needed

        if (propertyValue is Int) {
            return realmInstance.where(clazz).equalTo(propertyName, propertyValue).findAll()
        } else if (propertyValue is String) {
            return realmInstance.where(clazz).equalTo(propertyName, propertyValue).findAll()
        } else if (propertyValue is Boolean) {
            return realmInstance.where(clazz).equalTo(propertyName, propertyValue).findAll()
        }
        return null
    }

    @Synchronized
    fun <T : RealmObject> copyAllByProperty(clazz: Class<T>, propertyName: String, propertyValue: Any): MutableList<T> =
            realmInstance.copyFromRealm(findAllByProperty(clazz, propertyName, propertyValue))

    @Synchronized
    fun <T : RealmObject> findFirst(clazz: Class<T>): RealmObject =
            realmInstance.where(clazz).findFirst()

    @Synchronized
    fun <T : RealmObject> deleteAll(modelClass: Class<T>): Boolean {
        val realm = realmInstance
        val existingRecords = realm.where(modelClass).findAll()

        if (existingRecords != null && existingRecords.size > 0) {
            realm.beginTransaction()
            existingRecords.deleteAllFromRealm()
            realm.commitTransaction()
            return true
        }
        return false
    }

    @Synchronized
    fun <T : RealmObject> deleteAllBy(modelClass: Class<T>, propertyName: String, propertyValue: Any): Boolean {
        val realm = realmInstance
        var existingRecords: RealmResults<T>? = null
        if (propertyValue is Int) {

        } else if (propertyValue is String) {
            existingRecords = realmInstance.where(modelClass).equalTo(propertyName, propertyValue).findAll()
        } else if (propertyValue is Boolean) {
            existingRecords = realmInstance.where(modelClass).equalTo(propertyName, propertyValue).findAll()
        }
        if (existingRecords != null && existingRecords.size > 0) {
            realm.beginTransaction()
            existingRecords.deleteAllFromRealm()
            realm.commitTransaction()
            return true
        }
        return false
    }

    companion object {

        //Realm configuration
        //byte[] key = new byte[64];
        //new SecureRandom().nextBytes(key);
        //.encryptionKey(key)
        val realmInstance: Realm
            get() {
                val realmConfiguration = RealmConfiguration.Builder()
                        .build()
                return Realm.getInstance(realmConfiguration)
            }
    }
}
