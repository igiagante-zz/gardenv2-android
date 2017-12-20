package com.example.igiagante.thegarden.core.repository.restAPI.repositories;

import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.restAPI.services.SensorTempApi;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 19/8/16.
 */
public class RestApiSensorTempRepository extends BaseRestApiRepository<SensorTemp> {

    private final SensorTempApi api;

    public RestApiSensorTempRepository() {
        this.api = ServiceFactory.createRetrofitService(SensorTempApi.class);
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
