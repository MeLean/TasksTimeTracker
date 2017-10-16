package com.melean.taskstimetracker.testUtils


import com.melean.taskstimetracker.data.database.RealmObjects.TaskEntityRealmObject

import org.powermock.api.mockito.PowerMockito

import io.realm.Realm

import org.powermock.api.mockito.PowerMockito.mockStatic
import org.powermock.api.mockito.PowerMockito.`when`

object MockGenerator {

    val mockedRealm: Realm
        get() {
            mockStatic(Realm::class.java)

            val mockedRealm = PowerMockito.mock(Realm::class.java)

            `when`(mockedRealm.createObject(TaskEntityRealmObject::class.java)).thenReturn(TaskEntityRealmObject())

            `when`(Realm.getDefaultInstance()).thenReturn(mockedRealm)

            return mockedRealm
        }
}
