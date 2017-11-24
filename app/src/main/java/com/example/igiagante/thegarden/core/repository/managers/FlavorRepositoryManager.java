package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;

import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.FlavorRealmRepository;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiFlavorRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 3/6/16.
 */
public class FlavorRepositoryManager {

    private FlavorRealmRepository flavorRealmRepository;
    private RestApiFlavorRepository apiFlavorRepository;

    @Inject
    public FlavorRepositoryManager(Context context) {
        flavorRealmRepository = new FlavorRealmRepository(context);
        apiFlavorRepository = new RestApiFlavorRepository();
    }

    /**
     * Return an observable a list of resources.
     * @param specification {@link Specification}
     * @return Observable
     */
    public Observable<List<Flavor>> query(Specification specification) {

        Observable<List<Flavor>> attributesObservableDB = flavorRealmRepository.query(specification);
        Observable<List<Flavor>> attributesObservableAPI = apiFlavorRepository.query(specification);

        return attributesObservableDB.switchIfEmpty(attributesObservableAPI)
                .doOnNext(list -> System.out.print("list size of flavors: " + list.size()))
                .flatMap(attributes -> {
                    flavorRealmRepository.add(attributes);
                    return Observable.just(attributes);
                });
    }
}
