package com.melean.taskstimetracker.testUtils;



import com.melean.taskstimetracker.data.database.RealmObjects.TaskEntityRealmObject;

import org.powermock.api.mockito.PowerMockito;

import io.realm.Realm;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

public class MockGenerator {

    public static Realm getMockedRealm() {
        mockStatic(Realm.class);

        Realm mockedRealm = PowerMockito.mock(Realm.class);

        when(mockedRealm.createObject(TaskEntityRealmObject.class)).thenReturn(new TaskEntityRealmObject());

        when(Realm.getDefaultInstance()).thenReturn(mockedRealm);

        return mockedRealm;
    }
}
