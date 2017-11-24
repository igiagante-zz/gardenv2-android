package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.example.igiagante.thegarden.core.repository.MapToRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.SensorTempRealm;

import java.util.UUID;

import io.realm.Realm;

/**
 * @author Ignacio Giagante, on 22/8/16.
 */
public class SensorTempToSensorTempRealm implements MapToRealm<SensorTemp, SensorTempRealm> {

    private final Realm realm;

    public SensorTempToSensorTempRealm(Realm realm) {
        this.realm = realm;
    }

    @Override
    public SensorTempRealm map(SensorTemp sensorTemp, Realm realm) {
        SensorTempRealm sensorTempRealm = realm.createObject(SensorTempRealm.class);
        sensorTempRealm.setId(UUID.randomUUID().toString());
        copy(sensorTemp, sensorTempRealm, realm);

        return sensorTempRealm;
    }

    @Override
    public SensorTempRealm copy(SensorTemp sensorTemp, SensorTempRealm sensorTempRealm, Realm realm) {
        sensorTempRealm.setDate(sensorTemp.getDate());
        sensorTempRealm.setTemp(sensorTemp.getTemp());
        sensorTempRealm.setHumidity(sensorTemp.getHumidity());
        return sensorTempRealm;
    }
}
