package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;
import android.util.Log;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.AttributeRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.specification.attribute.AttributeSpecification;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiAttributeRepository;
import com.example.igiagante.thegarden.home.plants.usecase.PersistStaticDataUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Ignacio Giagante, on 3/6/16.
 */
public class AttributeRepositoryManager {

    private AttributeRealmRepository attributeRealmRepository;
    private RestApiAttributeRepository apiAttributeRepository;

    @Inject
    public AttributeRepositoryManager(Context context) {
        attributeRealmRepository = new AttributeRealmRepository(context);
        apiAttributeRepository = new RestApiAttributeRepository();
    }

    /**
     * Return an observable a list of resources.
     * @param specification {@link Specification}
     * @return Observable
     */
    public Observable<List<Attribute>> query(Specification specification) {

        Observable<List<Attribute>> attributesObservableDB = attributeRealmRepository.query(specification);
        Observable<List<Attribute>> attributesObservableAPI = apiAttributeRepository.query(specification)
                .subscribeOn(Schedulers.io());

        // We only want the list once it is loaded.
        //attributesObservableDB.filter(people -> p))


        return attributesObservableDB.switchIfEmpty(attributesObservableAPI)
                .flatMap(attributes -> {
                    attributeRealmRepository.add(attributes);
                    return Observable.just(attributes);
                });
    }
}
