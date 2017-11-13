package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;

import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.example.igiagante.thegarden.core.repository.realm.SensorTempRealmRepository;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiSensorTempRepository;

import javax.inject.Inject;


/**
 * @author Ignacio Giagante, on 22/8/16.
 */
public class SensorTempRepositoryManager extends
        BaseRepositoryManager<SensorTemp, SensorTempRealmRepository, RestApiSensorTempRepository> {

    @Inject
    public SensorTempRepositoryManager(Context context) {
        super(context, new SensorTempRealmRepository(context), new RestApiSensorTempRepository());
    }
}
