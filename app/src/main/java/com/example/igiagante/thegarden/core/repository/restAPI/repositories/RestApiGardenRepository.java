package com.example.igiagante.thegarden.core.repository.restAPI.repositories;

import android.content.Context;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.realm.GardenRealmRepository;
import com.example.igiagante.thegarden.core.repository.restAPI.services.GardenRestApi;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class RestApiGardenRepository extends BaseRestApiRepository<Garden> {

    private final GardenRestApi api;

    public RestApiGardenRepository(Context context, Session session) {
        api = ServiceFactory.createRetrofitService(GardenRestApi.class, session.getToken());
    }

    @Override
    public Observable<Garden> getById(String id) {
        return api.getGarden(id);
    }

    @Override
    public Observable<Garden> getByName(String name) {
        return null;
    }

    @Override
    public Observable<Garden> save(Garden garden, boolean update) {

        Observable<Garden> apiResult;

        if (update) {
            apiResult = api.updateGarden(garden.getId(), garden);
        } else {
            apiResult = api.createGarden(garden);
        }

        return execute(apiResult, GardenRealmRepository.class, update);
    }

    @Override
    public Observable<List<Garden>> add(Iterable<Garden> gardens) {
        return null;
    }

    @Override
    public Observable<Boolean> remove(String gardenId) {
        return null;
    }

    public Observable<Integer> remove(String gardenId, String userId) {
        return api.deleteGarden(gardenId, userId)
                .map(response -> response.isSuccessful() ? 1 : -1);
    }

    @Override
    public void removeAll() {

    }

    @Override
    public Observable<List<Garden>> getAll() {
        return api.getGardens();
    }

    @Override
    public Observable<List<Garden>> query(Specification specification) {
        return api.getGardens();
    }

    public Observable<List<Garden>> getGardensByUser(String username) {
        return api.getGardensByUserName(username);
    }
}
