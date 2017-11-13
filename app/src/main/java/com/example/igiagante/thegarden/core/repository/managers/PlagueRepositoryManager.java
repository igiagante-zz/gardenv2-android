package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;

import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.PlagueRealmRepository;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiPlagueRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Ignacio Giagante, on 6/4/16.
 */
public class PlagueRepositoryManager {

    private PlagueRealmRepository plagueRealmRepository;
    private RestApiPlagueRepository apiPlagueRepository;

    @Inject
    public PlagueRepositoryManager(Context context) {
        this.plagueRealmRepository = new PlagueRealmRepository(context);
        this.apiPlagueRepository = new RestApiPlagueRepository();
    }

    /**
     * Return an observable a list of resources.
     * @param specification {@link Specification}
     * @return Observable
     */
    public Observable<List<Plague>> query(Specification specification) {

        Observable<List<Plague>> attributesObservableDB = plagueRealmRepository.query(specification);
        Observable<List<Plague>> attributesObservableAPI = apiPlagueRepository.query(specification)
                .subscribeOn(Schedulers.io());

        return attributesObservableDB.switchIfEmpty(attributesObservableAPI)
                .flatMap(plagues -> {
                    plagueRealmRepository.add(plagues);
                    return Observable.just(plagues);
                });
    }
}
