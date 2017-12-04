package com.example.igiagante.thegarden.core.repository.restAPI.repositories;

import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.restAPI.services.SensorTempApi;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 19/8/16.
 */
public class RestApiSensorTempRepository implements Repository<SensorTemp> {

    private final SensorTempApi api;

    public RestApiSensorTempRepository() {
        this.api = ServiceFactory.createRetrofitService(SensorTempApi.class);
    }

    @Override
    public Observable<SensorTemp> getById(String id) {
        return null;
    }

    @Override
    public Observable<SensorTemp> getByName(String name) {
        return null;
    }

    @Override
    public Observable<SensorTemp> save(SensorTemp item, boolean update) {
        return null;
    }

    @Override
    public Observable<List<SensorTemp>> add(Iterable<SensorTemp> items) {
        return null;
    }

    @Override
    public Observable<Boolean> remove(String id) {
        return null;
    }

    @Override
    public void removeAll() {

    }

    @Override
    public Observable<List<SensorTemp>> getAll() {
        return api.getValues();
    }

    public Observable<SensorTemp> getActualTempAndHumd() {
        return api.getActualTempAndHumd();
    }

    @Override
    public Observable<List<SensorTemp>> query(Specification specification) {
        return api.getValues();
    }
}
