package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;

import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.AttributeRealmRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 3/6/16.
 */
public class AttributeRepositoryManager {

    private AttributeRealmRepository attributeRealmRepository;

    @Inject
    public AttributeRepositoryManager(Context context) {
        attributeRealmRepository = new AttributeRealmRepository(context);
    }

    /**
     * Return an observable a list of resources.
     * @param specification {@link Specification}
     * @return Observable
     */
    public Observable query(Specification specification) {
        return  attributeRealmRepository.query(specification);
    }
}
