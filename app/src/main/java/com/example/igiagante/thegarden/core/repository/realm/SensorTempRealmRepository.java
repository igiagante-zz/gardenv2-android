package com.example.igiagante.thegarden.core.repository.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.example.igiagante.thegarden.core.repository.MapToRealm;
import com.example.igiagante.thegarden.core.repository.MapToModel;
import com.example.igiagante.thegarden.core.repository.realm.mapper.SensorTempRealmToSensorTemp;
import com.example.igiagante.thegarden.core.repository.realm.mapper.SensorTempToSensorTempRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.SensorTempRealm;

import io.realm.Realm;

/**
 * @author Ignacio Giagante, on 22/8/16.
 */
public class SensorTempRealmRepository extends RealmRepository<SensorTemp, SensorTempRealm> {

    @Override
    MapToRealm<SensorTemp, SensorTempRealm> initModelToRealmMapper(Realm realm) {
        return new SensorTempToSensorTempRealm(realm);
    }

    @Override
    MapToModel<SensorTempRealm, SensorTemp> initRealmToModelMapper() {
        return new SensorTempRealmToSensorTemp();
    }

    public SensorTempRealmRepository(@NonNull Context context) {

        super(context);
    }

    @Override
    void setRealmClass() {
        this.realmClass = SensorTempRealm.class;
    }

}
