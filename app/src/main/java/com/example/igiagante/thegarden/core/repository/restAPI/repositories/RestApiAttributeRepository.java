package com.example.igiagante.thegarden.core.repository.restAPI.repositories;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.restAPI.services.AttributeRestApi;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 3/6/16.
 */
public class RestApiAttributeRepository extends BaseRestApiRepository<Attribute> {

    private final AttributeRestApi api;

    public RestApiAttributeRepository() {
        this.api = ServiceFactory.createRetrofitService(AttributeRestApi.class);
    }

    @Override
    public Observable<List<Attribute>> getAll() {
        return api.getAttributes();
    }

    @Override
    public Observable<List<Attribute>> query(Specification specification) {
        return api.getAttributes();
    }
}
