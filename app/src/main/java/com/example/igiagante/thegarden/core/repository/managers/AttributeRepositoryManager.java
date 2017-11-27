package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.AttributeRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.AttributeRealm;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiAttributeRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 3/6/16.
 */
public class AttributeRepositoryManager
        extends BaseRepositoryManager<Attribute, AttributeRealm, AttributeRealmRepository, RestApiAttributeRepository> {

    private AttributeRealmRepository attributeRealmRepository;
    private RestApiAttributeRepository apiAttributeRepository;

    @Inject
    public AttributeRepositoryManager(Context context) {
        super(context, new AttributeRealmRepository(context), new RestApiAttributeRepository());
    }

    /**
     * Return an observable a list of resources.
     * @param specification {@link Specification}
     * @return Observable
     */
    public Observable<List<Attribute>> query(Specification specification) {

        Observable<List<Attribute>> attributesObservableDB = attributeRealmRepository.query(specification);
        Observable<List<Attribute>> attributesObservableAPI = apiAttributeRepository.query(specification);

        return attributesObservableDB.switchIfEmpty(attributesObservableAPI)
                .doOnNext(list -> System.out.print("list size of attributes: " + list.size()))
                .flatMap(attributes -> {
                    attributeRealmRepository.add(attributes);
                    return Observable.just(attributes);
                });
    }
}
