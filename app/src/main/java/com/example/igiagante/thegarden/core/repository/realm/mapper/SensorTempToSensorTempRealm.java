package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.example.igiagante.thegarden.core.repository.MapToRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.SensorTempRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

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

        // create sensorTemp realm object and set id
        SensorTempRealm sensorTempRealm = realm.where(SensorTempRealm.class).equalTo(Table.ID,
                sensorTemp.getId()).findFirst();
        if(sensorTempRealm == null) {
            sensorTempRealm = realm.createObject(SensorTempRealm.class, sensorTemp.getId());
        }
        return copy(sensorTemp, sensorTempRealm, realm);
    }

    @Override
    public SensorTempRealm copy(SensorTemp sensorTemp, SensorTempRealm sensorTempRealm, Realm realm) {
        sensorTempRealm.setDate(sensorTemp.getDate());
        sensorTempRealm.setTemp(sensorTemp.getTemp());
        sensorTempRealm.setHumidity(sensorTemp.getHumidity());
        return sensorTempRealm;
    }
}
