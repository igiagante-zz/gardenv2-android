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

    @Inject
    public AttributeRepositoryManager(Context context) {
        super(context, new AttributeRealmRepository(context), new RestApiAttributeRepository());
    }
}
