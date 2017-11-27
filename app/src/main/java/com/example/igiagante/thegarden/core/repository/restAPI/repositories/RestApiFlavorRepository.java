package com.example.igiagante.thegarden.core.repository.restAPI.repositories;

import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.restAPI.services.FlavorRestApi;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * @author Ignacio Giagante, on 27/5/16.
 */
public class RestApiFlavorRepository implements Repository<Flavor> {

    private final FlavorRestApi api;

    @Inject
    public RestApiFlavorRepository() {
        api = ServiceFactory.createRetrofitService(FlavorRestApi.class);
    }

    @Override
    public Observable<Flavor> getById(String id) {
        return null;
    }

    @Override
    public Observable<Flavor> getByName(String name) {
        return null;
    }

    @Override
    public Observable<Flavor> save(Flavor item) {
        return null;
    }

    @Override
    public Observable<Integer> add(Iterable<Flavor> items) {
        return null;
    }

    @Override
    public Observable<Integer> remove(String flavorId) {
        return null;
    }

    @Override
    public void removeAll() {

    }

    @Override
    public Observable<List<Flavor>> getAll() {
        return api.getFlavors();
    }

    @Override
    public Observable<List<Flavor>> query(Specification specification) {
        return api.getFlavors();
    }
}
