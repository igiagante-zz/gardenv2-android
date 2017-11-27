package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.repository.realm.GardenRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiGardenRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class GardenRepositoryManager
        extends BaseRepositoryManager<Garden, GardenRealm, GardenRealmRepository, RestApiGardenRepository> {

    private Context context;
    private Session session;

    // TODO - Se debe inicializar esta variable -> como? :S
    private RestApiGardenRepository api;

    @Inject
    public GardenRepositoryManager(Context context, Session session) {
        super(context, new GardenRealmRepository(context), new RestApiGardenRepository(context, session));
        this.context = context;
        this.session = session;
    }

    public Observable<Boolean> delete(@NonNull String gardenId, @NonNull String userId) {

        if (!checkInternet()) {
            return Observable.just(false);
        }

        return api.remove(gardenId, userId)
                .flatMap(entityDeletedFromAPI -> db.remove(gardenId))
                .flatMap(entityDeletedFromDB -> Observable::just);
    }

    public Observable<Boolean> existGardenByNameAndUserId(Garden garden) {

        GardenRealmRepository gardenRealmRepository = new GardenRealmRepository(context);

        Observable<Garden> gardenObservable = gardenRealmRepository
                .getByNameAndUserId(garden.getName(), garden.getUserId());

        return gardenObservable
                .isEmpty()
                .map(object -> !object.equals(true))
                .toObservable();
    }
}
