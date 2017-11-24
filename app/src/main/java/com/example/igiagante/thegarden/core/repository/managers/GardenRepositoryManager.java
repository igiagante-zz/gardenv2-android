package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.repository.realm.GardenRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.IrrigationRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiGardenRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class GardenRepositoryManager
        extends BaseRepositoryManager<Garden, GardenRealm, GardenRealmRepository, RestApiGardenRepository> {

    private Context context;
    private Session session;

    @Inject
    public GardenRepositoryManager(Context context, Session session) {
        super(context, new GardenRealmRepository(context), new RestApiGardenRepository(context, session));
        this.context = context;
        this.session = session;
    }

    public Observable delete(@NonNull String gardenId, @NonNull String userId) {

        if (!checkInternet()) {
            return Observable.just(-1);
        }

        // delete garden from api
        Observable<Integer> resultFromApi = api.remove(gardenId, userId);

        //Create a list of Integer in order to avoid calling Realm from other Thread
        Integer result = resultFromApi.subscribeOn(Schedulers.io()).blockingFirst();

        // delete plant from DB
        if (result!= -1) {
            Observable<Integer> resultFromDB = db.remove(gardenId);
            result = resultFromDB.blockingFirst();
        }

        // delete irrigations from garden
        IrrigationRealmRepository irrigationRealmRepository = new IrrigationRealmRepository(context);
        irrigationRealmRepository.removeIrrigationsByGardenId(gardenId);

        return result == -1 ? Observable.just(-1) : Observable.just(gardenId);
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
