package com.melean.taskstimetracker;



import com.melean.taskstimetracker.data.database.RealmObjects.TaskEntity;

import org.powermock.api.mockito.PowerMockito;

import io.realm.Realm;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

public class MockGenerator {

    public static Realm getMockedRealm() {
        mockStatic(Realm.class);

        Realm mockedRealm = PowerMockito.mock(Realm.class);

        when(mockedRealm.createObject(TaskEntity.class)).thenReturn(new TaskEntity());

        when(Realm.getDefaultInstance()).thenReturn(mockedRealm);

        return mockedRealm;
    }
}
