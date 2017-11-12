package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;

import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.example.igiagante.thegarden.core.repository.realm.SensorTempRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.specification.SensorTempSpecification;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiSensorTempRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * @author Ignacio Giagante, on 22/8/16.
 */
public class SensorTempRepositoryManager extends
        BaseRepositoryManager<SensorTemp, SensorTempRealmRepository, RestApiSensorTempRepository> {

    @Inject
    public SensorTempRepositoryManager(Context context) {
        super(context, new SensorTempRealmRepository(context), new RestApiSensorTempRepository());
    }

    public Observable getSensorData() {

        SensorTempSpecification sensorTempSpecification = new SensorTempSpecification();
        Observable<List<SensorTemp>> query = db.query(sensorTempSpecification);

        return !checkInternet() ? query :
                Observable.concat(query, api.query(null)).first(new ArrayList<>()).toObservable();
    }
}
